package com.mycompany.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase utilitaria para manejar la encriptación de contraseñas.
 */
public class SecurePassword {
    
    /**
     * Encripta una contraseña usando el algoritmo SHA-256.
     * 
     * @param password La contraseña a encriptar.
     * @return La contraseña encriptada en formato hexadecimal, o null si ocurre un error.
     */
    public static String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Método principal para probar la encriptación de una contraseña.
     * 
     * @param args Argumentos pasados al programa (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        String password = "test";
        String encryptedPassword = encryptPassword(password);
        System.out.println("Contraseña encriptada: " + encryptedPassword);
    }
}
