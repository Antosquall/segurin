/**
 * Define el módulo com.mycompany.testjavafx, que es parte de una aplicación JavaFX.
 * Este módulo gestiona la interfaz de usuario y la lógica de negocio para una aplicación que manipula pólizas de seguro.
 * Incluye dependencias necesarias para la operación de la interfaz de usuario, acceso a bases de datos, y generación de informes.
 */

/**
 * Requerimientos del módulo:
 * - `javafx.controls`: Proporciona clases e interfaces esenciales para componentes de la interfaz de usuario.
 * - `javafx.fxml`: Permite la carga de archivos FXML para definir la interfaz de usuario.
 * - `javafx.graphics`: Incluye clases para gráficos, que son utilizadas indirectamente por otros módulos JavaFX.
 * - `javafx.base`: Proporciona las clases base usadas por JavaFX.
 * - `java.sql`: Permite la conexión y la ejecución de operaciones en bases de datos SQL.
 * - `java.naming`: Utilizado para acceso a JNDI, necesario para configuraciones y recursos nombrados.
 * - `mysql.connector.java`: Driver de MySQL para Java, permite la conexión a bases de datos MySQL.
 * - `jasperreports`: Utilizado para la generación de informes desde la aplicación.
 * - `javafx.web`: Proporciona soporte para contenido web en aplicaciones JavaFX.
 * - `java.desktop`: Utilizado por algunas dependencias de UI y de soporte a la plataforma.
 * - `java.base`: Módulo base de Java, siempre requerido.
 * - `org.mockito`: Biblioteca para la creación de objetos simulados en pruebas unitarias.
 */

/**
 * Apertura de paquetes:
 * - `com.mycompany.testjavafx`: Abre el paquete principal del módulo para que javafx.fxml pueda acceder a sus clases,
 *   necesario para cargar correctamente los controladores FXML.
 * - `com.mycompany.model`: Abre el paquete que contiene los modelos de datos para uso general dentro del módulo,
 *   facilitando la manipulación de estos modelos en diferentes partes de la aplicación.
 */

/**
 * Exportaciones de paquetes:
 * - `com.mycompany.testjavafx`: Exporta el paquete principal para permitir que otros módulos utilicen sus clases públicas.
 * - `com.mycompany.persistance`: Exporta el paquete de persistencia, permitiendo el acceso a clases que gestionan la persistencia de datos.
 */


module com.mycompany.testjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires java.naming;
    requires mysql.connector.java;
    requires jasperreports;
    requires javafx.web;

    opens com.mycompany.testjavafx to javafx.fxml;
    opens com.mycompany.model;
    
    
    exports com.mycompany.testjavafx;
     
    exports com.mycompany.persistance;
    requires java.desktop;
    requires java.base;
    

    requires org.mockito;

}
