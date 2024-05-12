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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador de la interfaz gráfica para la edición de clientes.
 */
public class FormEditClienteController implements Initializable {

    /**
     * Controlador FXML para la interfaz de gestión de clientes. Este
     * controlador facilita la entrada y modificación de los datos del cliente
     * en la base de datos.
     */
    /**
     * Campo de texto para ingresar el ID único del cliente, generalmente usado
     * como clave primaria en la base de datos.
     */
    @FXML
    private TextField txtIdCliente;

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
     * Campo de texto para ingresar la dirección física del cliente.
     */
    @FXML
    private TextField txtDireccion;

    /**
     * Campo de texto para ingresar el número de teléfono del cliente.
     */
    @FXML
    private TextField txtTelefono;

    /**
     * Campo de texto para ingresar el correo electrónico del cliente.
     */
    @FXML
    private TextField txtMail;

    /**
     * Selector de fecha para ingresar la fecha de nacimiento del cliente.
     */
    @FXML
    private DatePicker dtFecNacimiento;

    /**
     * Campo de texto para ingresar el nivel de estudios del cliente.
     */
    @FXML
    private TextField txtEstudios;

    /**
     * Campo de texto para ingresar el Documento Nacional de Identidad del
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
     * Campo de texto para ingresar observaciones adicionales sobre el cliente.
     */
    @FXML
    private TextField txtObservaciones;

    /**
     * Campo de texto para ingresar información sobre la persona o entidad que
     * refirió al cliente a la empresa.
     */
    @FXML
    private TextField txtReferido;

    /**
     * ComboBox para seleccionar el género del cliente. Las opciones son
     * configuradas desde un mapa de items.
     */
    @FXML
    private ComboBox<String> clmGenero;

    /**
     * ComboBox para seleccionar la nacionalidad del cliente. Las opciones son
     * configuradas desde un mapa de items.
     */
    @FXML
    private ComboBox<String> clmNacionalidad;

    /**
     * ComboBox para seleccionar el estado civil del cliente. Las opciones son
     * configuradas desde un mapa de items.
     */
    @FXML
    private ComboBox<String> clmEstadoCivil;

    /**
     * Instancia del modelo Cliente que representa al cliente actual en el
     * formulario.
     */
    private Cliente cliente;

    /**
     * DAO (Data Access Object) para Cliente que maneja las operaciones de base
     * de datos relacionadas con los clientes.
     */
    private ClienteDAO clienteDAO;

    /**
     * Mapa que contiene listas de strings usadas para los ComboBoxes del
     * formulario. Cada lista está asociada a una clave que representa una
     * categoría de datos, como género, nacionalidad, o estado civil.
     */
    private Map<String, List<String>> itemsMap = new HashMap<>();

    /**
     * Inicializa el controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            itemsMap = Util.loadItemsFromFile();
            clmGenero.getItems().addAll(itemsMap.get("generos"));
            clmNacionalidad.getItems().addAll(itemsMap.get("paises"));
            clmEstadoCivil.getItems().addAll(itemsMap.get("estadocivil"));

        } catch (IOException ex) {
            Logger.getLogger(FormEditClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Configura el cliente que se está editando.
     *
     * @param idCliente El ID del cliente a editar.
     */
    public void initData(int idCliente) {
        try {
            clienteDAO = new ClienteDAO(ConexionMySQL.conectar());
            cliente = clienteDAO.getCliente(idCliente);
            if (cliente != null) {
                cargarDatosCliente(cliente);
            } else {
                Util.mostrarAlerta("Error de Carga", "No se encontró el cliente");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormEditClienteController.class.getName()).log(Level.SEVERE, null, ex);
            Util.mostrarAlerta("Error al cargar los datos del cliente", "No se pudo cargar la información del cliente.");
        }
    }

    /**
     * Carga los datos del cliente en los campos de formulario.
     *
     * @param cliente El cliente cuyos datos deben ser cargados en el
     * formulario.
     */
    private void cargarDatosCliente(Cliente cliente) {
        //txtIdCliente.setText(cliente.getIdCliente() != null ? String.valueOf(cliente.getIdCliente()) : "");
        txtNombre.setText(cliente.getNombre() != null ? cliente.getNombre() : "");
        txtApellido.setText(cliente.getApellido() != null ? cliente.getApellido() : "");
        txtDireccion.setText(cliente.getDireccion() != null ? cliente.getDireccion() : "");
        txtTelefono.setText(cliente.getTelefono() != null ? cliente.getTelefono() : "");
        txtMail.setText(cliente.getMail() != null ? cliente.getMail() : "");
        dtFecNacimiento.setValue(cliente.getFechaNacimiento() != null ? cliente.getFechaNacimiento() : null);
        txtEstudios.setText(cliente.getEstudios() != null ? cliente.getEstudios() : "");
        txtDNI.setText(cliente.getDNI() != null ? cliente.getDNI() : "");
        txtProfesion.setText(cliente.getProfesion() != null ? cliente.getProfesion() : "");
        txtObservaciones.setText(cliente.getObservaciones() != null ? cliente.getObservaciones() : "");
        txtReferido.setText(cliente.getReferido() != null ? cliente.getReferido() : "");

        // Asumiendo que los ComboBox han sido previamente poblados con los valores adecuados
        if (cliente.getGenero() != null) {
            clmGenero.getSelectionModel().select(cliente.getGenero());
        } else {
            clmGenero.getSelectionModel().clearSelection();
        }

        if (cliente.getNacionalidad() != null) {
            clmNacionalidad.getSelectionModel().select(cliente.getNacionalidad());
        } else {
            clmNacionalidad.getSelectionModel().clearSelection();
        }

        if (cliente.getEstadoCivil() != null) {
            clmEstadoCivil.getSelectionModel().select(cliente.getEstadoCivil());
        } else {
            clmEstadoCivil.getSelectionModel().clearSelection();
        }
    }

    /**
     * Maneja el evento de edición del cliente, actualizando sus datos en la
     * base de datos.
     *
     * @param event El evento que disparó este método.
     */
    @FXML
    private void handleEditarCliente(ActionEvent event) {
        if (validarDatosCliente()) {
            actualizarDatosCliente();
            if (clienteDAO.updateCliente(cliente)) {
                Util.mostrarMensaje("Éxito", "Cliente actualizado correctamente.");
                handleCancelar(event);
            } else {
                Util.mostrarAlerta("Error de actualización", "No se pudo actualizar el cliente en la base de datos.");
            }
        } else {
            Util.mostrarAlerta("Datos inválidos", "Por favor, revise los campos y asegúrese de que todos los datos son correctos.");
        }
    }

    /**
     * Valida que los campos del formulario no estén vacíos y cumplan con los
     * requisitos necesarios.
     *
     * @return true si todos los datos son válidos, false en caso contrario.
     */
    private boolean validarDatosCliente() {
        if (txtNombre.getText().trim().isEmpty()
                || txtApellido.getText().trim().isEmpty()
                || txtDireccion.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty()
                || txtMail.getText().trim().isEmpty()
                || txtDNI.getText().trim().isEmpty()
                || dtFecNacimiento.getValue() == null) {
            return false; // Retorna false si alguno de los campos es vacío o la fecha de nacimiento no está establecida.
        }
        return true;
    }

    /**
     * Actualiza los datos del cliente desde los campos de formulario.
     */
    private void actualizarDatosCliente() {
        // Actualizar los campos del cliente con los valores de los campos del formulario
        cliente.setNombre(txtNombre.getText());
        cliente.setApellido(txtApellido.getText());
        cliente.setDireccion(txtDireccion.getText());
        cliente.setTelefono(txtTelefono.getText());
        cliente.setMail(txtMail.getText());
        cliente.setFechaNacimiento(java.sql.Date.valueOf(dtFecNacimiento.getValue()).toLocalDate());
        cliente.setEstudios(txtEstudios.getText());
        cliente.setDNI(txtDNI.getText());
        cliente.setProfesion(txtProfesion.getText());
        cliente.setObservaciones(txtObservaciones.getText());
        cliente.setReferido(txtReferido.getText());
        cliente.setGenero(clmGenero.getValue());
        cliente.setNacionalidad(clmNacionalidad.getValue());
        cliente.setEstadoCivil(clmEstadoCivil.getValue());
    }

    /**
     * Muestra un mensaje de información.
     *
     * @param titulo El título del mensaje.
     * @param contenido El contenido del mensaje.
     */
    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
