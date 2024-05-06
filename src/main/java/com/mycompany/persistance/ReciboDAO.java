package com.mycompany.persistance;

import com.mycompany.model.Recibo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar las operaciones de base de datos para los objetos Recibo.
 */
public class ReciboDAO {

    private Connection connection;

    /**
     * Constructor que establece la conexión con la base de datos.
     * @param connection Conexión activa a la base de datos.
     */
    public ReciboDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Crea un objeto Recibo desde un ResultSet.
     * @param resultSet ResultSet que contiene los datos del recibo.
     * @return Un nuevo objeto Recibo.
     * @throws SQLException si ocurre un error al acceder a los datos.
     */
    private Recibo mapResultSetToRecibo(ResultSet resultSet) throws SQLException {
        Recibo recibo = new Recibo();
        recibo.setIdRecibo(resultSet.getInt("ID_Recibos"));
        recibo.setNumRecibo(resultSet.getString("Num_Recibo"));
        recibo.setFechaEmision(resultSet.getDate("Fecha_Emision").toLocalDate());
        recibo.setFechaVencimiento(resultSet.getDate("Fecha_Vencimiento").toLocalDate());
        recibo.setTotalPagar(resultSet.getString("Total_Pagar"));
        recibo.setIdPoliza(resultSet.getInt("ID_Poliza"));
        recibo.setEstadoRecibo(resultSet.getString("Estado_Recibo"));
        recibo.setTipoPago(resultSet.getString("Tipo_Pago"));
        return recibo;
    }

    /**
     * Recupera un recibo por su ID.
     * @param idRecibo El ID del recibo a recuperar.
     * @return El recibo, o null si no se encuentra.
     * @throws SQLException si ocurre un error durante la consulta.
     */
    public Recibo getRecibo(int idRecibo) throws SQLException {
        String query = "SELECT * FROM recibos WHERE ID_Recibos = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idRecibo);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToRecibo(resultSet);
            }
        }
        return null;
    }

    /**
     * Obtiene el próximo ID disponible para un nuevo recibo.
     * @return El próximo ID disponible.
     * @throws SQLException si ocurre un error durante la consulta.
     */
    public int getNextID() throws SQLException {
        int maxID = 0;
        try (var statement = connection.createStatement();
             var resultSet = statement.executeQuery("SELECT MAX(ID_Recibos) AS maxID FROM recibos")) {
            if (resultSet.next()) {
                maxID = resultSet.getInt("maxID");
            }
        }
        return maxID + 1;
    }

    /**
     * Inserta un nuevo recibo en la base de datos.
     * @param recibo El recibo a insertar.
     * @return true si el recibo fue insertado correctamente, false de lo contrario.
     * @throws SQLException si ocurre un error durante la inserción.
     */
    public boolean insertRecibo(Recibo recibo) throws SQLException {
        String query = "INSERT INTO recibos (ID_Recibos, Num_Recibo, Fecha_Emision, Fecha_Vencimiento, Total_Pagar, ID_Poliza, Estado_Recibo, Tipo_Pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, getNextID());
            stmt.setString(2, recibo.getNumRecibo());
            stmt.setDate(3, java.sql.Date.valueOf(recibo.getFechaEmision()));
            stmt.setDate(4, java.sql.Date.valueOf(recibo.getFechaVencimiento()));
            stmt.setString(5, recibo.getTotalPagar());
            stmt.setInt(6, recibo.getIdPoliza());
            stmt.setString(7, recibo.getEstadoRecibo());
            stmt.setString(8, recibo.getTipoPago());

            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Obtiene todos los recibos de la base de datos.
     * @return Una lista de objetos Recibo.
     * @throws SQLException Si ocurre un error de acceso a la base de datos.
     */
    public List<Recibo> getAllRecibos() throws SQLException {
        List<Recibo> recibos = new ArrayList<>();
        String query = "SELECT idRecibo, numRecibo, fechaEmision, fechaVencimiento, totalPagar FROM Recibos";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Recibo recibo = new Recibo();
                recibos.add(recibo);
            }
        }
        return recibos;
    }

    /**
     * Elimina un recibo de la base de datos por su ID.
     * @param idRecibo El ID del recibo a eliminar.
     * @return true si el recibo fue eliminado correctamente, false de lo contrario.
     */
    public boolean deleteRecibo(int idRecibo) {
        String sql = "DELETE FROM recibos WHERE ID_Recibos = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecibo);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza un recibo existente en la base de datos.
     * @param recibo El recibo con los datos actualizados.
     * @return true si el recibo fue actualizado correctamente, false de lo contrario.
     * @throws SQLException si ocurre un error durante la actualización.
     */
    public boolean actualizarRecibo(Recibo recibo) throws SQLException {
        String query = "UPDATE recibos SET Num_Recibo = ?, Fecha_Emision = ?, Fecha_Vencimiento = ?, Total_Pagar = ?, Estado_Recibo = ?, Tipo_Pago = ? WHERE ID_Recibos = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, recibo.getNumRecibo());
            statement.setDate(2, java.sql.Date.valueOf(recibo.getFechaEmision()));
            statement.setDate(3, java.sql.Date.valueOf(recibo.getFechaVencimiento()));
            statement.setString(4, recibo.getTotalPagar());
            statement.setString(5, recibo.getEstadoRecibo());
            statement.setString(6, recibo.getTipoPago());
            statement.setInt(7, recibo.getIdRecibo());

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene todos los recibos asociados a una poliza especifica.
     * @param id El ID de la poliza para la cual se buscan los recibos.
     * @return Una lista de recibos asociados a la poliza.
     * @throws SQLException Si hay un error al recuperar los datos.
     */
    public List<Recibo> getRecibosByIdPoliza(int id) throws SQLException {
        List<Recibo> recibos = new ArrayList<>();
        String query = "SELECT * FROM recibos WHERE ID_Poliza = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                recibos.add(mapResultSetToRecibo(resultSet));
            }
        }
        return recibos;
    }
}
