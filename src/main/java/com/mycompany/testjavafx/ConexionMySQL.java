package com.mycompany.testjavafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL.
 * Proporciona métodos estáticos para conectar y cerrar conexiones a la base de datos.
 */
public class ConexionMySQL {

    private static final String URL = "jdbc:mysql://localhost:3306/segurin"; // URL de conexión a la base de datos
    private static final String USUARIO = "test"; // Usuario de la base de datos
    private static final String PASSWORD = "Ab_1234567"; // Contraseña del usuario de la base de datos

    /**
     * Establece una conexión con la base de datos MySQL.
     * @return Una conexión activa a la base de datos.
     */
    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Intentar establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    /**
     * Cierra la conexión proporcionada a la base de datos.
     * @param conexion La conexión a cerrar.
     */
    public static void cerrar(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Intenta iniciar sesión verificando las credenciales en la base de datos.
     * @param nombreUsuario El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean iniciarSesion(String nombreUsuario, String password) {
        try (Connection conexion = conectar()) {
            String query = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND password = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(query)) {
                stmt.setString(1, nombreUsuario);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Devuelve true si encuentra al menos una fila
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en inicio de sesión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Constructor que inicializa una conexión al crear una instancia de la clase.
     */
    public ConexionMySQL() {
        conectar();
    }

    /**
     * Método principal para demostración de uso.
     * @param args Argumentos de línea de comando.
     */
    public static void main(String[] args) {
        Connection conexion = conectar();
        cerrar(conexion);
    }
}
