package com.mycompany.testjavafx;

import com.mycompany.model.Siniestro;
import com.mycompany.persistance.SiniestroDAO;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormSiniestroController implements Initializable {

    /**
     * Controlador FXML para la interfaz de gestión de siniestros. Este
     * controlador facilita la entrada y modificación de los datos de siniestros
     * relacionados con pólizas de seguro.
     */
    /**
     * Campo de texto para ingresar el número identificatorio del siniestro.
     */
    @FXML
    private TextField txtNumSiniestro;

    /**
     * Campo de texto para ingresar la descripción detallada del siniestro.
     */
    @FXML
    private TextField txtDescripcion;

    /**
     * Campo de texto para ingresar el lugar donde ocurrió el siniestro.
     */
    @FXML
    private TextField txtLugarSiniestro;

    /**
     * Campo de texto para especificar el tipo de siniestro, por ejemplo,
     * "Robo", "Accidente vehicular", etc.
     */
    @FXML
    private TextField txtTipoSiniestro;

    /**
     * Campo de texto para ingresar el total monetario reclamado como resultado
     * del siniestro.
     */
    @FXML
    private TextField txtTotalReclamado;

    /**
     * Campo de texto para especificar el estado actual del siniestro, como "En
     * proceso", "Resuelto", etc.
     */
    @FXML
    private TextField txtEstadoSiniestro;

    /**
     * Selector de fecha para la fecha en que ocurrió el siniestro.
     */
    @FXML
    private DatePicker dpFechaSiniestro;

    /**
     * Selector de fecha para la fecha en que se resolvió o se espera resolver
     * el siniestro.
     */
    @FXML
    private DatePicker dpFechaResolucion;

    /**
     * Instancia de la clase Siniestro que representa al siniestro siendo creado
     * o editado en el formulario.
     */
    private Siniestro siniestro = new Siniestro();

    /**
     * Identificador de la póliza asociada con este siniestro. Este
     * identificador vincula el siniestro con una póliza específica dentro del
     * sistema.
     */
    private int idPoliza;

    /**
     * Indicador de si el formulario está en modo de edición. Si está en modo de
     * edición, el formulario carga y permite la modificación de un siniestro
     * existente. Si no, el formulario se configura para la creación de un nuevo
     * siniestro.
     */
    private boolean isEditMode = false;

    /**
     * Inicializa el controlador después de que todos los elementos de la
     * interfaz de usuario han sido cargados. Este método se llama
     * automáticamente después de que los elementos FXML han sido completamente
     * cargados e instanciados. Puede ser utilizado para realizar
     * configuraciones adicionales o cargar datos dinámicos necesarios para la
     * interfaz.
     *
     * @param url El URL que fue utilizado para cargar el archivo FXML, que
     * puede ser usado para resolver rutas relativas para el objeto raíz, o null
     * si la ubicación no es conocida.
     * @param rb El recurso que fue utilizado para localizar el objeto raíz, o
     * null si el recurso no fue encontrado.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí podrías cargar datos adicionales si es necesario
    }

    /**
     * Abre y muestra la ventana principal para el formulario de siniestros.
     * Carga la interfaz de usuario desde un archivo FXML específico y configura
     * una nueva ventana para mostrarla. Establece dimensiones fijas para la
     * ventana para asegurar una presentación consistente independientemente del
     * contenido.
     *
     * @throws IOException Si hay un problema al cargar el archivo FXML, como un
     * archivo no encontrado o un error de lectura, se captura la excepción y se
     * imprime un mensaje de error en la consola.
     */
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

    /**
     * Maneja el evento de clic en el botón de creación de siniestro. Valida los
     * campos del formulario antes de intentar guardar o actualizar un siniestro
     * en la base de datos. Si los campos son válidos y el formulario está en
     * modo de edición, actualiza el siniestro existente; de lo contrario, crea
     * un nuevo siniestro en la base de datos. Muestra mensajes de éxito o error
     * dependiendo del resultado de la operación de guardado.
     *
     * @param event El evento de acción que se activa al hacer clic en el botón
     * de crear o actualizar siniestro.
     * @throws SQLException Si ocurre un error al interactuar con la base de
     * datos, se captura y se maneja mostrando un mensaje de alerta al usuario.
     */
    @FXML
    private void handleCrearSiniestro(ActionEvent event) {
        if (validarCampos()) {
            configurarDatosSiniestroDesdeFormulario();
            try {
                SiniestroDAO siniestroDao = new SiniestroDAO(ConexionMySQL.conectar());
                if (isEditMode) {
                    // Actualizar el siniestro existente
                    if (siniestroDao.actualizarSiniestro(siniestro)) {
                        Util.mostrarMensaje("OK", "Siniestro actualizado correctamente.");
                        handleCancelar(event);
                    } else {
                        Util.mostrarAlerta("Error al actualizar", "No se pudo actualizar el siniestro.");
                    }
                } else {
                    // Crear un nuevo siniestro
                    if (siniestroDao.insertarSiniestro(siniestro)) {
                        Util.mostrarMensaje("OK", "Siniestro creado correctamente.");
                        handleCancelar(event);
                    } else {
                        Util.mostrarAlerta("Error al crear", "No se pudo crear el siniestro.");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormSiniestroController.class.getName()).log(Level.SEVERE, null, ex);
                Util.mostrarAlerta("Error de base de datos", "Error al intentar guardar el siniestro: " + ex.getMessage());
            }
        } else {
            Util.mostrarAlerta("Error de Validación", "Por favor, corrija los campos marcados en rojo.");
        }
    }

    /**
     * Extrae y asigna los valores de los campos del formulario a las
     * propiedades del objeto siniestro. Este método se encarga de configurar el
     * objeto siniestro con toda la información necesaria para su guardado o
     * actualización en la base de datos. Recopila los datos ingresados en los
     * campos del formulario y los asigna a las propiedades correspondientes del
     * objeto siniestro, preparándolo para una operación de persistencia.
     */
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

    /**
     * Inicializa el controlador con un ID de póliza específico. Este método es
     * utilizado para configurar el controlador con un ID de póliza
     * proporcionado, lo cual es crucial para asociar cualquier nuevo siniestro
     * creado o siniestro existente editado con la póliza correcta.
     *
     * @param idPolizaSeleccionada El ID de la póliza que se utilizará para
     * operaciones adicionales como la creación o actualización de siniestros
     * relacionados con esta póliza.
     */
    public void initData(int idPolizaSeleccionada) {
        this.idPoliza = idPolizaSeleccionada;
    }

    /**
     * Inicializa los datos del objeto Siniestro.
     *
     * Si el parámetro siniestro es nulo, se crea un nuevo objeto Siniestro y se
     * establece el modo de edición como falso. Si el parámetro siniestro no es
     * nulo, se asigna el objeto siniestro pasado, se establece el modo de
     * edición como verdadero, se deshabilita el componente txtNumSiniestro y se
     * cargan los datos del siniestro.
     *
     * @param siniestro El objeto Siniestro que se utilizará para inicializar
     * los datos. Puede ser nulo.
     */
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

    /**
     * Carga los datos del objeto Siniestro en los componentes de la interfaz de
     * usuario. Actualiza los campos de texto y selección con los valores
     * correspondientes del objeto Siniestro.
     */
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

    /**
     * Valida los campos de entrada en la interfaz de usuario. Comprueba si los
     * campos obligatorios están vacíos y agrega un estilo de error a los campos
     * vacíos.
     *
     * @return true si todos los campos obligatorios contienen datos válidos,
     * false de lo contrario.
     */
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

    /**
     * Limpia los estilos de error de los componentes de la interfaz de usuario.
     * Elimina la clase de estilo "text-field-error" de los componentes de texto
     * y selección.
     */
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

    /**
     * Maneja el evento de cancelación. Obtiene la ventana actual (Stage) a
     * partir del evento y la cierra.
     *
     * @param event El evento de acción que desencadenó este método.
     */
    @FXML
    private void handleCancelar(ActionEvent event) {
        // Obtiene la ventana (Stage) actual desde el evento, cerrándola.
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
