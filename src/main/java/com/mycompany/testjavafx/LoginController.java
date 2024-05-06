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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
            mostrarAlerta("Error de Seguridad", "Detectado intento de inyección SQL.");
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
                mostrarAlerta("Error de Login", "Inicio de sesión fallido.");
            }
        } catch (SQLException | IOException ex) {
            mostrarAlerta("Error al iniciar sesión", "No se pudo iniciar sesión debido a un error: " + ex.getMessage());
        }
    }
    
     private void abrirVentanaPrincipal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = loader.load();
        PrimaryController primaryController = loader.getController();
        primaryController.initData(usuario);  // Paso el usuario al controlador

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Sistema de Gestión");
        stage.show();
    }



    /**
     * Muestra una ventana de alerta con un mensaje específico.
     *
     * @param titulo El título de la ventana de alerta.
     * @param mensaje El mensaje que se mostrará en la ventana de alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Cierra la ventana de login actual.
     */
    private void cerrarVentanaActual() {
        Stage stage = (Stage) usuarioField.getScene().getWindow();
        stage.close();
    }

//    /**
//     * Abre la ventana principal de la aplicación.
//     *
//     * @throws IOException Si hay un error al cargar el recurso FXML para la
//     * ventana principal.
//     */
//    private void abrirVentanaPrincipal() throws IOException {
//    // Cargar primero el FXML y asegurarse de que todos los componentes están listos.
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
//    Parent root = loader.load();  // Esta llamada asegura que todos los @FXML estén cargados.
//
//    // Obtener el controlador y pasar los datos necesarios.
//    PrimaryController primaryController = loader.getController();
//    primaryController.initData(usuario);  // Ahora es seguro llamar a initData.
//
//    // Configurar y mostrar la ventana.
//    Stage stage = new Stage();
//    stage.setMinWidth(1280);
//    stage.setMinHeight(900);
//    stage.setMaxWidth(1280);
//    stage.setMaxHeight(900);
//    stage.setScene(new Scene(root));
//    stage.show();
//}


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
