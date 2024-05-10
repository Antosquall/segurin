module com.mycompany.testjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.naming;
    requires mysql.connector.java;
    requires jasperreports;

    opens com.mycompany.testjavafx to javafx.fxml;
    opens com.mycompany.model;
    
    exports com.mycompany.testjavafx;
     // Exporta el paquete para que sea accesible por otros módulos
    exports com.mycompany.persistance;
    requires java.desktop;
    requires java.base;
    
    
    // Si estás usando alguna librería que también necesita ser visible, por ejemplo, para JUnit
    //requires junit;
    requires org.mockito;
    requires javafx.base;

}
