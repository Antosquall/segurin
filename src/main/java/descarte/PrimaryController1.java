package descarte;

import com.mycompany.model.Cliente;
import com.mycompany.persistance.ClienteDAO;
import com.mycompany.testjavafx.ConexionMySQL;
import com.mycompany.testjavafx.FormClienteController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrimaryController1 implements Initializable {

    //Componentes graficos
    @FXML
    private Label timeField;
    @FXML
    private ScrollPane usuariosScrollPane;
    @FXML
    private VBox usuariosVBox;
    @FXML
    private TableView<Cliente> clientesTable;

    //Componentes Columnas
    @FXML
    private TableColumn<Cliente, String> clmNombre;
    @FXML
    private TableColumn<Cliente, String> clmApellido;
    @FXML
    private TableColumn<Cliente, String> clmTelefono;
    @FXML
    private TableColumn<Cliente, String> clmMail;
    @FXML
    private TableColumn<Cliente, String> clmDNI;

    //Colecciones
    private ObservableList<Cliente> listaClientes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Iniciarlizar lista
        listaClientes = FXCollections.observableArrayList();

        try {

            //Bloqueamos la tabla.
            //clientesTable.set
            var connection = ConexionMySQL.conectar();

            //Sustituir por lamada a ClienteDAO, que nos devuelve una list de objetos.
            //Llenar lista
            //Cliente.llenarInformacionCliente(connection, listaClientes);
            //Intneto con CLienteDAO
            ClienteDAO clienteDAO = new ClienteDAO(connection);
            List<Cliente> clientes = clienteDAO.getAllClientes();
            listaClientes.addAll(clientes);

            //Enlazar lista
            //clientesTable.setItems(listaClientes);
            //enlazar nueva lista
            clientesTable.setItems(listaClientes);

            //Enlazar Celdas Clientes
            clmDNI.setCellValueFactory(new PropertyValueFactory<Cliente, String>("DNI"));
            clmNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
            clmApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
            clmTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
            clmMail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("mail"));

            //ClienteDAO clienteDAO = new ClienteDAO(connection);
            //List<Cliente> clientes = clienteDAO.getListaClientes(); // Obtener la lista de clientes
            //for (Cliente cliente : clientes) {
            //    System.out.println("Nombre: " + cliente.getNombre());
            //    System.out.println("Apellido: " + cliente.getApellido());
            //    System.out.println("Teléfono: " + cliente.getTelefono());
            //    System.out.println("Correo electrónico: " + cliente.getMail());
            //    System.out.println("-----------------------");
            //}
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            //stage.initStyle(StageStyle.UNDECORATED); // Oculta la barra de título
            // Establecer tamaño mínimo y máximo
            stage.setMinWidth(1280);
            stage.setMinHeight(920);
            stage.setMaxWidth(1920);
            stage.setMaxHeight(1080); // 2K resolution
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNuevoCliente(ActionEvent event) {
        // Lógica para el botón NuevoCliente
        FormClienteController formularioCliente = new FormClienteController();
        formularioCliente.mostrarVentanaPrincipal();
    }

    @FXML
    private void handleNuevaPoliza(ActionEvent event) {
        // Lógica para el botón NuevaPoliza
    }

    @FXML
    private void handleNuevoSiniestro(ActionEvent event) {
        // Lógica para el botón NuevoSiniestro
    }

    private void updateTime() {
        // Obtener la hora actual
        LocalTime currentTime = LocalTime.now();

        // Formatear la hora en formato HH:mm:ss
        String formattedTime = String.format("%02d:%02d:%02d",
                currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());

        // Actualizar el campo de la hora con la hora actualizada
        timeField.setText(formattedTime);
    }
}
