package com.mycompany.testjavafx;

import com.mycompany.model.Siniestro;
import com.mycompany.persistance.SiniestroDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormSiniestroController implements Initializable {

    @FXML
    private TextField txtNumSiniestro;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtLugarSiniestro;
    @FXML
    private TextField txtTipoSiniestro;
    @FXML
    private TextField txtTotalReclamado;
    @FXML
    private TextField txtEstadoSiniestro;
    @FXML
    private DatePicker dpFechaSiniestro;
    @FXML
    private DatePicker dpFechaResolucion;

    private Siniestro siniestro = new Siniestro();
    private int idPoliza;

    private boolean isEditMode = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí podrías cargar datos adicionales si es necesario
    }

    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formSiniestro.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.setMaxWidth(900);
            stage.setMaxHeight(600);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void handleCrearSiniestro(ActionEvent event) {
        if (validarCampos()) {
            configurarDatosSiniestroDesdeFormulario();
            try {
                SiniestroDAO siniestroDao = new SiniestroDAO(ConexionMySQL.conectar());
                if (isEditMode) {
                    // Actualizar el siniestro existente
                    if (siniestroDao.actualizarSiniestro(siniestro)) {
                        System.out.println("Siniestro actualizado correctamente.");
                    } else {
                        mostrarMensajeDeError("Error al actualizar", "No se pudo actualizar el siniestro.");
                    }
                } else {
                    // Crear un nuevo siniestro
                    if (siniestroDao.insertarSiniestro(siniestro)) {
                        System.out.println("Siniestro creado correctamente.");
                    } else {
                        mostrarMensajeDeError("Error al crear", "No se pudo crear el siniestro.");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormSiniestroController.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensajeDeError("Error de base de datos", "Error al intentar guardar el siniestro: " + ex.getMessage());
            }
        } else {
            mostrarMensajeDeError("Error de Validación", "Por favor, corrija los campos marcados en rojo.");
        }
    }

    private void configurarDatosSiniestroDesdeFormulario() {
        siniestro.setNumSiniestro(txtNumSiniestro.getText());
        siniestro.setFechaSiniestro(dpFechaSiniestro.getValue());
        siniestro.setFechaResolucion(dpFechaResolucion.getValue());
        siniestro.setDescripcion(txtDescripcion.getText());
        siniestro.setLugarSiniestro(txtLugarSiniestro.getText());
        siniestro.setTipoSiniestro(txtTipoSiniestro.getText());
        siniestro.setTotalReclamado(txtTotalReclamado.getText());
        siniestro.setEstadoSiniestro(txtEstadoSiniestro.getText());
        siniestro.setIdPoliza(idPoliza);
        // Asegurarse de que el ID de la póliza esté establecido si es necesario
    }

    public void initData(int idPolizaSeleccionada) {
        this.idPoliza = idPolizaSeleccionada;
    }

    public void initData(Siniestro siniestro) {
        if (siniestro == null) {
            this.siniestro = new Siniestro();
            this.isEditMode = false;
        } else {
            this.siniestro = siniestro;
            this.isEditMode = true;
            txtNumSiniestro.setDisable(true);
            cargarDatosSiniestro();
        }
    }

    private void cargarDatosSiniestro() {
        txtNumSiniestro.setText(siniestro.getNumSiniestro());
        dpFechaSiniestro.setValue(siniestro.getFechaSiniestro());
        txtDescripcion.setText(siniestro.getDescripcion());
        txtLugarSiniestro.setText(siniestro.getLugarSiniestro());
        txtTipoSiniestro.setText(siniestro.getTipoSiniestro());
        txtTotalReclamado.setText(siniestro.getTotalReclamado());
        txtEstadoSiniestro.setText(siniestro.getEstadoSiniestro());
        dpFechaResolucion.setValue(siniestro.getFechaResolucion());
    }

    private boolean validarCampos() {
        boolean esValido = true;
        limpiarEstilosDeError();

        // Valida cada campo y añade estilo de error si es necesario
        if (txtNumSiniestro.getText().isEmpty()) {
            txtNumSiniestro.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtDescripcion.getText().isEmpty()) {
            txtDescripcion.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtLugarSiniestro.getText().isEmpty()) {
            txtLugarSiniestro.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtTipoSiniestro.getText().isEmpty()) {
            txtTipoSiniestro.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtTotalReclamado.getText().isEmpty()) {
            txtTotalReclamado.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtEstadoSiniestro.getText().isEmpty()) {
            txtEstadoSiniestro.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (dpFechaSiniestro.getValue() == null) {
            dpFechaSiniestro.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (dpFechaResolucion.getValue() == null) {
            dpFechaResolucion.getStyleClass().add("text-field-error");
            esValido = false;
        }

        return esValido;
    }

    private void limpiarEstilosDeError() {
        txtNumSiniestro.getStyleClass().remove("text-field-error");
        txtDescripcion.getStyleClass().remove("text-field-error");
        txtLugarSiniestro.getStyleClass().remove("text-field-error");
        txtTipoSiniestro.getStyleClass().remove("text-field-error");
        txtTotalReclamado.getStyleClass().remove("text-field-error");
        txtEstadoSiniestro.getStyleClass().remove("text-field-error");
        dpFechaSiniestro.getStyleClass().remove("text-field-error");
        dpFechaResolucion.getStyleClass().remove("text-field-error");
    }
    
     @FXML
    private void handleCancelar(ActionEvent event) {
        // Obtiene la ventana (Stage) actual desde el evento, cerrándola.
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void mostrarMensajeDeError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
