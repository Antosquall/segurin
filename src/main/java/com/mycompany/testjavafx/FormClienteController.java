package com.mycompany.testjavafx;

import com.mycompany.model.Cliente;
import com.mycompany.persistance.ClienteDAO;
import com.mycompany.util.Util;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador de la forma de cliente que gestiona la creación y visualización
 * de clientes.
 */
public class FormClienteController implements Initializable {

    /**
     * Campos de la interfaz de usuario para la entrada de datos de un cliente
     * en el sistema. Estos campos están vinculados a elementos FXML en el
     * diseño de la interfaz de usuario y permiten la captura y modificación de
     * la información de un cliente.
     */
    /**
     * Campo de texto para ingresar el nombre del cliente.
     */
    @FXML
    private TextField txtNombre;

    /**
     * Campo de texto para ingresar el apellido del cliente.
     */
    @FXML
    private TextField txtApellido;

    /**
     * Campo de texto para ingresar la dirección del domicilio del cliente.
     */
    @FXML
    private TextField txtDireccion;

    /**
     * Campo de texto para ingresar el número de teléfono del cliente.
     */
    @FXML
    private TextField txtTelfono;

    /**
     * Campo de texto para ingresar el correo electrónico del cliente.
     */
    @FXML
    private TextField txtMail;

    /**
     * Selector de fecha para la fecha de nacimiento del cliente.
     */
    @FXML
    private DatePicker dtFecNacimiento;

    /**
     * Campo de texto para ingresar los estudios o nivel educativo del cliente.
     */
    @FXML
    private TextField txtEstudios;

    /**
     * Campo de texto para ingresar el Documento Nacional de Identidad (DNI) del
     * cliente.
     */
    @FXML
    private TextField txtDNI;

    /**
     * Campo de texto para ingresar la profesión actual del cliente.
     */
    @FXML
    private TextField txtProfesion;

    /**
     * Campo de texto para ingresar cualquier observación adicional sobre el
     * cliente.
     */
    @FXML
    private TextField txtObservaciones;

    /**
     * Campo de texto para ingresar información sobre quién refirió al cliente,
     * si aplica.
     */
    @FXML
    private TextField txtReferido;

    /**
     * ComboBox para seleccionar el género del cliente. Las opciones son
     * cargadas desde un archivo o una fuente externa.
     */
    @FXML
    private ComboBox<String> clmGenero;

    /**
     * ComboBox para seleccionar la nacionalidad del cliente. Las opciones son
     * cargadas desde un archivo o una fuente externa.
     */
    @FXML
    private ComboBox<String> clmNacionalidad;

    /**
     * ComboBox para seleccionar el estado civil del cliente. Las opciones son
     * cargadas desde un archivo o una fuente externa.
     */
    @FXML
    private ComboBox<String> clmEstadoCivil;

    /**
     * Instancia del modelo Cliente que representa al cliente siendo creado o
     * editado.
     */
    Cliente cliente;

    /**
     * Mapa que contiene listas de elementos para los ComboBox, cargados desde
     * un archivo. Las claves del mapa representan diferentes categorías de
     * datos como género, nacionalidad, y estado civil.
     */
    private Map<String, List<String>> itemsMap = new HashMap<>();

    /**
     * Inicializa el controlador y prepara los campos y listas desplegables.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            itemsMap = Util.loadItemsFromFile();
            clmGenero.getItems().addAll(itemsMap.get("generos"));
            clmNacionalidad.getItems().addAll(itemsMap.get("paises"));
            clmEstadoCivil.getItems().addAll(itemsMap.get("estadocivil"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Muestra la ventana principal del formulario del cliente.
     */
    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formCliente.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setMinWidth(600);
            stage.setMinHeight(600);
            stage.setMaxWidth(600);
            stage.setMaxHeight(600);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Maneja la acción de crear un nuevo cliente en la base de datos.
     *
     * @param event La acción que disparó el evento.
     */
    @FXML
    private void handleCrearCliente(ActionEvent event) {
        limpiarEstilosError();
        if (!validarCampos()) {
            Util.mostrarAlerta("Error de Validación", "No se puede crear el cliente. Por favor, corrija los campos marcados en rojo.");
        } else {
            try {
                ClienteDAO clientedao = new ClienteDAO(ConexionMySQL.conectar());
                cliente = new Cliente(
                        clientedao.getNextID(),
                        txtDNI.getText(),
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtDireccion.getText(),
                        txtTelfono.getText(),
                        txtMail.getText(),
                        dtFecNacimiento.getValue(),
                        clmGenero.getValue(),
                        0,
                        0,
                        clmEstadoCivil.getValue(),
                        0,
                        txtProfesion.getText(),
                        txtEstudios.getText(),
                        0,
                        null,
                        null,
                        txtObservaciones.getText(),
                        clmNacionalidad.getValue(),
                        txtReferido.getText(),
                        null);

                clientedao.createCliente(cliente);
                Util.mostrarMensaje("Cliente creado.", "Alta de " + txtNombre.getText() + " " + txtApellido.getText());
                handleCancelar(event);
            } catch (SQLException ex) {
                Logger.getLogger(FormClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Limpia los estilos de error de todos los campos de entrada.
     */
    private void limpiarEstilosError() {
        txtNombre.getStyleClass().remove("text-field-error");
        txtApellido.getStyleClass().remove("text-field-error");
        txtTelfono.getStyleClass().remove("text-field-error");
        dtFecNacimiento.getStyleClass().remove("text-field-error");
        clmGenero.getStyleClass().remove("combo-box-error");
        clmNacionalidad.getStyleClass().remove("combo-box-error");
        txtDNI.getStyleClass().remove("text-field-error");
    }

    /**
     * Verifica si los campos del formulario son válidos.
     *
     * @return true si todos los campos requeridos son válidos, false en caso
     * contrario.
     */
    private boolean validarCampos() {
        boolean valido = true;
        if (txtNombre.getText().trim().isEmpty()) {
            txtNombre.getStyleClass().add("text-field-error");
            valido = false;
        }
        if (txtApellido.getText().trim().isEmpty()) {
            txtApellido.getStyleClass().add("text-field-error");
            valido = false;
        }
        if (txtTelfono.getText().trim().isEmpty()) {
            txtTelfono.getStyleClass().add("text-field-error");
            valido = false;
        }
        if (dtFecNacimiento.getValue() == null) {
            dtFecNacimiento.getStyleClass().add("text-field-error");
            valido = false;
        }
        if (clmGenero.getValue() == null) {
            clmGenero.getStyleClass().add("combo-box-error");
            valido = false;
        }
        if (clmNacionalidad.getValue() == null) {
            clmNacionalidad.getStyleClass().add("combo-box-error");
            valido = false;
        }
        if (txtDNI.getText().trim().isEmpty() || !checkDNI(txtDNI.getText())) {
            txtDNI.getStyleClass().add("text-field-error");
            valido = false;
        }
        return valido;
    }

    /**
     * Verifica la validez del formato del DNI.
     *
     * @param dni El DNI a verificar.
     * @return true si el DNI es válido, false en caso contrario.
     */
    private boolean checkDNI(String dni) {
        return dni.matches("\\d{0,8}[A-Za-z]?");
    }

    /**
     * Cierra la ventana actual. Este método puede ser llamado en respuesta a un
     * evento, por ejemplo, un clic de botón.
     *
     * @param event El evento que disparó la llamada al método.
     */
    public void cerrarVentanaActual(ActionEvent event) {
        // Obtiene el nodo que originó el evento
        Node source = (Node) event.getSource();
        // Obtiene la ventana (Stage) que contiene este nodo
        Stage stage = (Stage) source.getScene().getWindow();
        // Cierra la ventana
        stage.close();
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        // Obtiene la ventana (Stage) actual desde el evento, cerrándola.
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
