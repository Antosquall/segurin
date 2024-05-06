package com.mycompany.util;

import com.mycompany.testjavafx.FormObtenerListadoController;
import com.mycompany.testjavafx.FormUsuariosController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Clase que gestiona los menús de la aplicación, incluyendo menús contextuales y menús basados en roles.
 */
public class MenuManager {

    /**
     * Configura el menú común para todos los usuarios.
     *
     * @param menuBar El {@link MenuBar} al que se agregarán los menús.
     */
    public void menuComun(MenuBar menuBar) {
        Menu archivoMenu = new Menu("Archivo");
        MenuItem nuevoMenuItem = new MenuItem("Nuevo");//Pendiente de desarrollo.
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
            mostrarAlerta("Menu sin funcion.", "Pendiente de desarrollo.");
        });
        
        listarSiniestrosMenuItem.setOnAction(event -> {
            mostrarAlerta("Menu sin funcion.", "Pendiente de desarrollo.");
        });
        
        listarRecibosMenuItem.setOnAction(event -> {
            mostrarAlerta("Menu sin funcion.", "Pendiente de desarrollo.");
        });

        salirMenuItem.setOnAction(event -> Platform.exit());
    }

    /**
     * Construye menús específicos basados en el rol del usuario y los añade al {@link MenuBar} proporcionado.
     *
     * @param menuBar El {@link MenuBar} al que se agregarán los menús.
     * @param rolUsuario El rol del usuario que determina qué menús se deben mostrar.
     */
    public void construirMenus(MenuBar menuBar, String rolUsuario) {
        menuBar.getMenus().clear();
        menuComun(menuBar);

        if ("administrador".equals(rolUsuario)) {
            Menu administradorMenu = new Menu("Administrar");
            MenuItem usuariosItem = new MenuItem("Usuarios");
            MenuItem configuracionItem = new MenuItem("Configuración");

            usuariosItem.setOnAction(event -> {
                FormUsuariosController obtenerusuario = new FormUsuariosController();
                obtenerusuario.mostrarVentanaPrincipal();
            });
            
            configuracionItem.setOnAction(event -> {
                mostrarAlerta("Menu sin funcion.", "Pendiente de desarrollo.");
            });
            
            
            

            administradorMenu.getItems().addAll(usuariosItem, configuracionItem);
            menuBar.getMenus().add(administradorMenu);
        } else if ("pdtDesarrllo".equals(rolUsuario)) { //usuario. Pendiente de desarrllo
            Menu usuarioMenu = new Menu("Opciones");
            MenuItem perfilItem = new MenuItem("Perfil");
            MenuItem configuracionItem = new MenuItem("Configuración");

            perfilItem.setOnAction(event -> {
                // Lógica para ver perfil...
            });
            configuracionItem.setOnAction(event -> {
                // Lógica para configuración...
            });

            usuarioMenu.getItems().addAll(perfilItem, configuracionItem);
            menuBar.getMenus().add(usuarioMenu);
        }
    }
    
        private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
