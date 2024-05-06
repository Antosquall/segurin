package com.mycompany.testjavafx;

import com.mycompany.util.Util;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Controlador para la interfaz gráfica de selección de formato de archivo para listados.
 */
public class FormObtenerListadoController implements Initializable {

    @FXML private RadioButton btnPDF;
    @FXML private RadioButton btnXLS;
    @FXML private RadioButton btnCSV;
    private final ToggleGroup groupGenero = new ToggleGroup();

    /**
     * Inicializa el controlador y configura el grupo de botones de radio.
     *
     * @param url La ubicación utilizada para resolver rutas relativas para el objeto raíz, o null si la ubicación no es conocida.
     * @param rb Los recursos utilizados para localizar el objeto raíz, o null si el objeto raíz no fue localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnPDF.setToggleGroup(groupGenero);
        btnXLS.setToggleGroup(groupGenero);
        btnCSV.setToggleGroup(groupGenero);
    }

    /**
     * Abre un cuadro de diálogo para elegir el archivo y guardar el listado.
     *
     * @param event El evento que disparó este método.
     */
    @FXML
    private void elegirArchivo(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialFileName("informe.pdf");
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("PDF Files", "*.pdf"),
                new ExtensionFilter("CSV Files", "*.csv"),
                new ExtensionFilter("Excel Files", "*.xls"));

        File f = fc.showSaveDialog(null);
        if (f != null) {
            String tipoArchivo = getSelectedOption();
            System.out.println("Guardar en: " + f.getAbsolutePath());
            Util.generarListadoClientes(f, tipoArchivo);
        }
    }
    
    /**
     * Obtiene el tipo de archivo seleccionado a partir del grupo de botones de radio.
     *
     * @return El tipo de archivo seleccionado.
     */
    public String getSelectedOption() {
        RadioButton selectedRadioButton = (RadioButton) groupGenero.getSelectedToggle();
        String tipo = "nulo";
        if (selectedRadioButton != null) {
            tipo = selectedRadioButton.getText();
        }
        return tipo;
    }

    /**
     * Muestra la ventana principal de la aplicación.
     */
    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("formObtenerListado.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setMinWidth(200);
            stage.setMinHeight(120);
            stage.setMaxWidth(200);
            stage.setMaxHeight(120);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
     @FXML
    private void handleCancelar(ActionEvent event) {
        // Obtiene la ventana (Stage) actual desde el evento, cerrándola.
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
