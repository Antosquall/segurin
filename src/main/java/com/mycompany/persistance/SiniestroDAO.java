package com.mycompany.persistance;

import com.mycompany.model.Siniestro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos para manejar la persistencia de objetos Siniestro.
 */
public class SiniestroDAO {

    private Connection connection;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     * @param connection La conexión activa a la base de datos.
     */
    public SiniestroDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Recupera todos los siniestros registrados en la base de datos.
     * @return Una lista de objetos Siniestro.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<Siniestro> getAllSiniestros() throws SQLException {
        List<Siniestro> siniestros = new ArrayList<>();
        String query = "SELECT * FROM siniestros";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Siniestro siniestro = mapResultSetToSiniestro(resultSet);
                siniestros.add(siniestro);
            }
        }
        return siniestros;
    }

    /**
     * Inserta un nuevo siniestro en la base de datos.
     * @param siniestro El objeto Siniestro a insertar.
     * @return true si el siniestro fue insertado exitosamente, false en caso contrario.
     * @throws SQLException Si ocurre un error durante la operación de inserción.
     */
    public boolean insertarSiniestro(Siniestro siniestro) throws SQLException {
        String query = "INSERT INTO siniestros (ID_Siniestros, Num_Siniestro, Fecha_Siniestro, Descripcion, ID_Poliza, Lugar_Siniestro, Tipo_Siniestro, Total_Reclamado, Estado_Siniestro, Fecha_Resolucion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, getNextID());
            statement.setString(2, siniestro.getNumSiniestro());
            statement.setDate(3, java.sql.Date.valueOf(siniestro.getFechaSiniestro()));
            statement.setString(4, siniestro.getDescripcion());
            statement.setInt(5, siniestro.getIdPoliza());
            statement.setString(6, siniestro.getLugarSiniestro());
            statement.setString(7, siniestro.getTipoSiniestro());
            statement.setString(8, siniestro.getTotalReclamado());
            statement.setString(9, siniestro.getEstadoSiniestro());
            statement.setDate(10, java.sql.Date.valueOf(siniestro.getFechaResolucion()));

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        }
    }

    /**
     * Mapea un ResultSet a un objeto Siniestro.
     * @param resultSet El ResultSet de donde extraer los datos.
     * @return Un objeto Siniestro poblado con los datos del ResultSet.
     * @throws SQLException Si ocurre un error al leer el ResultSet.
     */
    private Siniestro mapResultSetToSiniestro(ResultSet resultSet) throws SQLException {
        Siniestro siniestro = new Siniestro();
        siniestro.setIdSiniestro(resultSet.getInt("ID_Siniestros"));
        siniestro.setNumSiniestro(resultSet.getString("Num_Siniestro"));
        siniestro.setFechaSiniestro(resultSet.getDate("Fecha_Siniestro").toLocalDate());
        siniestro.setDescripcion(resultSet.getString("Descripcion"));
        siniestro.setIdPoliza(resultSet.getInt("ID_Poliza"));
        siniestro.setLugarSiniestro(resultSet.getString("Lugar_Siniestro"));
        siniestro.setTipoSiniestro(resultSet.getString("Tipo_Siniestro"));
        siniestro.setTotalReclamado(resultSet.getString("Total_Reclamado"));
        siniestro.setEstadoSiniestro(resultSet.getString("Estado_Siniestro"));
        siniestro.setFechaResolucion(resultSet.getDate("Fecha_Resolucion").toLocalDate()); // O cambiar el tipo según el formato de la columna en la base de datos
        return siniestro;
    }

    /**
     * Obtiene el próximo ID disponible para usar en la inserción de un nuevo siniestro.
     * @return El próximo ID disponible.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public int getNextID() throws SQLException {
        int maxID = 0;
        try (var statement = connection.createStatement(); var resultSet = statement.executeQuery("SELECT MAX(ID_Siniestros) AS maxID FROM siniestros")) {

            if (resultSet.next()) {
                maxID = resultSet.getInt("maxID");
            }

        }
        return maxID + 1;
    }

    /**
     * Obtiene un siniestro específico por su ID.
     * @param idSiniestro El ID del siniestro a buscar.
     * @return El siniestro si se encuentra, null de lo contrario.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public Siniestro getSiniestro(int idSiniestro) throws SQLException {
        Siniestro siniestro = null;
        String query = "SELECT * FROM siniestros WHERE ID_Siniestros = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSiniestro);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    siniestro = mapResultSetToSiniestro(resultSet);
                }
            }
        }
        return siniestro;
    }

    /**
     * Recupera todos los siniestros asociados a una poliza específica.
     * @param id El ID de la poliza para la cual se buscan siniestros.
     * @return Una lista de siniestros asociados a la poliza especificada.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public List<Siniestro> getSiniestrosByIdPoliza(int id) throws SQLException {
        List<Siniestro> siniestros = new ArrayList<>();
        String query = "SELECT * FROM siniestros WHERE ID_Poliza = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Siniestro siniestro = mapResultSetToSiniestro(rs);
                siniestros.add(siniestro);
            }
        }
        return siniestros;
    }

    /**
     * Elimina un siniestro de la base de datos.
     * @param idSiniestro El ID del siniestro a eliminar.
     * @return true si el siniestro fue eliminado con éxito, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public boolean deleteSiniestro(int idSiniestro) {
        String sql = "DELETE FROM siniestros WHERE ID_Siniestros = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idSiniestro);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los datos de un siniestro en la base de datos.
     * @param siniestro El siniestro con los datos actualizados.
     * @return true si el siniestro fue actualizado con éxito, false de lo contrario.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public boolean actualizarSiniestro(Siniestro siniestro) throws SQLException {
        String query = "UPDATE siniestros SET "
                       + "Num_Siniestro = ?, "
                       + "Fecha_Siniestro = ?, "
                       + "Descripcion = ?, "
                       + "Lugar_Siniestro = ?, "
                       + "Tipo_Siniestro = ?, "
                       + "Total_Reclamado = ?, "
                       + "Estado_Siniestro = ?, "
                       + "Fecha_Resolucion = ? "
                       + "WHERE ID_Siniestros = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, siniestro.getNumSiniestro());
            statement.setDate(2, java.sql.Date.valueOf(siniestro.getFechaSiniestro()));
            statement.setString(3, siniestro.getDescripcion());
            statement.setString(4, siniestro.getLugarSiniestro());
            statement.setString(5, siniestro.getTipoSiniestro());
            statement.setString(6, siniestro.getTotalReclamado());
            statement.setString(7, siniestro.getEstadoSiniestro());
            statement.setDate(8, java.sql.Date.valueOf(siniestro.getFechaResolucion()));
            statement.setInt(9, siniestro.getIdSiniestro());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}
