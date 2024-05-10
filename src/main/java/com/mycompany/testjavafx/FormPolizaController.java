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

    @FXML
    private TextField txtNumPoliza;
    @FXML
    private TextField txtTipoCobertura;
    @FXML
    private TextField txtCoberturaAdicional;
    @FXML
    private TextField txtComentarios;
    @FXML
    private ComboBox<String> cmbComercial;
    @FXML
    private DatePicker dtFechaEmision;
    @FXML
    private DatePicker dtFechaVencimiento;

    private Poliza poliza;
    private int idCliente;
    private boolean isEditMode = false;

    private Map<String, List<String>> itemsMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Código de inicialización aquí.
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

    @FXML
    private void handleCancelar(ActionEvent event) {
        // Obtiene la ventana (Stage) actual desde el evento, cerrándola.
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
