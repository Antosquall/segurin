/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.testjavafx;

import com.mycompany.model.Recibo;
import com.mycompany.persistance.ReciboDAO;
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

/**
 * FXML Controller class
 *
 * @author User
 */
public class FormReciboController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtNumRecibo;
    @FXML
    private TextField txtTotalPagar;
    @FXML
    private TextField txtEstadoRecibo;
    @FXML
    private TextField txtTipoPago;
    @FXML
    private DatePicker dpFechaEmision;
    @FXML
    private DatePicker dpFechaVencimiento;

    private Recibo recibo = new Recibo();
    private int idPoliza;

    private boolean isEditMode = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formRecibo.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            //stage.initStyle(StageStyle.UNDECORATED); // Oculta la barra de título
            // Establecer tamaño mínimo y máximo
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.setMaxWidth(900);
            stage.setMaxHeight(600); // 2K resolution
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void handleGuardarRecibo(ActionEvent event) {
        if (validarCampos()) {
            configurarDatosReciboDesdeFormulario();
            try {
                ReciboDAO reciboDao = new ReciboDAO(ConexionMySQL.conectar());
                if (isEditMode) {
                    // Actualizar el recibo existente
                    if (reciboDao.actualizarRecibo(recibo)) {
                        mostrarMensaje("Operación Exitosa", "Recibo actualizado.");
                    } else {
                        mostrarMensajeDeError("Error al actualizar", "No se pudo actualizar el recibo.");
                    }
                } else {
                    // Crear un nuevo recibo
                    if (reciboDao.insertRecibo(recibo)) {
                        mostrarMensaje("Operación Exitosa", "Recibo creado.");
                    } else {
                        mostrarMensajeDeError("Error al crear", "No se pudo crear el recibo.");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormReciboController.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensajeDeError("Error de base de datos", "Error al intentar guardar el recibo: " + ex.getMessage());
            }
        } else {
            mostrarMensajeDeError("Error de Validación", "Por favor, corrija los campos marcados en rojo.");
        }
    }

    private void configurarDatosReciboDesdeFormulario() {
        recibo.setNumRecibo(txtNumRecibo.getText());
        recibo.setFechaEmision(dpFechaEmision.getValue());
        recibo.setFechaVencimiento(dpFechaVencimiento.getValue());
        recibo.setTotalPagar(txtTotalPagar.getText());
        recibo.setEstadoRecibo(txtEstadoRecibo.getText());
        recibo.setIdPoliza(idPoliza);
        // Asegúrate de configurar cualquier otro dato necesario para el recibo
    }

    public void initData(int idPolizaSeleccionada) {
        this.idPoliza = idPolizaSeleccionada;
        // Aquí puedes utilizar el ID del cliente para realizar cualquier acción necesaria
    }

    public void initData(Recibo recibo) {
        if (recibo == null) {
            this.recibo = new Recibo();
            this.isEditMode = false;
        } else {
            this.recibo = recibo;
            this.isEditMode = true;
            txtNumRecibo.setDisable(true);
            cargarDatosRecibo();
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        // Obtiene la ventana (Stage) actual desde el evento, cerrándola.
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void cargarDatosRecibo() {
        txtNumRecibo.setText(recibo.getNumRecibo());
        dpFechaEmision.setValue(recibo.getFechaEmision());
        txtTotalPagar.setText(String.valueOf(recibo.getTotalPagar()));
        txtEstadoRecibo.setText(recibo.getEstadoRecibo());
        dpFechaVencimiento.setValue(recibo.getFechaVencimiento());
    }

    private boolean validarCampos() {
        boolean esValido = true;
        limpiarEstilosDeError(); // Método para limpiar estilos de error antes de validar

        if (txtNumRecibo.getText().isEmpty()) {
            txtNumRecibo.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtTotalPagar.getText().isEmpty()) {
            txtTotalPagar.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtEstadoRecibo.getText().isEmpty()) {
            txtEstadoRecibo.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (txtTipoPago.getText().isEmpty()) {
            txtTipoPago.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (dpFechaEmision.getValue() == null) {
            dpFechaEmision.getStyleClass().add("text-field-error");
            esValido = false;
        }
        if (dpFechaVencimiento.getValue() == null) {
            dpFechaVencimiento.getStyleClass().add("text-field-error");
            esValido = false;
        }
        return esValido;
    }

    private void limpiarEstilosDeError() {
        txtNumRecibo.getStyleClass().remove("text-field-error");
        txtTotalPagar.getStyleClass().remove("text-field-error");
        txtEstadoRecibo.getStyleClass().remove("text-field-error");
        txtTipoPago.getStyleClass().remove("text-field-error");
        dpFechaEmision.getStyleClass().remove("text-field-error");
        dpFechaVencimiento.getStyleClass().remove("text-field-error");
    }

    /**
     * Muestra un mensaje de error en una ventana de alerta.
     *
     * @param titulo El título de la ventana de alerta.
     * @param mensaje El mensaje específico que se mostrará en la alerta.
     */
    private void mostrarMensajeDeError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Crea una nueva alerta de tipo ERROR
        alert.setTitle(titulo);                   // Establece el título de la alerta
        alert.setHeaderText(null);                // No se usa texto de encabezado
        alert.setContentText(mensaje);            // Establece el contenido del mensaje de la alerta
        alert.showAndWait();                      // Muestra la alerta y espera a que el usuario la cierre
    }

    /**
     * Muestra un mensaje genérico en un cuadro de diálogo.
     *
     * @param titulo El título del cuadro de diálogo.
     * @param mensaje El mensaje a mostrar.
     */
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
