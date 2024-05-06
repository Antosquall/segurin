module com.mycompany.testjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires hibernate.entitymanager;
    requires mysql.connector.java;
    requires org.hibernate.commons.annotations;
    requires jasperreports;

    opens com.mycompany.testjavafx to javafx.fxml;
    opens com.mycompany.model;
    
    exports com.mycompany.testjavafx;
 requires java.desktop;
}
