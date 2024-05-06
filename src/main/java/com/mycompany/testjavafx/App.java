package com.mycompany.testjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.stage.StageStyle;

/**
 * Clase principal de la aplicación segurin.
 * Esta clase inicia la interfaz gráfica y configura la escena inicial.
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Inicia la aplicación con la ventana principal.
     * Este método carga el FXML para la vista inicial y establece la configuración básica del escenario (Stage).
     * 
     * @param stage El escenario principal en el que se mostrará la vista.
     * @throws IOException Si hay un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la vista inicial desde un archivo FXML.
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        // Configura el estilo del escenario para no mostrar la barra de título.
        stage.initStyle(StageStyle.UNDECORATED);
        
        // Establece y muestra la escena en el escenario.
        scene = new Scene(root, 400, 250);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la vista raíz de la escena actual.
     * Este método permite cambiar dinámicamente el contenido de la escena.
     * 
     * @param fxml El nombre del archivo FXML que define la nueva vista raíz.
     * @throws IOException Si hay un error al cargar el archivo FXML.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Carga un archivo FXML y retorna su raíz como un objeto Parent.
     * Este método es utilizado para cambiar la vista dentro de la escena actual.
     * 
     * @param fxml El nombre del archivo FXML sin la extensión.
     * @return La raíz del archivo FXML como un objeto Parent.
     * @throws IOException Si hay un error al cargar el archivo FXML.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Punto de entrada principal de la aplicación.
     * 
     * @param args Argumentos de línea de comando pasados al programa.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
