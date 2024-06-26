package com.mycompany.testjavafx;

import com.mycompany.model.Cliente;
import com.mycompany.model.Poliza;
import com.mycompany.model.Recibo;
import com.mycompany.model.Siniestro;
import com.mycompany.model.Usuario;
import com.mycompany.persistance.ClienteDAO;
import com.mycompany.persistance.PolizaDAO;
import com.mycompany.persistance.ReciboDAO;
import com.mycompany.persistance.SiniestroDAO;
import com.mycompany.util.MenuManager;
import com.mycompany.util.Util;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controlador principal de la interfaz de usuario para la aplicación.
 */
public class PrimaryController implements Initializable {

    /**
     * Tabla que muestra la lista de clientes.
     */
    @FXML
    private TableView<Cliente> clientesTable;

    /**
     * Tabla que muestra la lista de pólizas.
     */
    @FXML
    private TableView<Poliza> polizaTable;

    /**
     * Tabla que muestra la lista de siniestros.
     */
    @FXML
    private TableView<Siniestro> siniestroTable;

    /**
     * Tabla que muestra la lista de recibos.
     */
    @FXML
    private TableView<Recibo> reciboTable;

    /**
     * Columnas de la tabla de clientes.
     */
    @FXML
    private TableColumn<Cliente, String> clmNombre, clmApellido, clmTelefono, clmMail, clmDNI;

    /**
     * Columnas de la tabla de pólizas.
     */
    @FXML
    private TableColumn<Poliza, String> clmNumPoliza, clmVencimiento, clmCobertura;

    /**
     * Columnas de la tabla de siniestros.
     */
    @FXML
    private TableColumn<Siniestro, String> clmSiniestro, clmFechaSiniestro, clmEstadoSiniestro;

    /**
     * Columnas de la tabla de recibos.
     */
    @FXML
    private TableColumn<Recibo, String> clmRecibo, clmFechaEmision, clmFechaVencimiento, clmTotal;

    /**
     * Campo de texto utilizado para filtrar los elementos de las tablas.
     */
    @FXML
    private TextField filterField;

    /**
     * Texto que muestra el rol del usuario actual.
     */
    @FXML
    private Text textRol;

    /**
     * Texto que muestra el nombre de usuario actual.
     */
    @FXML
    private Text textUser;

    /**
     * Lista observable que contiene la lista de clientes.
     */
    private ObservableList<Cliente> listaClientes;

    /**
     * Lista observable que contiene la lista de pólizas asociadas a un cliente.
     */
    private ObservableList<Poliza> polizaClientes;

    /**
     * Lista observable que contiene la lista de recibos asociados a una póliza.
     */
    private ObservableList<Recibo> reciboPoliza;

    /**
     * Lista observable que contiene la lista de siniestros asociados a una
     * póliza.
     */
    private ObservableList<Siniestro> siniestroPoliza;

    /**
     * Conexión a la base de datos.
     */
    private Connection connection;

    /**
     * Menú de la aplicación.
     */
    @FXML
    private MenuBar menuBar;

    /**
     * Objeto que representa al usuario actual.
     */
    private Usuario usuario;

    /**
     * Inicializa el controlador y configura los componentes de la interfaz de
     * usuario.
     *
     * @param location La ubicación utilizada para resolver rutas relativas para
     * el objeto raíz, o null si no se conoce la ubicación.
     * @param resources Los recursos utilizados para localizar el objeto raíz, o
     * null si no se encontraron recursos.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupConnection();
        configurarMenuContextual();
        setupTables();
        configureTextFields();

        setupTableListeners();
    }

    /**
     * Configura las tablas de la interfaz de usuario.
     */
    private void setupTables() {
        listaClientes = FXCollections.observableArrayList();
        polizaClientes = FXCollections.observableArrayList();
        reciboPoliza = FXCollections.observableArrayList();
        siniestroPoliza = FXCollections.observableArrayList();

        setupTableColumns();
        loadInitialData();  // Asegúrate de que los datos se carguen antes de configurar el filtro
        setupFilter();      // Configura el filtro después de que los datos estén listos
    }

    /**
     * Configura las columnas para todas las tablas.
     */
    private void setupTableColumns() {
        setupClientTableColumns();
        setupPolicyTableColumns();
        setupClaimTableColumns();
        setupReceiptTableColumns();
    }

    /**
     * Configura las columnas de la tabla de clientes.
     */
    private void setupClientTableColumns() {
        clmDNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        clmMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
    }

    /**
     * Configura las columnas de la tabla de pólizas.
     */
    private void setupPolicyTableColumns() {
        clmNumPoliza.setCellValueFactory(new PropertyValueFactory<>("NumeroPoliza"));
        clmVencimiento.setCellValueFactory(new PropertyValueFactory<>("FechaVencimiento"));
        clmCobertura.setCellValueFactory(new PropertyValueFactory<>("TipoCobertura"));
    }

    /**
     * Configura las columnas de la tabla de siniestros.
     */
    private void setupClaimTableColumns() {
        clmSiniestro.setCellValueFactory(new PropertyValueFactory<>("numSiniestro"));
        clmFechaSiniestro.setCellValueFactory(new PropertyValueFactory<>("fechaSiniestro"));
        clmEstadoSiniestro.setCellValueFactory(new PropertyValueFactory<>("estadoSiniestro"));
    }

    /**
     * Configura las columnas de la tabla de recibos.
     */
    private void setupReceiptTableColumns() {
        clmRecibo.setCellValueFactory(new PropertyValueFactory<>("numRecibo"));
        clmFechaEmision.setCellValueFactory(new PropertyValueFactory<>("fechaEmision"));
        clmFechaVencimiento.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<>("totalPagar"));
    }

    /**
     * Carga los datos iniciales para todas las tablas.
     */
    private void loadInitialData() {
        loadClientesData();
//        loadPolizasData();
//        loadSiniestrosData();
//        loadRecibosData();
    }

    /**
     * Carga los datos de los clientes desde la base de datos y los muestra en
     * la tabla de clientes. Utiliza ClienteDAO para obtener la lista de todos
     * los clientes desde la base de datos, asigna la lista obtenida a la lista
     * observable listaClientes y establece esta lista como el conjunto de ítems
     * de la tabla de clientes. En caso de error al cargar los datos de los
     * clientes desde la base de datos, registra el error.
     */
    private void loadClientesData() {
        try {
            ClienteDAO clienteDAO = new ClienteDAO(connection);
            List<Cliente> clientes = clienteDAO.getAllClientes();
            listaClientes.setAll(clientes);
            clientesTable.setItems(listaClientes);
        } catch (SQLException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, "Error loading clients data", e);
        }
    }

    /**
     * Carga los datos de las pólizas desde la base de datos y los muestra en la
     * tabla de pólizas. Utiliza PolizaDAO para obtener la lista de todas las
     * pólizas desde la base de datos, limpia la lista observable
     * polizaClientes, agrega todas las pólizas obtenidas a esta lista
     * observable y establece esta lista como el conjunto de ítems de la tabla
     * de pólizas. En caso de error al cargar los datos de las pólizas desde la
     * base de datos, registra el error y muestra una alerta de error.
     */
    private void loadPolizasData() {
        try {
            PolizaDAO polizaDao = new PolizaDAO(connection);
            List<Poliza> polizas = polizaDao.getAllPolizas();  // Asume que tienes un método para obtener todas las pólizas
            polizaClientes.clear();
            polizaClientes.addAll(polizas);
            polizaTable.setItems(polizaClientes);
        } catch (SQLException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, "Error loading policies data", e);
            Util.mostrarAlerta("Error de Carga", "No se pudieron cargar los datos de las pólizas.");
        }
    }

    /**
     * Carga los datos de los siniestros desde la base de datos y los muestra en
     * la tabla de siniestros. Utiliza SiniestroDAO para obtener la lista de
     * todos los siniestros desde la base de datos, limpia la lista observable
     * siniestroPoliza, agrega todos los siniestros obtenidos a esta lista
     * observable y establece esta lista como el conjunto de ítems de la tabla
     * de siniestros. En caso de error al cargar los datos de los siniestros
     * desde la base de datos, registra el error y muestra una alerta de error.
     */
    private void loadSiniestrosData() {
        try {
            SiniestroDAO siniestroDao = new SiniestroDAO(connection);
            List<Siniestro> siniestros = siniestroDao.getAllSiniestros();  // Asume que tienes un método para obtener todos los siniestros
            siniestroPoliza.clear();
            siniestroPoliza.addAll(siniestros);
            siniestroTable.setItems(siniestroPoliza);
        } catch (SQLException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, "Error loading claims data", e);
            Util.mostrarAlerta("Error de Carga", "No se pudieron cargar los datos de los siniestros.");
        }
    }

    /**
     * Carga los datos de los recibos desde la base de datos y los muestra en la
     * tabla de recibos. Utiliza ReciboDAO para obtener la lista de todos los
     * recibos desde la base de datos, limpia la lista observable reciboPoliza,
     * agrega todos los recibos obtenidos a esta lista observable y establece
     * esta lista como el conjunto de ítems de la tabla de recibos. En caso de
     * error al cargar los datos de los recibos desde la base de datos, registra
     * el error y muestra una alerta de error.
     */
    private void loadRecibosData() {
        try {
            ReciboDAO reciboDao = new ReciboDAO(connection);
            List<Recibo> recibos = reciboDao.getAllRecibos();  // Asume que tienes un método para obtener todos los recibos
            reciboPoliza.clear();
            reciboPoliza.addAll(recibos);
            reciboTable.setItems(reciboPoliza);
        } catch (SQLException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, "Error loading receipts data", e);
            Util.mostrarAlerta("Error de Carga", "No se pudieron cargar los datos de los recibos.");
        }
    }

    /**
     * Configura los oyentes de eventos para las tablas.
     */
    private void setupTableListeners() {
        clientesTable.setOnMouseClicked(this::handleClienteTableClick);
        polizaTable.setOnMouseClicked(this::handlePolizaTableClick);
    }

    /**
     * Maneja los eventos de clic en la tabla de clientes.
     *
     * @param event El evento de clic del mouse.
     */
    private void handleClienteTableClick(MouseEvent event) {
        if (event.getClickCount() == 1 && !clientesTable.getSelectionModel().isEmpty()) {
            Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
            //System.out.println("Cliente seleccionado: " + clienteSeleccionado.getIdCliente());
            clearAndLoadPolizas(clienteSeleccionado.getIdCliente());
        }
    }

    /**
     * Maneja los eventos de clic en la tabla de pólizas.
     *
     * @param event El evento de clic del mouse.
     */
    private void handlePolizaTableClick(MouseEvent event) {
        if (event.getClickCount() == 1 && !polizaTable.getSelectionModel().isEmpty()) {
            Poliza polizaSeleccionada = polizaTable.getSelectionModel().getSelectedItem();
            //System.out.println("Poliza Seleccionada: " + polizaSeleccionada.getIdPoliza());
            clearAndLoadSiniestrosRecibos(polizaSeleccionada.getIdPoliza());
        }
    }

    /**
     * Limpia y carga nuevas pólizas para un cliente específico.
     *
     * @param clientId El ID del cliente para el cual cargar pólizas.
     */
    private void clearAndLoadPolizas(int clientId) {
        polizaClientes.clear();
        try {
            PolizaDAO polizaDao = new PolizaDAO(connection);
            List<Poliza> polizas = polizaDao.getPolizasByIdCliente(clientId);
            polizaClientes.setAll(polizas);
            polizaTable.setItems(polizaClientes);
            siniestroPoliza.clear();
            reciboPoliza.clear();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Limpia y carga nuevos siniestros y recibos para una póliza específica.
     *
     * @param polizaId El ID de la póliza para la cual cargar siniestros y
     * recibos.
     */
    private void clearAndLoadSiniestrosRecibos(int polizaId) {
        siniestroPoliza.clear();
        reciboPoliza.clear();
        try {
            SiniestroDAO siniestroDAO = new SiniestroDAO(connection);
            List<Siniestro> siniestros = siniestroDAO.getSiniestrosByIdPoliza(polizaId);
            siniestroPoliza.setAll(siniestros);
            siniestroTable.setItems(siniestroPoliza);

            ReciboDAO reciboDAO = new ReciboDAO(connection);
            List<Recibo> recibos = reciboDAO.getRecibosByIdPoliza(polizaId);
            reciboPoliza.setAll(recibos);
            reciboTable.setItems(reciboPoliza);
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Configura el filtro para la tabla de clientes.
     */
    private void setupFilter() {
        FilteredList<Cliente> filteredData = new FilteredList<>(listaClientes, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Muestra todos los datos si el filtro está vacío
                }
                String lowerCaseFilter = newValue.toLowerCase();

                // Comprueba si los campos DNI, nombre o apellido coinciden con el filtro
                if (cliente.getDNI().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cliente.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cliente.getApellido().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // No coincide
            });
        });

        SortedList<Cliente> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clientesTable.comparatorProperty());
        clientesTable.setItems(sortedData);
    }

    /**
     * Configura la conexión con la base de datos.
     */
    private void setupConnection() {
        connection = ConexionMySQL.conectar();
    }

    /**
     * Configura los campos de texto relacionados con la información del
     * usuario.
     */
    private void configureTextFields() {
        textRol.setText("administrador");
        textUser.setText("Antonio");
        //System.out.println(loginUsuario.getNombreUsuario());
        //textRol.setText(loginUsuario.getRol());
        //textUser.setText(loginUsuario.getNombreUsuario());
        //System.out.println("Usuario logueado: " + this.nombreUsuario);
    }

    /**
     * Configura el menú de la aplicación.
     */
    private void setupMenu() {
        MenuManager menuManager = new MenuManager();
        menuManager.menuComun(menuBar);
        // Aquí se inicializan los menús pasando el rol del usuario.
        //MenuManager menuManager = new MenuManager();
        menuManager.construirMenus(menuBar, usuario.getRol());
        //menuManager.construirMenus(menuBar, loginUsuario.getRol());
        //menuManager.construirMenus(menuBar);

    }

    /**
     * Configura los permisos de vision de los menus.
     */
    private void configMenu(Usuario usuario) {
        System.out.println(usuario.getNombreUsuario());
        if (usuario.getRol() != "administrador") {

        }
//        MenuManager menuManager = new MenuManager();
//        menuManager.menuComun(menuBar);
//        //menuManager.construirMenus(menuBar, loginUsuario.getRol());
//        menuManager.construirMenus(menuBar);
    }

    /**
     * Inicializa los datos del usuario cuando el controlador se carga.
     *
     * @param usuario El nombre del usuario para ser utilizado en la aplicación.
     */
    public void initData(Usuario usuario) {
        this.usuario = usuario;

        // Aquí puedes inicializar o recargar datos que dependen del usuario
        setupMenu();
        updateUI();
        //configMenu(usuario);
    }

    /**
     * Actualiza la interfaz de usuario basada en la información del usuario
     * actual. Establece el nombre de usuario del usuario actual en el campo de
     * texto textUser y el rol del usuario actual en el campo de texto textRol.
     */
    private void updateUI() {
        // Actualiza la interfaz de usuario basada en la información del usuario
        textUser.setText(usuario.getNombreUsuario());
        textRol.setText(usuario.getRol());

    }

    /**
     * Recarga todos los datos de las tablas de clientes, pólizas, siniestros y
     * recibos desde la base de datos. Llama a los métodos loadClientesData(),
     * loadPolizasData(), loadSiniestrosData() y loadRecibosData() para cargar
     * los datos actualizados en las tablas correspondientes. Además, limpia las
     * listas observables polizaClientes, reciboPoliza y siniestroPoliza para
     * evitar duplicados de datos al recargar.
     */
    private void reloadAllData() {
        loadClientesData();
        loadPolizasData();
        loadSiniestrosData();
        loadRecibosData();
        polizaClientes.clear();
        reciboPoliza.clear();
        siniestroPoliza.clear();

    }

    /**
     * Muestra la ventana principal de la aplicación.
     */
    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setMinWidth(1280);
            stage.setMinHeight(920);
            stage.setMaxWidth(1920);
            stage.setMaxHeight(1080); // 2K resolution
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Maneja el evento de clic en el botón para crear un nuevo cliente.
     *
     * @param event El evento de acción que se activa al hacer clic.
     */
    @FXML
    private void handleNuevoCliente(ActionEvent event) {
        FormClienteController formularioCliente = new FormClienteController();
        formularioCliente.mostrarVentanaPrincipal();
        reloadAllData();
        setupFilter();

    }

    /**
     * Maneja el evento de selección de cliente, mostrando información detallada
     * del cliente seleccionado si es necesario.
     *
     * @param event El evento de acción que se activa al seleccionar un cliente.
     */
    @FXML
    private void handleSeleccionaCliente(ActionEvent event) {
        if (!clientesTable.getSelectionModel().isEmpty()) {
            Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
            //System.out.println("Cliente seleccionado: " + clienteSeleccionado.getDNI());
        }
    }

    /**
     * Maneja el evento de clic en el botón para crear una nueva póliza.
     *
     * @param event El evento de acción que se activa al hacer clic.
     */
    @FXML
    private void handleNuevaPoliza(ActionEvent event) {
        Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/testjavafx/formPoliza.fxml"));
                Parent root = loader.load();
                FormPolizaController formPolizaController = loader.getController();
                formPolizaController.initData(clienteSeleccionado.getIdCliente());
                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setTitle("Crear nueva póliza");
                newStage.showAndWait();
                reloadAllData();

            } catch (IOException e) {
                System.err.println("Error al cargar la ventana de creación de póliza: " + e.getMessage());
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Cliente no seleccionado.");
                alert.setContentText("Por favor, seleccione un cliente para crear una póliza.");
                alert.showAndWait();
            }
        } else {
            Util.mostrarAlerta("Faltan datos", "Por favor, seleccione un cliente.");
        }

    }

    /**
     * Maneja el evento de clic en el botón para crear un nuevo siniestro.
     *
     * @param event El evento de acción que se activa al hacer clic.
     */
    @FXML
    private void handleNuevoSiniestro(ActionEvent event) {
        Poliza polizaSeleccionada = polizaTable.getSelectionModel().getSelectedItem();
        if (polizaSeleccionada != null) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("formSiniestro.fxml"));
                Parent root = loader.load();
                FormSiniestroController formSiniestroController = loader.getController();
                formSiniestroController.initData(polizaSeleccionada.getIdPoliza());
                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setTitle("Crear nuevo siniestro");
                newStage.showAndWait();
                reloadAllData();
            } catch (IOException e) {
                System.err.println("Error al cargar la ventana de creación de póliza: " + e.getMessage());
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Póliza no seleccionado.");
                alert.setContentText("Por favor, seleccione una póliza para grabar un siniestro.");
                alert.showAndWait();
            }
        } else {
            Util.mostrarAlerta("Faltan datos", "Por favor, seleccione una póliza.");
        }
    }

    /**
     * Maneja el evento de clic en el botón para crear un nuevo recibo.
     *
     * @param event El evento de acción que se activa al hacer clic.
     */
    @FXML
    private void handleNuevoRecibo(ActionEvent event) {
        Poliza polizaSeleccionada = polizaTable.getSelectionModel().getSelectedItem();
        if (polizaSeleccionada != null) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("formRecibo.fxml"));
                Parent root = loader.load();
                FormReciboController formReciboController = loader.getController();
                formReciboController.initData(polizaSeleccionada.getIdPoliza());
                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setTitle("Crear nuevo recibo");
                newStage.showAndWait();
                reloadAllData();
            } catch (IOException e) {
                System.err.println("Error al cargar la ventana de creación de recibo: " + e.getMessage());
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Póliza no seleccionado.");
                alert.setContentText("Por favor, seleccione una póliza para grabar un recibo.");
                alert.showAndWait();
            }
        } else {
            Util.mostrarAlerta("Faltan datos", "Por favor, seleccione una póliza.");
        }
    }

    /**
     * Configura el menú contextual para todas las tablas.
     */
    private void configurarMenuContextual() {
        // Configura el menú contextual para clientesTable
        ContextMenu menuClientes = new ContextMenu();
        MenuItem verCliente = new MenuItem("Ver Cliente");
        MenuItem editarCliente = new MenuItem("Editar Cliente");
        MenuItem eliminarCliente = new MenuItem("Eliminar Cliente");
        menuClientes.getItems().addAll(verCliente, editarCliente, eliminarCliente);
        clientesTable.setContextMenu(menuClientes);
        editarCliente.setOnAction(this::editarItemSeleccionado);
        eliminarCliente.setOnAction(this::eliminarItemSeleccionado);
        verCliente.setOnAction(this::verClienteSeleccionado);

        // Configura el menú contextual para polizaTable
        ContextMenu menuPolizas = new ContextMenu();
        MenuItem editarPoliza = new MenuItem("Editar Póliza");
        MenuItem eliminarPoliza = new MenuItem("Eliminar Póliza");
        menuPolizas.getItems().addAll(editarPoliza, eliminarPoliza);
        polizaTable.setContextMenu(menuPolizas);
        editarPoliza.setOnAction(this::editarItemSeleccionado);
        eliminarPoliza.setOnAction(this::eliminarItemSeleccionado);

        // Configura el menú contextual para siniestroTable
        ContextMenu menuSiniestros = new ContextMenu();
        MenuItem editarSiniestro = new MenuItem("Editar Siniestro");
        MenuItem eliminarSiniestro = new MenuItem("Eliminar Siniestro");
        menuSiniestros.getItems().addAll(editarSiniestro, eliminarSiniestro);
        siniestroTable.setContextMenu(menuSiniestros);
        editarSiniestro.setOnAction(this::editarItemSeleccionado);
        eliminarSiniestro.setOnAction(this::eliminarItemSeleccionado);

        // Configura el menú contextual para reciboTable
        ContextMenu menuRecibos = new ContextMenu();
        MenuItem editarRecibo = new MenuItem("Editar Recibo");
        MenuItem eliminarRecibo = new MenuItem("Eliminar Recibo");
        menuRecibos.getItems().addAll(editarRecibo, eliminarRecibo);
        reciboTable.setContextMenu(menuRecibos);
        editarRecibo.setOnAction(this::editarItemSeleccionado);
        eliminarRecibo.setOnAction(this::eliminarItemSeleccionado);

    }

    /**
     * Maneja el evento de edición de un elemento seleccionado.
     */
    private void editarItemSeleccionado(ActionEvent event) {
        TableView<?> tablaActiva = obtenerTablaActiva();
        if (tablaActiva != null) {
            Object item = tablaActiva.getSelectionModel().getSelectedItem();
            if (item != null) {
                try {
                    if (item instanceof Cliente) {
                        Cliente cliente = (Cliente) item;
                        abrirEditorCliente(cliente);
                        loadClientesData();
                    } else if (item instanceof Poliza) {
                        Poliza poliza = (Poliza) item;
                        abrirEditorPoliza(poliza);
                        loadClientesData();
                    } else if (item instanceof Siniestro) {
                        Siniestro siniestro = (Siniestro) item;
                        abrirEditorSiniestro(siniestro);
                        loadClientesData();
                    } else if (item instanceof Recibo) {
                        Recibo recibo = (Recibo) item;
                        abrirEditorRecibo(recibo);
                        loadClientesData();
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    Util.mostrarAlerta("Error al cargar la ventana de edición", e.getMessage());
                }
            } else {
                Util.mostrarAlerta("Selección inválida", "Seleccione un elemento para editar.");
            }
            reloadAllData();
        } else {
            Util.mostrarAlerta("Error", "No se pudo determinar la tabla activa.");
        }
    }

    /**
     * Abre la ventana de edición para un cliente específico.
     *
     * @param cliente El cliente a editar.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    private void abrirEditorCliente(Cliente cliente) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formEditCliente.fxml"));
            Parent root = fxmlLoader.load();

            // Obtener el controlador y pasar el ID del cliente
            FormEditClienteController controller = fxmlLoader.getController();
            controller.initData(cliente.getIdCliente());  // Asegúrate de que esto se llama después de cargar

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Editar Cliente");
            stage.showAndWait();
            reloadAllData();
        } catch (IOException e) {
            System.err.println("Error al cargar la ventana de edición de cliente: " + e.getMessage());
        }
    }

    /**
     * Abre la ventana de edición para una póliza específica.
     *
     * @param poliza La póliza a editar.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    private void abrirEditorPoliza(Poliza poliza) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/testjavafx/formPoliza.fxml"));
        Parent root = loader.load();
        FormPolizaController controller = loader.getController();
        controller.initData(poliza);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Editar Póliza");
        stage.setScene(scene);
        stage.showAndWait();
        reloadAllData();
    }

    /**
     * Abre la ventana de edición para un siniestro específico.
     *
     * @param siniestro El siniestro a editar.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    private void abrirEditorSiniestro(Siniestro siniestro) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/testjavafx/formSiniestro.fxml"));
        Parent root = loader.load();
        FormSiniestroController controller = loader.getController();
        controller.initData(siniestro);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Editar Siniestro");
        stage.setScene(scene);
        stage.showAndWait();
        reloadAllData();
    }

    /**
     * Abre la ventana de edición para un recibo específico.
     *
     * @param recibo El recibo a editar.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    private void abrirEditorRecibo(Recibo recibo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/testjavafx/formRecibo.fxml"));
        Parent root = loader.load();
        FormReciboController controller = loader.getController();
        controller.initData(recibo);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Editar Recibo");
        stage.setScene(scene);
        stage.showAndWait();
        reloadAllData();
    }

    /**
     * Maneja la eliminación de un elemento seleccionado.
     */
    private void eliminarItemSeleccionado(ActionEvent event) {
        TableView<?> tablaActiva = obtenerTablaActiva();
        if (tablaActiva != null) {
            Object item = tablaActiva.getSelectionModel().getSelectedItem();
            if (item != null) {
                try {
                    if (item instanceof Cliente) {
                        ClienteDAO clientedao = new ClienteDAO(connection);
                        boolean eliminado = clientedao.deleteCLiente(((Cliente) item).getIdCliente());
                        if (eliminado) {
                            //loadClientesData();
                            reloadAllData();
                            //System.out.println("Cliente eliminado: " + ((Cliente) item).getNombre());
                        } else {
                            Util.mostrarAlerta("Error al eliminar", "No se pudo eliminar el cliente seleccionado.");
                        }
                    } else if (item instanceof Poliza) {
                        PolizaDAO polizaDao = new PolizaDAO(connection);
                        boolean eliminado = polizaDao.deletePoliza(((Poliza) item).getIdPoliza());
                        if (eliminado) {
                            //loadClientesData();
                            reloadAllData();
                            //System.out.println("Póliza eliminada: " + ((Poliza) item).getNumeroPoliza());
                        } else {
                            Util.mostrarAlerta("Error al eliminar", "No se pudo eliminar la póliza seleccionada.");
                        }
                    } else if (item instanceof Siniestro) {
                        SiniestroDAO siniestroDao = new SiniestroDAO(connection);
                        boolean eliminado = siniestroDao.deleteSiniestro(((Siniestro) item).getIdSiniestro());
                        if (eliminado) {
                            //loadClientesData();
                            reloadAllData();
                            //System.out.println("Siniestro eliminado: " + ((Siniestro) item).getNumSiniestro());
                        } else {
                            Util.mostrarAlerta("Error al eliminar", "No se pudo eliminar el siniestro seleccionado.");
                        }
                    } else if (item instanceof Recibo) {
                        ReciboDAO reciboDao = new ReciboDAO(connection);
                        boolean eliminado = reciboDao.deleteRecibo(((Recibo) item).getIdRecibo());
                        if (eliminado) {
                            //loadClientesData();
                            reloadAllData();
                            //System.out.println("Recibo eliminado: " + ((Recibo) item).getNumRecibo());
                        } else {
                            Util.mostrarAlerta("Error al eliminar", "No se pudo eliminar el recibo seleccionado.");
                        }
                    }
                } catch (Exception e) {
                    Util.mostrarAlerta("Error de eliminación", "Error al eliminar el elemento: " + e.getMessage());
                }
            } else {
                Util.mostrarAlerta("Selección inválida", "No hay elemento seleccionado para eliminar.");
            }
        } else {
            Util.mostrarAlerta("Error", "No se pudo determinar la tabla activa.");
        }
    }

    /**
     * Maneja la eliminación de un elemento seleccionado.
     */
    private void verClienteSeleccionado(ActionEvent event) {
        TableView<?> tablaActiva = obtenerTablaActiva();
        if (tablaActiva != null) {
            Object item = tablaActiva.getSelectionModel().getSelectedItem();
            if (item != null) {
                Util.generateReport(((Cliente) item).getIdCliente());
            } else {
                Util.mostrarAlerta("Selección inválida", "No hay elemento seleccionado para eliminar.");
            }
        } else {
            Util.mostrarAlerta("Error", "No se pudo determinar la tabla activa.");
        }
    }

    /**
     * Obtiene la tabla activa en la interfaz de usuario.
     *
     * @return La tabla activa o null si ninguna tabla está enfocada.
     */
    private TableView<?> obtenerTablaActiva() {
        if (clientesTable.isFocused()) {
            return clientesTable;
        } else if (polizaTable.isFocused()) {
            return polizaTable;
        } else if (siniestroTable.isFocused()) {
            return siniestroTable;
        } else if (reciboTable.isFocused()) {
            return reciboTable;
        }
        return null;
    }

}
