package com.mycompany.testjavafx;

import com.mycompany.model.Poliza;
import com.mycompany.persistance.PolizaDAO;
import com.mycompany.util.Util;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Clase controladora FXML para gestionar pólizas de seguro.
 */
public class FormPolizaController implements Initializable {

    /**
     * Controlador FXML para la gestión de pólizas de seguro. Este controlador
     * maneja la entrada de datos de pólizas, permitiendo crear nuevas pólizas o
     * editar pólizas existentes.
     */
    /**
     * Campo de texto para ingresar el número de la póliza.
     */
    @FXML
    private TextField txtNumPoliza;

    /**
     * Campo de texto para ingresar el tipo de cobertura de la póliza.
     */
    @FXML
    private TextField txtTipoCobertura;

    /**
     * Campo de texto para ingresar cualquier cobertura adicional asociada con
     * la póliza.
     */
    @FXML
    private TextField txtCoberturaAdicional;

    /**
     * Campo de texto para ingresar comentarios relacionados con la póliza.
     */
    @FXML
    private TextField txtComentarios;

    /**
     * ComboBox para seleccionar el comercial asociado a la póliza. Los
     * elementos del ComboBox se cargan dinámicamente desde un archivo.
     */
    @FXML
    private ComboBox<String> cmbComercial;

    /**
     * Selector de fecha para la fecha de emisión de la póliza.
     */
    @FXML
    private DatePicker dtFechaEmision;

    /**
     * Selector de fecha para la fecha de vencimiento de la póliza.
     */
    @FXML
    private DatePicker dtFechaVencimiento;

    /**
     * Representa la instancia de la póliza que se está creando o editando.
     */
    private Poliza poliza;

    /**
     * Identificador del cliente asociado a la póliza, utilizado en caso de
     * creación de una nueva póliza.
     */
    private int idCliente;

    /**
     * Indica si el formulario está en modo de edición, lo que significa que se
     * está editando una póliza existente, en lugar de crear una nueva.
     */
    private boolean isEditMode = false;

    /**
     * Mapa que almacena listas de elementos para los ComboBox, cargados desde
     * un archivo. Las claves del mapa representan diferentes tipos de datos
     * como 'comercial', entre otros.
     */
    private Map<String, List<String>> itemsMap = new HashMap<>();

    /**
     * Inicializa el controlador después de que sus elementos de la interfaz de
     * usuario han sido completamente cargados. Configura los listeners y carga
     * inicial de datos necesarios para el correcto funcionamiento del
     * formulario. Este método establece un listener en el campo de fecha de
     * emisión para auto-configurar la fecha de vencimiento, y carga
     * dinámicamente las opciones del comboBox de comerciales desde un archivo.
     *
     * @param url El URL que se usó para cargar el FXML y que puede ser usado
     * para resolver rutas relativas para el objeto raíz, o null si el FXML no
     * fue cargado desde un URL.
     * @param rb El recurso usado para localizar la raíz del objeto, o null si
     * el recurso no está localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Código de inicialización aquí.
        fijarFecha();
        try {
            itemsMap = Util.loadItemsFromFile();
            cmbComercial.getItems().addAll(itemsMap.get("comercial"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Inicializa el formulario con un objeto Poliza para su edición.
     *
     * @param poliza El objeto Poliza a editar.
     */
    public void initData(Poliza poliza) {
        this.poliza = poliza;
        if (poliza != null) {
            cargarDatosPoliza();
            isEditMode = true;
        }
    }

    /**
     * Inicializa el formulario con un ID de cliente para una nueva póliza.
     *
     * @param idCliente El ID del cliente para quien se crea la póliza.
     */
    public void initData(int idCliente) {
        this.idCliente = idCliente;
        this.poliza = new Poliza(); // Crea una nueva instancia si es una nueva entrada
        isEditMode = false;
    }

    /**
     * Carga los datos de la póliza en los campos del formulario.
     */
    private void cargarDatosPoliza() {
        txtNumPoliza.setText(poliza.getNumeroPoliza());
        dtFechaEmision.setValue(poliza.getFechaEmision());
        dtFechaVencimiento.setValue(poliza.getFechaVencimiento());
        txtTipoCobertura.setText(poliza.getTipoCobertura());
        txtCoberturaAdicional.setText(poliza.getCoberturaAdicional());
        cmbComercial.getSelectionModel().select(poliza.getComercial());
        txtComentarios.setText(poliza.getComentarios());
    }

    /**
     * Maneja el evento de creación o actualización de una póliza de seguro.
     * Limpia los estilos de error, valida los campos del formulario y, si son
     * correctos, configura y guarda los datos de la póliza en la base de datos.
     * Muestra mensajes relevantes según el resultado de la operación y cierra
     * la ventana en caso de éxito.
     *
     * @param event El evento de acción que se activa al hacer clic en el botón
     * de guardar. Contiene información sobre el contexto del evento.
     * @throws SQLException Si ocurre un error al interactuar con la base de
     * datos, se captura y se maneja mostrando un mensaje de alerta al usuario.
     */
    @FXML
    private void handleCrearPoliza(ActionEvent event) {
        limpiarEstilosDeError();
        if (validarCampos()) {
            configurarDatosPolizaDesdeFormulario();
            try {
                PolizaDAO polizaDao = new PolizaDAO(ConexionMySQL.conectar());
                if (isEditMode) {
                    polizaDao.updatePoliza(poliza);
                    txtNumPoliza.setEditable(false);
                } else {
                    poliza.setIdPoliza(polizaDao.getNextID());
                    polizaDao.addPoliza(poliza);
                }
                Util.mostrarMensaje("Operación Exitosa", "La póliza ha sido guardada correctamente.");
                handleCancelar(event);
            } catch (SQLException ex) {
                Util.mostrarAlerta("Error de Base de Datos", "No se pudo guardar la póliza. " + ex.getMessage());
            }
        } else {
            Util.mostrarAlerta("Error de Validación", "Por favor, corrija los campos marcados en rojo.");
        }
    }

    /**
     * Configura la instancia de Poliza con datos desde los campos del
     * formulario.
     */
    private void configurarDatosPolizaDesdeFormulario() {
        poliza.setNumeroPoliza(txtNumPoliza.getText());
        poliza.setFechaEmision(dtFechaEmision.getValue());
        poliza.setFechaVencimiento(dtFechaVencimiento.getValue());
        poliza.setTipoCobertura(txtTipoCobertura.getText());
        poliza.setCoberturaAdicional(txtCoberturaAdicional.getText());
        poliza.setComercial(cmbComercial.getValue().toString());
        poliza.setComentarios(txtComentarios.getText());
        poliza.setID_Cliente(idCliente);
    }

    /**
     * Limpia todos los estilos de error de los campos del formulario.
     */
    private void limpiarEstilosDeError() {
        txtNumPoliza.getStyleClass().remove("text-field-error");
        txtTipoCobertura.getStyleClass().remove("text-field-error");
        txtCoberturaAdicional.getStyleClass().remove("text-field-error");
        dtFechaEmision.getStyleClass().remove("text-field-error");
        dtFechaVencimiento.getStyleClass().remove("text-field-error");
        cmbComercial.getStyleClass().remove("combo-box-error");
    }

    /**
     * Valida que los campos del formulario no estén vacíos y estén
     * correctamente formateados.
     *
     * @return true si todos los campos son válidos, false si alguno no lo es.
     */
    private boolean validarCampos() {
        boolean esValido = true;
        limpiarEstilosDeError(); // Método para limpiar estilos de error antes de validar

        if (txtNumPoliza.getText().isEmpty()) {
            txtNumPoliza.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtTipoCobertura.getText().isEmpty()) {
            txtTipoCobertura.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtCoberturaAdicional.getText().isEmpty()) {
            txtCoberturaAdicional.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (dtFechaEmision.getValue() == null) {
            dtFechaEmision.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (dtFechaVencimiento.getValue() == null) {
            dtFechaVencimiento.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (cmbComercial.getValue() == null) {
            cmbComercial.getStyleClass().add("combo-box-error");
            esValido = false;
        }

        return esValido;
    }

    /**
     * Controlador de acción que se invoca al presionar un botón para cancelar.
     * Cierra la ventana (Stage) actual.
     *
     * @param event El evento de acción que contiene la información del contexto
     * en el que se produjo el evento.
     */
    @FXML
    private void handleCancelar(ActionEvent event) {
        // Obtiene la ventana (Stage) actual desde el evento, cerrándola.
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Configura un listener para el campo de fecha de emisión que ajusta
     * automáticamente la fecha de vencimiento a un año después de la fecha
     * seleccionada, si la fecha de vencimiento no está ya establecida o está
     * deshabilitada. También carga los items disponibles para el comboBox de
     * comerciales desde un archivo.
     */
    private void fijarFecha() {
        // Listener que ajusta la fecha de vencimiento
        dtFechaEmision.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null && (dtFechaVencimiento.getValue() == null || dtFechaVencimiento.isDisabled())) {
                dtFechaVencimiento.setValue(newDate.plusYears(1));
            }
        });

        try {
            itemsMap = Util.loadItemsFromFile();
            cmbComercial.getItems().addAll(itemsMap.get("comercial"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
