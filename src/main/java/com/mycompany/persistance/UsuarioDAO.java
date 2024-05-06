package com.mycompany.persistance;

import com.mycompany.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para la gestión de usuarios en la base de datos. Proporciona métodos
 * para realizar operaciones CRUD sobre la tabla de usuarios.
 */
public class UsuarioDAO {

    private final Connection connection;

    /**
     * Constructor que inicializa una nueva instancia de UsuarioDAO con una
     * conexión a base de datos.
     *
     * @param connection La conexión a la base de datos.
     */
    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Verifica las credenciales de inicio de sesión de un usuario.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public boolean comprobarUsuario(String username, String password) throws SQLException {
        String query = "SELECT * FROM usuario WHERE nombreUsuario = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Recupera todos los usuarios de la base de datos.
     *
     * @return Una lista de objetos Usuario.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuario";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
        }
        return usuarios;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a insertar.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public void createUsuario(Usuario usuario) throws SQLException {
        String query = "INSERT INTO usuario (nombreUsuario, password, rol) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getPassword()); // Asegúrese de que la contraseña esté encriptada.
            stmt.setString(3, usuario.getRol());
            stmt.executeUpdate();
        }
    }

    /**
     * Actualiza la información de un usuario en la base de datos.
     *
     * @param id El ID del usuario a actualizar.
     * @param nombreUsuario El nuevo nombre de usuario.
     * @param rol El nuevo rol del usuario.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public void modificarUsuario(int id, String nombreUsuario, String rol) throws SQLException {
        String query = "UPDATE usuario SET nombreUsuario = ?, rol = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, rol);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Actualiza la contraseña de un usuario.
     *
     * @param id El ID del usuario.
     * @param nuevaContraseña La nueva contraseña para el usuario.
     * @throws SQLException Si ocurre un error durante la actualización.
     */
    public void modificarPassword(int id, String nuevaContraseña) throws SQLException {
        String query = "UPDATE usuario SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuevaContraseña);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id El ID del usuario a eliminar.
     * @throws SQLException Si ocurre un error durante la eliminación.
     */
    public void eliminarUsuario(int id) throws SQLException {
        String query = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario del usuario a buscar.
     * @return El objeto Usuario si se encuentra, null en caso contrario.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public Usuario buscarUsuarioPorNombre(String nombreUsuario) throws SQLException {
        String query = "SELECT * FROM usuario WHERE nombreUsuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
        }
        return null; // Retorna null si no se encuentra ningún usuario.
    }

    /**
     * Mapea un ResultSet a un objeto Usuario.
     *
     * @param rs El ResultSet que contiene los datos de un usuario.
     * @return Un objeto Usuario poblado con datos desde el ResultSet.
     * @throws SQLException Si ocurre un error al acceder a los datos del
     * ResultSet.
     */
    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombreUsuario"),
                rs.getString("password"), // Considerar la encriptación/desencriptación según sea necesario.
                rs.getString("rol")
        );
    }

    /**
     * Calcula el siguiente ID disponible para una nueva póliza.
     *
     * @return El siguiente ID disponible.
     * @throws SQLException Si ocurre un error durante la consulta SQL.
     */
    public int getNextID() throws SQLException {
        int maxID = 0;
        try (var statement = connection.createStatement(); var resultSet = statement.executeQuery("SELECT MAX(id) AS maxID FROM usuario")) {
            if (resultSet.next()) {
                maxID = resultSet.getInt("maxID");
            }
        }
        return maxID + 1;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET password = ?, rol = ? WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, usuario.getPassword());
            statement.setString(2, usuario.getRol());
            statement.setInt(3, usuario.getId());
            statement.executeUpdate();
        }
    }

     /**
     * Obtiene un usuario de la base de datos basado en el nombre de usuario y la contraseña.
     * @param nombreUsuario El nombre de usuario del usuario a buscar.
     * @param password La contraseña encriptada del usuario a buscar.
     * @return El objeto Usuario si se encuentra, null en caso contrario.
     * @throws SQLException Si ocurre un error durante la consulta.
     */
    public Usuario obtenerUsuario(String nombreUsuario, String password) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE nombreUsuario = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, password);
            
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUsuario(resultSet);
                }
            }
        }
        return null;
    }

}
