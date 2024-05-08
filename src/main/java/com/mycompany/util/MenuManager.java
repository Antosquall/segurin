package com.mycompany.util;

import com.mycompany.testjavafx.FormObtenerListadoController;
import com.mycompany.testjavafx.FormUsuariosController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

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

        archivoMenu.getItems().addAll(salirMenuItem);
        listadosMenu.getItems().addAll(listarPolizasMenuItem, listarClientesMenuItem,
                listarSiniestrosMenuItem, listarRecibosMenuItem);

        menuBar.getMenus().addAll(archivoMenu, listadosMenu);

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

        salirMenuItem.setOnAction(event -> Platform.exit());
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
