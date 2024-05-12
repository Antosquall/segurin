/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.testjavafx;

import com.mycompany.model.Recibo;
import com.mycompany.persistance.ReciboDAO;
import com.mycompany.util.Util;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FormReciboController implements Initializable {

    /**
     * Controlador FXML para la interfaz de gestión de recibos. Este controlador
     * facilita la entrada y modificación de los datos de recibos relacionados
     * con pólizas de seguro.
     */
    /**
     * Campo de texto para ingresar el número del recibo. Es utilizado como un
     * identificador único dentro de la interfaz de usuario para representar
     * cada recibo.
     */
    @FXML
    private TextField txtNumRecibo;

    /**
     * Campo de texto para ingresar el total a pagar indicado en el recibo. Este
     * campo acepta valores numéricos que representan la cantidad monetaria del
     * recibo.
     */
    @FXML
    private TextField txtTotalPagar;

    /**
     * Campo de texto para ingresar el estado actual del recibo. Los estados
     * comunes pueden incluir términos como "Pagado", "Pendiente" o "Vencido".
     */
    @FXML
    private TextField txtEstadoRecibo;

    /**
     * Campo de texto para ingresar el tipo de pago utilizado para liquidar el
     * recibo. Ejemplos de tipos de pago incluyen "Efectivo", "Tarjeta de
     * Crédito", "Transferencia Bancaria", etc.
     */
    @FXML
    private TextField txtTipoPago;

    /**
     * Selector de fecha para la fecha de emisión del recibo. Permite al usuario
     * seleccionar una fecha, que se utiliza para registrar cuándo fue emitido
     * el recibo.
     */
    @FXML
    private DatePicker dpFechaEmision;

    /**
     * Selector de fecha para la fecha de vencimiento del recibo. Esta fecha
     * indica el último día en que el recibo puede ser pagado sin incurrir en
     * penalizaciones.
     */
    @FXML
    private DatePicker dpFechaVencimiento;

    /**
     * Instancia de la clase Recibo que representa el recibo siendo creado o
     * editado en el formulario. Este objeto sirve como un modelo de datos para
     * recoger la información introducida en la interfaz de usuario.
     */
    private Recibo recibo = new Recibo();

    /**
     * Identificador de la póliza asociada con este recibo. Este identificador
     * vincula el recibo con una póliza específica dentro del sistema.
     */
    private int idPoliza;

    /**
     * Indicador de si el formulario está en modo de edición. Si está en modo de
     * edición, el formulario carga y permite la modificación de un recibo
     * existente. Si no, el formulario se configura para la creación de un nuevo
     * recibo.
     */
    private boolean isEditMode = false;

    /**
     * Inicializa el controlador después de que todos los elementos de la
     * interfaz de usuario han sido cargados. Este método configura
     * inicializaciones específicas necesarias para el correcto funcionamiento
     * del formulario de recibos.
     *
     * @param url El URL que fue usado para cargar el archivo FXML, que puede
     * ser usado para resolver rutas relativas para el objeto raíz, o null si la
     * ubicación no es conocida.
     * @param rb El recurso que fue usado para localizar el objeto raíz, o null
     * si el recurso no fue localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Util.configurarFiltroNumericoDecimal(txtTotalPagar);
    }

    /**
     * Abre y muestra la ventana principal de la interfaz de usuario para el
     * formulario de recibos. Carga la interfaz desde un archivo FXML y
     * configura la ventana donde se mostrará este formulario. Establece
     * restricciones de tamaño para asegurar que la ventana tenga dimensiones
     * adecuadas y consistentes.
     *
     * Este método captura y maneja las excepciones de I/O que pueden ocurrir
     * durante la carga del archivo FXML, asegurando que cualquier error se
     * registre adecuadamente.
     *
     * @throws IOException Si hay un error al cargar el archivo FXML, la
     * excepción se captura y se imprime un mensaje de error en la consola. Esto
     * puede ocurrir si el archivo FXML no se encuentra o hay un problema de
     * lectura.
     */
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

    /**
     * Maneja el evento de clic en el botón guardar recibo. Valida los campos
     * del formulario antes de intentar guardar o actualizar un recibo en la
     * base de datos. Si los campos son válidos y es un modo de edición,
     * actualiza el recibo existente; de lo contrario, crea un nuevo recibo en
     * la base de datos. Muestra mensajes de éxito o error dependiendo del
     * resultado de la operación de guardado.
     *
     * @param event El evento de acción que se activa al hacer clic en el botón
     * guardar.
     * @throws SQLException Si ocurre un error al interactuar con la base de
     * datos, se captura y se maneja mostrando un mensaje de alerta al usuario.
     */
    @FXML
    private void handleGuardarRecibo(ActionEvent event) {
        if (validarCampos()) {
            configurarDatosReciboDesdeFormulario();
            try {
                ReciboDAO reciboDao = new ReciboDAO(ConexionMySQL.conectar());
                if (isEditMode) {
                    // Actualizar el recibo existente
                    if (reciboDao.actualizarRecibo(recibo)) {
                        Util.mostrarMensaje("Operación Exitosa", "Recibo actualizado.");
                        handleCancelar(event);
                    } else {
                        Util.mostrarAlerta("Error al actualizar", "No se pudo actualizar el recibo.");
                    }
                } else {
                    // Crear un nuevo recibo
                    if (reciboDao.insertRecibo(recibo)) {
                        Util.mostrarMensaje("Operación Exitosa", "Recibo creado.");
                        handleCancelar(event);
                    } else {
                        Util.mostrarAlerta("Error al crear", "No se pudo crear el recibo.");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormReciboController.class.getName()).log(Level.SEVERE, null, ex);
                Util.mostrarAlerta("Error de base de datos", "Error al intentar guardar el recibo: " + ex.getMessage());
            }
        } else {
            Util.mostrarAlerta("Error de Validación", "Por favor, corrija los campos marcados en rojo.");
        }
    }

    /**
     * Configura los datos del recibo a partir de los valores ingresados en los
     * campos del formulario. Este método transfiere los datos de la interfaz
     * gráfica al modelo de datos del recibo, preparándolo para una operación de
     * guardado o actualización en la base de datos.
     *
     * Extrae y asigna los valores de los campos del formulario a las
     * propiedades correspondientes del objeto recibo. Asegura que todos los
     * datos necesarios, como número de recibo, fechas de emisión y vencimiento,
     * total a pagar, estado del recibo y la identificación de la póliza
     * asociada, sean correctamente establecidos en el modelo del recibo.
     */
    private void configurarDatosReciboDesdeFormulario() {
        recibo.setNumRecibo(txtNumRecibo.getText());
        recibo.setFechaEmision(dpFechaEmision.getValue());
        recibo.setFechaVencimiento(dpFechaVencimiento.getValue());
        recibo.setTotalPagar(txtTotalPagar.getText());
        recibo.setEstadoRecibo(txtEstadoRecibo.getText());
        recibo.setIdPoliza(idPoliza);
        // Asegúrate de configurar cualquier otro dato necesario para el recibo
    }

    /**
     * Inicializa el controlador con el ID de la póliza seleccionada. Este
     * método es utilizado para configurar el controlador con un ID de póliza
     * específico, permitiendo que se carguen o se preparen datos específicos
     * relacionados con esa póliza.
     *
     * @param idPolizaSeleccionada El ID de la póliza que será usado para cargar
     * o preparar datos adicionales relacionados con la póliza en el contexto de
     * este controlador.
     */
    public void initData(int idPolizaSeleccionada) {
        this.idPoliza = idPolizaSeleccionada;
        // Aquí puedes utilizar el ID del cliente para realizar cualquier acción necesaria
    }

    /**
     * Inicializa el controlador con una instancia de Recibo, estableciendo el
     * modo de operación del formulario dependiendo de si el recibo
     * proporcionado es nulo o no. Si el recibo es nulo, el método configura el
     * controlador para crear un nuevo recibo. Si se proporciona un recibo, el
     * método configura el controlador para editar el recibo existente.
     *
     * En el caso de edición, el número de recibo se deshabilita para prevenir
     * modificaciones, y se cargan los datos del recibo en los campos del
     * formulario.
     *
     * @param recibo La instancia de Recibo a utilizar para configurar el
     * formulario. Si es null, el formulario se configura para la creación de un
     * nuevo recibo. Si no es null, el formulario se prepara para la edición del
     * recibo existente.
     */
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

    /**
     * Maneja el evento de clic en el botón de cancelar. Este método cierra la
     * ventana actual en la que se encuentra el formulario. Se activa cuando el
     * usuario hace clic en el botón de cancelar.
     *
     * @param event El evento de acción generado cuando se hace clic en el botón
     * de cancelar. Contiene información sobre el contexto en el que se generó
     * el evento, permitiendo acceder a la ventana actual y cerrarla.
     */
    @FXML
    private void handleCancelar(ActionEvent event) {
        // Obtiene la ventana (Stage) actual desde el evento, cerrándola.
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Carga los datos del recibo actual en los campos correspondientes del
     * formulario. Este método extrae información del objeto recibo asociado y
     * establece los valores en los campos de la interfaz de usuario. Se utiliza
     * para prellenar el formulario con los datos existentes cuando se está en
     * modo de edición.
     */
    private void cargarDatosRecibo() {
        txtNumRecibo.setText(recibo.getNumRecibo());
        dpFechaEmision.setValue(recibo.getFechaEmision());
        txtTotalPagar.setText(String.valueOf(recibo.getTotalPagar()));
        txtEstadoRecibo.setText(recibo.getEstadoRecibo());
        dpFechaVencimiento.setValue(recibo.getFechaVencimiento());
    }

    /**
     * Valida los campos del formulario para asegurar que todos los campos
     * requeridos están correctamente llenados. Este método verifica si cada
     * campo del formulario tiene un valor válido y no está vacío. Si un campo
     * está vacío o no contiene un valor válido, se agrega un estilo de error a
     * ese campo y se establece la validez del formulario a falso.
     *
     * @return boolean Retorna verdadero si todos los campos contienen valores
     * válidos, de lo contrario retorna falso. Esta valoración es crucial para
     * decidir si el formulario puede ser enviado o si requiere correcciones.
     */
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

    /**
     * Elimina los estilos de error de todos los campos del formulario. Este
     * método se utiliza para limpiar visualmente los estilos que indican un
     * error antes de una nueva validación o antes de rellenar el formulario con
     * nuevos datos para asegurar que no persistan indicaciones de error
     * previas.
     */
    private void limpiarEstilosDeError() {
        txtNumRecibo.getStyleClass().remove("text-field-error");
        txtTotalPagar.getStyleClass().remove("text-field-error");
        txtEstadoRecibo.getStyleClass().remove("text-field-error");
        txtTipoPago.getStyleClass().remove("text-field-error");
        dpFechaEmision.getStyleClass().remove("text-field-error");
        dpFechaVencimiento.getStyleClass().remove("text-field-error");
    }

}
