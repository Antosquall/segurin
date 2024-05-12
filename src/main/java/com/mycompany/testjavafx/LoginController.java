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

    /**
     * Campo de texto para ingresar el nombre de usuario.
     */
    @FXML
    private TextField usuarioField;

    /**
     * Campo de texto para ingresar la contraseña del usuario.
     */
    @FXML
    private PasswordField contrasenaField;

    /**
     * Botón para salir de la aplicación.
     */
    @FXML
    private Button btnSalir;

    /**
     * Objeto DAO para interactuar con la base de datos de usuarios.
     */
    private UsuarioDAO usuarioDAO;

    /**
     * Objeto que representa al usuario actual.
     */
    private Usuario usuario;

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido
     * completamente procesado. Este método se llama automáticamente después de
     * que se haya cargado el archivo FXML y se haya asociado el controlador.
     * Configura el PasswordField para responder a la tecla Enter, de manera que
     * al presionar Enter en el campo de contraseña, se llama al método
     * iniciarSesion() para iniciar sesión en la aplicación.
     */
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

    /**
     * Abre la ventana principal de la aplicación después de que el usuario
     * inicie sesión con éxito. Carga el archivo FXML que define la interfaz de
     * usuario de la ventana principal, carga el contenido en un objeto Parent,
     * obtiene el controlador de la ventana principal, inicializa los datos del
     * controlador con el usuario que inició sesión, crea un nuevo Stage para la
     * ventana, establece el título y las dimensiones de la ventana, asigna la
     * escena creada a la ventana y muestra la ventana.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
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
