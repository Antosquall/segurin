package com.mycompany.testjavafx;

import com.mycompany.model.Usuario;
import com.mycompany.persistance.UsuarioDAO;
import com.mycompany.util.SecurePassword;
import com.mycompany.util.Util;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controlador de la interfaz de gestión de usuarios que permite crear,
 * modificar y eliminar usuarios.
 */
public class FormUsuariosController implements Initializable {

    /**
     * Campo de texto para ingresar el nombre de usuario.
     */
    @FXML
    private TextField txtUsuario;

    /**
     * Campo de texto para ingresar la contraseña del usuario.
     */
    @FXML
    private TextField txtPassword;

    /**
     * Tabla que muestra la lista de usuarios.
     */
    @FXML
    private TableView<Usuario> usuarioTable;

    /**
     * Botón de opción para seleccionar el rol de administrador.
     */
    @FXML
    private RadioButton btnAdministrador;

    /**
     * Botón de opción para seleccionar el rol de usuario normal.
     */
    @FXML
    private RadioButton btnUsuario;

    /**
     * Columna en la tabla que muestra el nombre de usuario.
     */
    @FXML
    private TableColumn<Usuario, String> clmUsuario;

    /**
     * Columna en la tabla que muestra el rol del usuario.
     */
    @FXML
    private TableColumn<Usuario, String> clmRol;

    /**
     * Grupo de botones de opción que permite seleccionar entre los roles de
     * administrador y usuario.
     */
    private final ToggleGroup groupRol = new ToggleGroup();

    /**
     * Lista observable que contiene la lista de usuarios para mostrar en la
     * tabla.
     */
    private ObservableList<Usuario> listaUsuarios;

    /**
     * Usuario seleccionado en la tabla.
     */
    private Usuario usuarioSeleccionado;

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido
     * completamente procesado. Este método se llama automáticamente después de
     * que se haya cargado el archivo FXML y se haya asociado el controlador.
     *
     * @param url La ubicación utilizada para resolver rutas relativas para el
     * objeto raíz o null si no está disponible.
     * @param rb El recurso utilizado para localizar objetos de la interfaz de
     * usuario, o null si no está disponible.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAdministrador.setToggleGroup(groupRol);
        btnUsuario.setToggleGroup(groupRol);
        listaUsuarios = FXCollections.observableArrayList();
        cargarTabla();
        usuarioTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            if (usuarioSeleccionado != null) {
                txtUsuario.setText(usuarioSeleccionado.getNombreUsuario());
                txtPassword.clear();
                btnAdministrador.setSelected("administrador".equals(usuarioSeleccionado.getRol()));
                btnUsuario.setSelected("usuario".equals(usuarioSeleccionado.getRol()));
            }
        });
    }

    /**
     * Carga los datos de los usuarios en la tabla de la interfaz de usuario. Se
     * conecta a la base de datos, obtiene la lista de usuarios a través del
     * objeto UsuarioDAO, asigna los usuarios a la lista observable y establece
     * la lista como el conjunto de datos de la tabla. Además, configura las
     * propiedades de las celdas de la tabla para mostrar el nombre de usuario y
     * el rol.
     */
    private void cargarTabla() {
        try (var connection = ConexionMySQL.conectar()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            listaUsuarios.setAll(usuarioDAO.getAllUsuarios());
            usuarioTable.setItems(listaUsuarios);
            clmUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
            clmRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        } catch (SQLException ex) {
            Logger.getLogger(FormUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            Util.mostrarAlerta("Error de Conexión", "No se pudo cargar la información de los usuarios.");
        }
    }

    /**
     * Maneja el evento de creación de un nuevo usuario. Verifica si los campos
     * de nombre de usuario, contraseña y rol están vacíos. Si alguno de estos
     * campos está vacío, muestra una alerta indicando que todos los campos son
     * obligatorios. De lo contrario, llama al método crearUsuario() para crear
     * el nuevo usuario.
     *
     * @param event El evento de acción que desencadenó este método.
     */
    @FXML
    private void handleNuevoUsuario(ActionEvent event) {
        if (txtUsuario.getText().isEmpty() || txtPassword.getText().isEmpty() || groupRol.getSelectedToggle() == null) {
            Util.mostrarAlerta("Datos Incompletos", "Todos los campos son obligatorios.");
            return;
        }
        crearUsuario();
    }

    /**
     * Crea un nuevo usuario con los datos proporcionados en los campos de la
     * interfaz de usuario. Se conecta a la base de datos, crea un objeto
     * Usuario con el nombre de usuario, contraseña y rol proporcionados,
     * utiliza UsuarioDAO para insertar el nuevo usuario en la base de datos,
     * recarga la tabla de usuarios, limpia el formulario y muestra un mensaje
     * de éxito si la operación se realiza correctamente. En caso de error al
     * conectarse a la base de datos o al crear el usuario, muestra una alerta
     * de error.
     */
    private void crearUsuario() {
        try (var connection = ConexionMySQL.conectar()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            Usuario usuario = new Usuario(0, txtUsuario.getText(), SecurePassword.encryptPassword(txtPassword.getText()), getRolSeleccionado());
            usuarioDAO.createUsuario(usuario);
            cargarTabla();
            limpiarFormulario();
            Util.mostrarMensaje("Éxito", "Usuario creado correctamente.");
        } catch (SQLException ex) {
            Logger.getLogger(FormUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            Util.mostrarAlerta("Error al Crear", "No se pudo crear el usuario.");
        }
    }

    /**
     * Maneja el evento de modificación de un usuario existente. Verifica si se
     * ha seleccionado un usuario en la tabla y si los campos de contraseña y
     * rol están vacíos. Si alguno de estos campos está vacío o no se ha
     * seleccionado ningún usuario, muestra una alerta indicando que se deben
     * seleccionar un usuario y completar todos los campos necesarios. De lo
     * contrario, llama al método actualizarUsuario() para modificar el usuario
     * seleccionado.
     *
     * @param event El evento de acción que desencadenó este método.
     */
    @FXML
    private void handleModificarUsuario(ActionEvent event) {
        if (usuarioSeleccionado == null || txtPassword.getText().isEmpty() || groupRol.getSelectedToggle() == null) {
            Util.mostrarAlerta("Datos Incompletos", "Debe seleccionar un usuario y completar todos los campos necesarios.");
            return;
        }
        actualizarUsuario();
    }

    /**
     * Actualiza los datos del usuario seleccionado con los valores
     * proporcionados en los campos de la interfaz de usuario. Se conecta a la
     * base de datos, actualiza la contraseña y el rol del usuario seleccionado
     * con los nuevos valores, utilizando UsuarioDAO para actualizar el usuario
     * en la base de datos, recarga la tabla de usuarios, limpia el formulario y
     * muestra un mensaje de éxito si la operación se realiza correctamente. En
     * caso de error al conectarse a la base de datos o al actualizar el
     * usuario, muestra una alerta de error.
     */
    private void actualizarUsuario() {
        try (var connection = ConexionMySQL.conectar()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            usuarioSeleccionado.setPassword(SecurePassword.encryptPassword(txtPassword.getText()));
            usuarioSeleccionado.setRol(getRolSeleccionado());
            usuarioDAO.actualizarUsuario(usuarioSeleccionado);
            cargarTabla();
            limpiarFormulario();
            Util.mostrarMensaje("Éxito", "Usuario actualizado correctamente.");
        } catch (SQLException ex) {
            Logger.getLogger(FormUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            Util.mostrarAlerta("Error al Actualizar", "No se pudo actualizar el usuario.");
        }
    }

    /**
     * Maneja el evento de eliminación de un usuario. Verifica si se ha
     * seleccionado un usuario en la tabla. Si no se ha seleccionado ningún
     * usuario, muestra una alerta indicando que se debe seleccionar un usuario
     * para eliminar. De lo contrario, llama al método eliminarUsuario() para
     * eliminar el usuario seleccionado.
     *
     * @param event El evento de acción que desencadenó este método.
     */
    @FXML
    private void handleEliminarUsuario(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            Util.mostrarAlerta("Sin Selección", "Seleccione un usuario para eliminar.");
            return;
        }
        eliminarUsuario();
    }

    /**
     * Elimina el usuario seleccionado de la base de datos. Se conecta a la base
     * de datos, utiliza UsuarioDAO para eliminar el usuario seleccionado con su
     * ID, recarga la tabla de usuarios, limpia el formulario y muestra un
     * mensaje de éxito si la operación se realiza correctamente. En caso de
     * error al conectarse a la base de datos o al eliminar el usuario, muestra
     * una alerta de error.
     */
    private void eliminarUsuario() {
        try (var connection = ConexionMySQL.conectar()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            usuarioDAO.eliminarUsuario(usuarioSeleccionado.getId());
            cargarTabla();
            limpiarFormulario();
            Util.mostrarMensaje("Éxito", "Usuario eliminado correctamente.");
        } catch (SQLException ex) {
            Logger.getLogger(FormUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            Util.mostrarAlerta("Error al Eliminar", "No se pudo eliminar el usuario seleccionado.");
        }
    }

    /**
     * Limpia los campos del formulario de usuario en la interfaz de usuario.
     * Limpia el campo de nombre de usuario y contraseña, deselecciona los
     * botones de opción de rol, habilita la edición del campo de nombre de
     * usuario y establece el usuario seleccionado como nulo.
     */
    private void limpiarFormulario() {
        txtUsuario.clear();
        txtPassword.clear();
        btnAdministrador.setSelected(false);
        btnUsuario.setSelected(false);
        txtUsuario.setEditable(true);
        usuarioSeleccionado = null;
    }

    /**
     * Obtiene el rol seleccionado entre los botones de opción de rol. Recupera
     * el botón de opción de rol seleccionado del grupo de botones de opción de
     * rol. Si se ha seleccionado un botón de opción, devuelve el texto asociado
     * a ese botón (el rol seleccionado). Si no se ha seleccionado ningún botón
     * de opción, devuelve una cadena vacía.
     *
     * @return El rol seleccionado, o una cadena vacía si no hay ningún botón de
     * opción seleccionado.
     */
    private String getRolSeleccionado() {
        RadioButton selectedRadioButton = (RadioButton) groupRol.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : "";
    }

    /**
     * Muestra la ventana principal de la aplicación, que contiene el formulario
     * de usuarios. Carga el archivo FXML que define la interfaz de usuario del
     * formulario de usuarios, carga el contenido en un objeto Parent, crea un
     * nuevo Stage para la ventana, establece las dimensiones mínimas y máximas
     * de la ventana, asigna la escena creada a la ventana y muestra la ventana.
     *
     * En caso de error al cargar el archivo FXML o al mostrar la ventana,
     * imprime el mensaje de error en la consola.
     */
    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formUsuarios.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.setMaxWidth(600);
            stage.setMaxHeight(400);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
