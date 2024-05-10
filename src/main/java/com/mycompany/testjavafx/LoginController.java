package com.mycompany.testjavafx;

import com.mycompany.model.Usuario;
import com.mycompany.persistance.UsuarioDAO;
import com.mycompany.util.SecurePassword;
import com.mycompany.util.Util;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Controlador para la interfaz de login.
 */
public class LoginController {

    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private Button btnSalir;

    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

     @FXML
    public void initialize() {
        // Configura el TextField para responder a la tecla Enter
        contrasenaField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                iniciarSesion();
            }
        });
    }
    
    
    /**
     * Intenta iniciar sesión utilizando las credenciales proporcionadas en los
     * campos de texto. Si la autenticación es exitosa, cambia a la ventana
     * principal de la aplicación. Si falla, muestra un mensaje de error en una
     * ventana de alerta.
     */
    @FXML
    private void iniciarSesion() {
        String nombreUsuario = usuarioField.getText().trim();
        String password = contrasenaField.getText();

        if (Util.verificarInyeccionSQL(nombreUsuario) || Util.verificarInyeccionSQL(password)) {
            Util.mostrarAlerta("Error de Seguridad", "Detectado intento de inyección SQL.");
            return;
        }

        String passEncriptada = SecurePassword.encryptPassword(password);
        usuarioDAO = new UsuarioDAO(ConexionMySQL.conectar());

        try {
            usuario = usuarioDAO.obtenerUsuario(nombreUsuario, passEncriptada);
            if (usuario != null) {
                cerrarVentanaActual();
                abrirVentanaPrincipal();
            } else {
                Util.mostrarAlerta("Error de Login", "Inicio de sesión fallido.");
            }
        } catch (SQLException | IOException ex) {
            Util.mostrarAlerta("Error al iniciar sesión", "No se pudo iniciar sesión debido a un error: " + ex.getMessage());
        }
    }

    private void abrirVentanaPrincipal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = loader.load();
        PrimaryController primaryController = loader.getController();
        primaryController.initData(usuario); 

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Sistema de Gestión");
        stage.setMaxWidth(1920);
        stage.setMinWidth(1024);
        stage.setHeight(1600);
        stage.setMaxHeight(1080);
        stage.setMinHeight(768);
        stage.setHeight(900);
        
        stage.show();
    }

    /**
     * Cierra la ventana de login actual.
     */
    private void cerrarVentanaActual() {
        Stage stage = (Stage) usuarioField.getScene().getWindow();
        stage.close();
    }

    /**
     * Maneja la acción del botón salir, cerrando la aplicación.
     *
     * @param event El evento que disparó este método.
     */
    @FXML
    private void handleSalir(ActionEvent event) {
        Platform.exit();
    }
}
