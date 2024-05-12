package com.mycompany.testjavafx;

import com.mycompany.util.Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.util.ConfigLoader;


/**
 * Clase para gestionar la conexión a la base de datos MySQL. Proporciona
 * métodos estáticos para conectar y cerrar conexiones a la base de datos.
 */
public class ConexionMySQL {

    /**
     * Establece una conexión con la base de datos MySQL.
     *
     * @return Una conexión activa a la base de datos.
     */
    public static Connection conectar() {
        ConfigLoader loader = ConfigLoader.getInstance();
        String url = "jdbc:mysql://" + loader.getProperty("db.url") + ":" + loader.getProperty("db.port") + "/segurin";
        String user = "root";
        String password = "";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            Util.mostrarAlerta("Error de Base de datos.", "No se puede estabecer conexión con la base de datos.");
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Cierra la conexión proporcionada a la base de datos.
     *
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
     *
     * @param nombreUsuario El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public static boolean iniciarSesion(String nombreUsuario, String password) {
        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM usuarios WHERE nombreUsuario = ? AND password = ?")) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Devuelve true si encuentra al menos una fila
            }
        } catch (SQLException e) {
            System.err.println("Error en inicio de sesión: " + e.getMessage());
            return false;
        }
    }

    // Otras partes de la clase se mantienen igual
    public static void main(String[] args) {
        Connection conexion = conectar();
        cerrar(conexion);
    }

}
