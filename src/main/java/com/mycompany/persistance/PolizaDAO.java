package com.mycompany.persistance;

import com.mycompany.model.Poliza;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de base de datos para objetos de tipo Poliza.
 */
public class PolizaDAO {

    private Connection connection;

    /**
     * Constructor que inicializa una conexión a la base de datos.
     * @param connection La conexión a la base de datos.
     */
    public PolizaDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Agrega una nueva póliza a la base de datos.
     * @param poliza La póliza a agregar.
     * @return true si la póliza se agregó correctamente, false de lo contrario.
     */
    public boolean addPoliza(Poliza poliza) {
        String query = "INSERT INTO polizas (ID_Poliza, ID_Cliente, ID_Recibo, Numero_Poliza, Fecha_Emision, Fecha_Vencimiento, Tipo_Cobertura, Cobertura_Adicional, Comercial, Comentarios) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, poliza.getIdPoliza());
            stmt.setInt(2, poliza.getID_Cliente());
            stmt.setString(3, poliza.getIdRecibo());
            stmt.setString(4, poliza.getNumeroPoliza());
            stmt.setDate(5, Date.valueOf(poliza.getFechaEmision()));
            stmt.setDate(6, Date.valueOf(poliza.getFechaVencimiento()));
            stmt.setString(7, poliza.getTipoCobertura());
            stmt.setString(8, poliza.getCoberturaAdicional());
            stmt.setString(9, poliza.getComercial());
            stmt.setString(10, poliza.getComentarios());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
          
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Recupera una póliza por su ID.
     * @param id El identificador de la póliza.
     * @return La póliza si se encuentra, null de lo contrario.
     */
    public Poliza getPolizaById(int id) {
        String query = "SELECT * FROM polizas WHERE ID_Poliza = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractPolizaFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Recupera todas las pólizas asociadas a un cliente específico.
     * @param id El ID del cliente.
     * @return Una lista de pólizas.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<Poliza> getPolizasByIdCliente(int id) throws SQLException {
        List<Poliza> polizas = new ArrayList<>();
        String query = "SELECT * FROM polizas WHERE ID_Cliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Poliza poliza = mapResultSetToPoliza(rs);
                polizas.add(poliza);
            }
        }
        return polizas;
    }

    /**
     * Obtiene todas las pólizas de la base de datos.
     * @return Una lista de objetos Poliza.
     * @throws SQLException Si ocurre un error de acceso a la base de datos.
     */
    public List<Poliza> getAllPolizas() throws SQLException {
        List<Poliza> polizas = new ArrayList<>();
        String query = "SELECT idPoliza, numeroPoliza, fechaVencimiento, tipoCobertura FROM Polizas";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Poliza poliza = mapResultSetToPoliza(resultSet);
                polizas.add(poliza);

            }
        }
        return polizas;
    }
    
    /**
     * Actualiza una póliza existente en la base de datos.
     * @param poliza La póliza con los datos actualizados.
     * @return true si la actualización fue exitosa, false de lo contrario.
     */
    public boolean updatePoliza(Poliza poliza) {
        String query = "UPDATE polizas SET Fecha_Emision = ?, Fecha_Vencimiento = ?, Tipo_Cobertura = ?, Cobertura_Adicional = ?, Comercial = ?, Comentarios = ? WHERE ID_Poliza = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(poliza.getFechaEmision()));
            stmt.setDate(2, Date.valueOf(poliza.getFechaVencimiento()));
            stmt.setString(3, poliza.getTipoCobertura());
            stmt.setString(4, poliza.getCoberturaAdicional());
            stmt.setString(5, poliza.getComercial());
            stmt.setString(6, poliza.getComentarios());
            stmt.setInt(7, poliza.getIdPoliza());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una póliza de la base de datos por su ID.
     * @param id El identificador de la póliza a eliminar.
     * @return true si la póliza fue eliminada correctamente, false de lo contrario.
     */
    public boolean deletePoliza(int id) {
        String query = "DELETE FROM polizas WHERE ID_Poliza = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Extrae una póliza de un ResultSet.
     * @param rs El ResultSet de donde extraer la póliza.
     * @return La póliza extraída.
     * @throws SQLException Si ocurre un error al acceder al ResultSet.
     */
    private Poliza extractPolizaFromResultSet(ResultSet rs) throws SQLException {
        return new Poliza(
                rs.getInt("ID_Poliza"),
                rs.getInt("ID_Cliente"),
                rs.getString("ID_Recibo"),
                rs.getString("Numero_Poliza"),
                rs.getDate("Fecha_Emision").toLocalDate(),
                rs.getDate("Fecha_Vencimineto").toLocalDate(),
                rs.getString("Tipo_Cobertura"),
                rs.getString("Cobertura_Adicional"),
                rs.getString("Comercial"),
                rs.getString("Comentarios")
        );
    }

    private Poliza mapResultSetToPoliza(ResultSet resultSet) throws SQLException {
        Poliza poliza = new Poliza();
        poliza.setIdPoliza(resultSet.getInt("ID_Poliza"));
        poliza.setID_Cliente(resultSet.getInt("ID_Cliente"));
        poliza.setIdRecibo(resultSet.getString("ID_Recibo"));
        poliza.setNumeroPoliza(resultSet.getString("Numero_Poliza"));
        poliza.setFechaEmision(resultSet.getDate("Fecha_Emision").toLocalDate());
        poliza.setFechaVencimiento(resultSet.getDate("Fecha_Vencimiento").toLocalDate());
        poliza.setTipoCobertura(resultSet.getString("Tipo_Cobertura"));
        poliza.setCoberturaAdicional(resultSet.getString("Cobertura_Adicional"));
        poliza.setComercial(resultSet.getString("Comercial"));
        poliza.setComentarios(resultSet.getString("Comentarios"));
        return poliza;
    }

    /**
     * Calcula el siguiente ID disponible para una nueva póliza.
     * @return El siguiente ID disponible.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public int getNextID() throws SQLException {
        int maxID = 0;
        try (var statement = connection.createStatement(); var resultSet = statement.executeQuery("SELECT MAX(ID_Poliza) AS maxID FROM polizas")) {
            if (resultSet.next()) {
                maxID = resultSet.getInt("maxID");
            }
        }
        return maxID + 1;
    }
}
