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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controlador de la interfaz de gestión de usuarios que permite crear, modificar y eliminar usuarios.
 */
public class FormUsuariosController implements Initializable {

    @FXML private TextField txtUsuario, txtPassword;
    @FXML private TableView<Usuario> usuarioTable;
    @FXML private RadioButton btnAdministrador, btnUsuario;
    @FXML private TableColumn<Usuario, String> clmUsuario, clmRol;
    private final ToggleGroup groupRol = new ToggleGroup();
    private ObservableList<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;

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

    @FXML
    private void handleNuevoUsuario(ActionEvent event) {
        if (txtUsuario.getText().isEmpty() || txtPassword.getText().isEmpty() || groupRol.getSelectedToggle() == null) {
            Util.mostrarAlerta("Datos Incompletos", "Todos los campos son obligatorios.");
            return;
        }
        crearUsuario();
    }

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

    @FXML
    private void handleModificarUsuario(ActionEvent event) {
        if (usuarioSeleccionado == null || txtPassword.getText().isEmpty() || groupRol.getSelectedToggle() == null) {
            Util.mostrarAlerta("Datos Incompletos", "Debe seleccionar un usuario y completar todos los campos necesarios.");
            return;
        }
        actualizarUsuario();
    }

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

    @FXML
    private void handleEliminarUsuario(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            Util.mostrarAlerta("Sin Selección", "Seleccione un usuario para eliminar.");
            return;
        }
        eliminarUsuario();
    }

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

    private void limpiarFormulario() {
        txtUsuario.clear();
        txtPassword.clear();
        btnAdministrador.setSelected(false);
        btnUsuario.setSelected(false);
        txtUsuario.setEditable(true);
        usuarioSeleccionado = null;
    }

    private String getRolSeleccionado() {
        RadioButton selectedRadioButton = (RadioButton) groupRol.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : "";
    }


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
