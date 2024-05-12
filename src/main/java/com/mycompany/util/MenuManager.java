package com.mycompany.util;

import com.mycompany.testjavafx.FormObtenerListadoController;
import com.mycompany.testjavafx.FormUsuariosController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.net.URL;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Clase que gestiona los menús de la aplicación, incluyendo menús contextuales
 * y menús basados en roles.
 */
public class MenuManager {

    /**
     * Configura el menú común para todos los usuarios.
     *
     * @param menuBar El {@link MenuBar} al que se agregarán los menús.
     */
    public void menuComun(MenuBar menuBar) {
        Menu archivoMenu = new Menu("Archivo");
        MenuItem salirMenuItem = new MenuItem("Salir");

        Menu listadosMenu = new Menu("Listados");
        MenuItem listarPolizasMenuItem = new MenuItem("Listar Polizas");
        MenuItem listarClientesMenuItem = new MenuItem("Listar Clientes");
        MenuItem listarSiniestrosMenuItem = new MenuItem("Listar Siniestros");
        MenuItem listarRecibosMenuItem = new MenuItem("Listar Recibos");

        Menu ayudaMenu = new Menu("Ayuda");
        MenuItem acercaDeMenuItem = new MenuItem("Acerca de");
        MenuItem ayudaMenuItem = new MenuItem("Ayuda");

        archivoMenu.getItems().addAll(salirMenuItem);
        listadosMenu.getItems().addAll(listarPolizasMenuItem, listarClientesMenuItem,
                listarSiniestrosMenuItem, listarRecibosMenuItem);
        ayudaMenu.getItems().addAll(acercaDeMenuItem, ayudaMenuItem);

        menuBar.getMenus().addAll(archivoMenu, listadosMenu, ayudaMenu);

        listarClientesMenuItem.setOnAction(event -> {
            FormObtenerListadoController obtenerListado = new FormObtenerListadoController();
            obtenerListado.mostrarVentanaPrincipal();
        });

        listarPolizasMenuItem.setOnAction(event -> {
            mostrarAlerta("Menu sin funcion.", "Pendiente de desarrollo. Usar menú Clientes");
        });

        listarSiniestrosMenuItem.setOnAction(event -> {
            mostrarAlerta("Menu sin funcion.", "Pendiente de desarrollo. Usar menú Clientes");
        });

        listarRecibosMenuItem.setOnAction(event -> {
            mostrarAlerta("Menu sin funcion.", "Pendiente de desarrollo. Usar menú Clientes");
        });
        // Configuración inicial del MenuItem de ayuda
        ayudaMenuItem.setOnAction(event -> showHelp());
        acercaDeMenuItem.setOnAction(event -> showAcercaDe());

        salirMenuItem.setOnAction(event -> Platform.exit());
    }

    /**
     * Muestra la ayuda de la aplicación.
     */
    public void showHelp() {
        Stage helpStage = new Stage();
        helpStage.setTitle("Ayuda de la Aplicación");

        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/ayuda/ayuda.html").toExternalForm());

        Scene scene = new Scene(webView, 600, 400);
        helpStage.setScene(scene);
        helpStage.show();
    }
    
    
    /**
     * Muestra la ayuda de la aplicación.
     */
    public void showAcercaDe() {
        Stage helpStage = new Stage();
        helpStage.setTitle("Acerca de segurin");

        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/ayuda/acercade.html").toExternalForm());

        Scene scene = new Scene(webView, 600, 400);
        helpStage.setScene(scene);
        helpStage.show();
    }

    /**
     * Construye menús específicos basados en el rol del usuario y los añade al
     * {@link MenuBar} proporcionado.
     *
     * @param menuBar El {@link MenuBar} al que se agregarán los menús.
     */
    public void construirMenus(MenuBar menuBar, String rol) {
        menuBar.getMenus().clear();
        menuComun(menuBar);
        Menu usuarioMenu = new Menu("Usuario");
        MenuItem configuracionItem = new MenuItem("Configuración");

        if ("administrador".equals(rol)) {
            Menu administradorMenu = new Menu("Administrar");
            MenuItem usuariosItem = new MenuItem("Usuarios");
            administradorMenu.getItems().addAll(usuariosItem);
            menuBar.getMenus().add(administradorMenu);

            usuariosItem.setOnAction(event -> {
                // Lógica para administrar usuarios
                FormUsuariosController controller = new FormUsuariosController();
                controller.mostrarVentanaPrincipal();

            });

        }

        configuracionItem.setOnAction(event -> {
            mostrarAlerta("Configuración", "Ventana de configuración.");
        });
        usuarioMenu.getItems().addAll(configuracionItem);
        menuBar.getMenus().add(usuarioMenu);

        // Menús adicionales para otros roles pueden ser añadidos aquí
    }

    /**
     * Muestra una alerta con el título y mensaje especificados. Crea una nueva
     * instancia de Alert con el tipo de alerta ERROR, establece el título y
     * mensaje especificados, y muestra la alerta modalmente. El método bloquea
     * la ejecución hasta que el usuario cierra la alerta.
     *
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
