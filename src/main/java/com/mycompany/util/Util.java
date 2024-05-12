package com.mycompany.util;

import com.mycompany.testjavafx.ConexionMySQL;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import java.awt.Desktop;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * Clase de utilidades para la aplicación.
 */
public class Util {

    /**
     * Verifica si un DNI es válido según el estándar español.
     *
     * @param dni El DNI a verificar.
     * @return true si el DNI es válido, false en caso contrario.
     */
    public static boolean verificarDNI(String dni) {
        String regex = "\\d{8}[A-HJ-NP-TV-Z]";
        if (!dni.matches(regex)) {
            return false;
        }
        String numero = dni.substring(0, 8);
        char letra = dni.charAt(8);
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numeroDNI;
        try {
            numeroDNI = Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            return false;
        }
        char letraEsperada = letras.charAt(numeroDNI % 23);
        return letra == letraEsperada;
    }

    /**
     * Verifica si una cadena de texto contiene potenciales inyecciones SQL.
     *
     * @param input El texto a verificar.
     * @return true si se detecta potencial inyección SQL, false en caso
     * contrario.
     */
    public static boolean verificarInyeccionSQL(String input) {
        String regex = "(?i).*\\b(SELECT|INSERT|UPDATE|DELETE|CREATE|DROP|ALTER|TRUNCATE|GRANT|REVOKE|UNION|FROM|WHERE|AND|OR|EXEC|EXECUTE|DECLARE|CAST)\\b.*";
        return Pattern.matches(regex, input);
    }

    /**
     * Verifica si la longitud de un texto no excede un máximo especificado.
     *
     * @param input El texto a verificar.
     * @param maxLength La longitud máxima permitida.
     * @return true si el texto es válido, false en caso contrario.
     */
    public static boolean verificarLongitud(String input, int maxLength) {
        if (input == null) {
            return false;
        }
        return input.length() <= maxLength;
    }

    /**
     * Genera un listado de clientes y lo exporta a un archivo PDF o CSV
     * dependiendo de la opción elegida.
     *
     * @param f El archivo donde se guardará el listado.
     * @param tipoExp El tipo de exportación ('PDF' o 'CSV').
     */
    public static void generarListadoClientes(File f, String tipoExp) {
        try {
            InputStream inputStream = Util.class.getResourceAsStream("/informes/Clientes2.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            Map<String, Object> parametros = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, ConexionMySQL.conectar());

            if ("CSV".equals(tipoExp)) {
                JRCsvExporter exporter = new JRCsvExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleWriterExporterOutput(f));
                SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
                configuration.setWriteBOM(Boolean.TRUE);
                configuration.setRecordDelimiter("\r\n");
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            } else if ("PDF".equals(tipoExp)) {
                JasperExportManager.exportReportToPdfFile(jasperPrint, f.getAbsolutePath());
            } else {
                System.out.println("Formato de exportación no soportado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Carga elementos desde un archivo de texto y los almacena en un mapa.
     *
     * @return Un mapa que contiene listas de elementos organizados por
     * categorías.
     * @throws IOException Si no se puede leer el archivo.
     */
    public static Map<String, List<String>> loadItemsFromFile() throws IOException {
        Map<String, List<String>> itemsMap = new HashMap<>();
        InputStream is = Util.class.getResourceAsStream("/datos/datosComboBox.txt");
        if (is == null) {
            throw new IOException("No se puede cargar el recurso: /datos/datosComboBox.txt");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            String currentKey = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[")) {
                    currentKey = line.substring(1, line.length() - 1).toLowerCase();
                    itemsMap.putIfAbsent(currentKey, new ArrayList<>());
                } else if (currentKey != null && !line.trim().isEmpty()) {
                    itemsMap.get(currentKey).add(line.trim());
                }
            }
        }
        return itemsMap;
    }

    /**
     * Configura un filtro que permite solo números y opcionalmente un punto
     * decimal con hasta dos dígitos.
     *
     * @param textField El TextField al que se aplicará el filtro.
     */
    public static void configurarFiltroNumericoDecimal(TextField textField) {
        TextFormatter<String> formatoDecimal = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Permite números y un punto decimal con hasta dos posiciones decimales.
            if (newText.matches("\\d*(\\.\\d{0,2})?")) {
                return change;
            }
            return null; // Ignora el cambio si no cumple el formato
        });

        textField.setTextFormatter(formatoDecimal);
    }

    /**
     * Genera un informe de cliente en formato PDF y lo abre con el software de
     * PDF predeterminado del sistema. El informe se genera a partir de un
     * archivo de informe precompilado (JasperReport), utilizando los datos del
     * cliente identificado por el ID proporcionado. El informe se genera con la
     * información del cliente obtenida de la base de datos. El archivo PDF
     * generado se guarda en el directorio actual con el nombre
     * "output_report.pdf". En caso de éxito, imprime un mensaje indicando que
     * el reporte se generó correctamente y abre automáticamente el PDF generado
     * con el software de PDF predeterminado del sistema. Si no se puede abrir
     * el PDF automáticamente, imprime un mensaje indicando que no se puede
     * abrir el PDF automáticamente en este sistema. Si ocurre algún error
     * durante la generación o apertura del reporte, imprime un mensaje de error
     * detallado.
     *
     * @param clienteId El ID del cliente para el cual se generará el informe.
     */
    public static void generateReport(int clienteId) {
        try {
            // Cargar el informe precompilado
            InputStream inputStream = Util.class.getResourceAsStream("/informes/datosCliente.jasper");
            JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);

            // Parametros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ClienteID", clienteId);

            // Conectar a la base de datos y llenar el informe
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, ConexionMySQL.conectar());
            JasperPrint print = JasperFillManager.fillReport(report, parameters, ConexionMySQL.conectar());

            // Exportar a PDF
            File pdfFile = new File("output_report.pdf");
            JasperExportManager.exportReportToPdfFile(print, pdfFile.getAbsolutePath());

            System.out.println("Reporte generado correctamente.");

            // Abrir el PDF con el software de PDF predeterminado del sistema
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("No se puede abrir el PDF automáticamente en este sistema.");
            }
        } catch (Exception ex) {
            System.err.println("Error al generar o abrir el reporte: " + ex.getMessage());
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Muestra un mensaje de información.
     *
     * @param titulo El título del mensaje.
     * @param contenido El contenido del mensaje.
     */
    public static void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param titulo El título del mensaje de error.
     * @param mensaje El contenido del mensaje de error.
     */
    public static void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private Connection getConnection() {
        // Implementar conexión a base de datos
        ConexionMySQL.conectar();
        return null;
    }

}
