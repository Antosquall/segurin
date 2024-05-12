package com.mycompany.util;

import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ConfigLoader {

    private static ConfigLoader instance;
    private Properties config;
    private Properties defaultConfig;

    private ConfigLoader() {
        config = new Properties();
        defaultConfig = new Properties();

            // Carga la configuración predeterminada
        try (InputStream defaultInput = getClass().getResourceAsStream("/datos/default.properties")) {
            if (defaultInput == null) {
                throw new IllegalStateException("default.properties file not found in classpath");
            }
            defaultConfig.load(defaultInput);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load default configuration properties", ex);
        }

        // Intentar cargar la configuración desde el directorio del .jar
        try {
            URL jarLocationUrl = ConfigLoader.class.getProtectionDomain().getCodeSource().getLocation();
            File jarFile = new File(jarLocationUrl.toURI());
            File configDir = new File(jarFile.getParentFile(), "config");
            File configFile = new File(configDir, "config.properties");

            if (configFile.exists()) {
                try (InputStream externalInput = new FileInputStream(configFile)) {
                    config.load(externalInput);
                    // Reemplaza las configuraciones predeterminadas solo si los valores no están vacíos
                    defaultConfig.forEach((key, value) -> {
                        String externalValue = config.getProperty(key.toString());
                        if (externalValue != null && !externalValue.isEmpty()) {
                            defaultConfig.setProperty(key.toString(), externalValue);
                        }
                    });
                } catch (IOException e) {
                    System.out.println("External config file found, but an error occurred: " + e.getMessage());
                }
            } else {
                System.out.println("No external config file found at " + configFile.getAbsolutePath() + ", using default settings.");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error determining .jar directory", e);
        }
    }

    public static synchronized ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return defaultConfig.getProperty(key);
    }
}
