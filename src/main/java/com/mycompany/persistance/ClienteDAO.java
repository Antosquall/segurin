package com.mycompany.persistance;

import com.mycompany.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos para la entidad Cliente.
 * 
 * Proporciona métodos para obtener, crear, actualizar y eliminar clientes de la base de datos.
 */
public class ClienteDAO {

    /**
     * Conexión a la base de datos.
     */
    private final Connection connection;

    /**
     * Crea una instancia de ClienteDAO con una conexión a la base de datos proporcionada.
     * 
     * @param connection la conexión a la base de datos.
     */
    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtiene una lista de todos los clientes de la base de datos.
     * 
     * @return una lista de objetos Cliente.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Cliente> getAllClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        try (var statement = connection.createStatement(); var resultSet = statement.executeQuery("SELECT * FROM clientes")) {
            while (resultSet.next()) {
                Cliente cliente = mapResultSetToCliente(resultSet);
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    /**
     * Obtiene un cliente específico de la base de datos por su identificador.
     * 
     * @param id_cliente el identificador del cliente.
     * @return el objeto Cliente correspondiente al identificador proporcionado, o null si no se encuentra.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public Cliente getCliente(int id_cliente) throws SQLException {
        Cliente cliente = null;
        String query = "SELECT * FROM clientes WHERE ID_Cliente = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id_cliente);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    cliente = mapResultSetToCliente(resultSet);
                }
            }
        }
        return cliente;
    }
    
    /**
     * Actualiza los datos de un cliente en la base de datos.
     * @param cliente El cliente a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean updateCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, direccion = ?, telefono = ?, mail = ?, dni = ?, fec_nacimiento = ?, estudios = ?, profesion = ?, observaciones = ?, referido = ? WHERE id_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellido());
            statement.setString(3, cliente.getDireccion());
            statement.setString(4, cliente.getTelefono());
            statement.setString(5, cliente.getMail());
            statement.setString(6, cliente.getDNI());
            statement.setDate(7, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            statement.setString(8, cliente.getEstudios());
            statement.setString(9, cliente.getProfesion());
            statement.setString(10, cliente.getObservaciones());
            statement.setString(11, cliente.getReferido());
            statement.setInt(12, cliente.getIdCliente());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos por su identificador.
     * 
     * @param id el identificador del cliente a eliminar.
     * @return true si la eliminación se realizó con éxito, false en caso contrario.
     */
    public boolean deleteCLiente(int id) {
        String query = "DELETE FROM clientes WHERE ID_Cliente = ?";
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
     * Obtiene el siguiente identificador disponible para un nuevo cliente.
     * 
     * @return el siguiente identificador entero válido que no esté siendo utilizado por ningún cliente existente.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public int getNextID() throws SQLException {
        int maxID = 0;
        try (var statement = connection.createStatement(); var resultSet = statement.executeQuery("SELECT MAX(ID_Cliente) AS maxID FROM clientes")) {

            if (resultSet.next()) {
                maxID = resultSet.getInt("maxID");
            }

        }
        return maxID + 1;
    }

    /**
     * Crea un nuevo cliente en la base de datos.
     * 
     * @param cliente el objeto Cliente que representa los datos del nuevo cliente.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void createCliente(Cliente cliente) {

        String query = "INSERT INTO clientes (ID_Cliente, DNI, Nombre, Apellido, Direccion, Telefono, Mail, Fec_Nacimiento, Genero, Total_Polizas, Bonificacion, Estado_Civil, Num_Parientes, Profesion, Estudios, Ingresos_Anuales, Observaciones, Nacionalidad, Referido, VIP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getDNI());
            stmt.setString(3, cliente.getNombre());
            stmt.setString(4, cliente.getApellido());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getTelefono());
            stmt.setString(7, cliente.getMail());
            stmt.setDate(8, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            stmt.setString(9, cliente.getGenero());
            stmt.setInt(10, cliente.getTotalPolizas());
            stmt.setInt(11, cliente.getBonificacion());
            stmt.setString(12, cliente.getEstadoCivil());
            stmt.setInt(13, cliente.getNumParientes());
            stmt.setString(14, cliente.getProfesion());
            stmt.setString(15, cliente.getEstudios());
            stmt.setInt(16, cliente.getIngresosAnuales());
            stmt.setString(17, cliente.getObservaciones());
            stmt.setString(18, cliente.getNacionalidad());
            stmt.setString(19, cliente.getReferido());
            stmt.setString(20, cliente.getVip());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Mapea un resultado de la consulta a un objeto Cliente.
     * 
     * @param resultSet el resultado de la consulta a la base de datos.
     * @return el objeto Cliente correspondiente a los datos obtenidos.
     * @throws SQLException si ocurre un error al procesar el resultado de la consulta.
     */
    private Cliente mapResultSetToCliente(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(resultSet.getInt("ID_Cliente"));
        cliente.setDNI(resultSet.getString("DNI"));
        cliente.setNombre(resultSet.getString("Nombre"));
        cliente.setApellido(resultSet.getString("Apellido"));
        cliente.setDireccion(resultSet.getString("Direccion"));
        cliente.setTelefono(resultSet.getString("Telefono"));
        cliente.setMail(resultSet.getString("Mail"));
        cliente.setFechaNacimiento(resultSet.getDate("Fec_Nacimiento").toLocalDate());
        cliente.setGenero(resultSet.getString("Genero"));
        cliente.setTotalPolizas(resultSet.getInt("Total_Polizas"));
        cliente.setBonificacion(resultSet.getInt("Bonificacion"));
        cliente.setEstadoCivil(resultSet.getString("Estado_civil"));
        cliente.setNumParientes(resultSet.getInt("Num_Parientes"));
        cliente.setProfesion(resultSet.getString("Profesion"));
        cliente.setEstudios(resultSet.getString("Estudios"));
        cliente.setIngresosAnuales(resultSet.getInt("Ingresos_Anuales"));
        cliente.setFechaRegistro(resultSet.getDate("Fecha_Registro").toLocalDate());
        cliente.setFechaBaja(resultSet.getDate("Fecha_Baja") != null ? resultSet.getDate("Fecha_Baja").toLocalDate() : null);
        cliente.setObservaciones(resultSet.getString("Observaciones"));
        cliente.setNacionalidad(resultSet.getString("Nacionalidad"));
        cliente.setReferido(resultSet.getString("Referido"));
        cliente.setVip(resultSet.getString("Vip"));
        return cliente;
    }
}
