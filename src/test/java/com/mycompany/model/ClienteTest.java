/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.model;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author User
 */
public class ClienteTest {
    
    public ClienteTest() {
    }

    /**
     * Test of getIdCliente method, of class Cliente.
     */
    @Test
    public void testGetIdCliente() {
        System.out.println("getIdCliente");
        Cliente instance = new Cliente();
        int expResult = 0;
        int result = instance.getIdCliente();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIdCliente method, of class Cliente.
     */
    @Test
    public void testSetIdCliente() {
        System.out.println("setIdCliente");
        int idCliente = 0;
        Cliente instance = new Cliente();
        instance.setIdCliente(idCliente);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDNI method, of class Cliente.
     */
    @Test
    public void testGetDNI() {
        System.out.println("getDNI");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getDNI();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDNI method, of class Cliente.
     */
    @Test
    public void testSetDNI() {
        System.out.println("setDNI");
        String dni = "";
        Cliente instance = new Cliente();
        instance.setDNI(dni);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNombre method, of class Cliente.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getNombre();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNombre method, of class Cliente.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String nombre = "";
        Cliente instance = new Cliente();
        instance.setNombre(nombre);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getApellido method, of class Cliente.
     */
    @Test
    public void testGetApellido() {
        System.out.println("getApellido");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getApellido();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setApellido method, of class Cliente.
     */
    @Test
    public void testSetApellido() {
        System.out.println("setApellido");
        String apellido = "";
        Cliente instance = new Cliente();
        instance.setApellido(apellido);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDireccion method, of class Cliente.
     */
    @Test
    public void testGetDireccion() {
        System.out.println("getDireccion");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getDireccion();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDireccion method, of class Cliente.
     */
    @Test
    public void testSetDireccion() {
        System.out.println("setDireccion");
        String direccion = "";
        Cliente instance = new Cliente();
        instance.setDireccion(direccion);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTelefono method, of class Cliente.
     */
    @Test
    public void testGetTelefono() {
        System.out.println("getTelefono");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getTelefono();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTelefono method, of class Cliente.
     */
    @Test
    public void testSetTelefono() {
        System.out.println("setTelefono");
        String telefono = "";
        Cliente instance = new Cliente();
        instance.setTelefono(telefono);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMail method, of class Cliente.
     */
    @Test
    public void testGetMail() {
        System.out.println("getMail");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getMail();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMail method, of class Cliente.
     */
    @Test
    public void testSetMail() {
        System.out.println("setMail");
        String mail = "";
        Cliente instance = new Cliente();
        instance.setMail(mail);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFechaNacimiento method, of class Cliente.
     */
    @Test
    public void testGetFechaNacimiento() {
        System.out.println("getFechaNacimiento");
        Cliente instance = new Cliente();
        LocalDate expResult = null;
        LocalDate result = instance.getFechaNacimiento();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFechaNacimiento method, of class Cliente.
     */
    @Test
    public void testSetFechaNacimiento() {
        System.out.println("setFechaNacimiento");
        LocalDate fechaNacimiento = null;
        Cliente instance = new Cliente();
        instance.setFechaNacimiento(fechaNacimiento);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGenero method, of class Cliente.
     */
    @Test
    public void testGetGenero() {
        System.out.println("getGenero");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getGenero();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGenero method, of class Cliente.
     */
    @Test
    public void testSetGenero() {
        System.out.println("setGenero");
        String genero = "";
        Cliente instance = new Cliente();
        instance.setGenero(genero);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalPolizas method, of class Cliente.
     */
    @Test
    public void testGetTotalPolizas() {
        System.out.println("getTotalPolizas");
        Cliente instance = new Cliente();
        int expResult = 0;
        int result = instance.getTotalPolizas();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTotalPolizas method, of class Cliente.
     */
    @Test
    public void testSetTotalPolizas() {
        System.out.println("setTotalPolizas");
        int totalPolizas = 0;
        Cliente instance = new Cliente();
        instance.setTotalPolizas(totalPolizas);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBonificacion method, of class Cliente.
     */
    @Test
    public void testGetBonificacion() {
        System.out.println("getBonificacion");
        Cliente instance = new Cliente();
        int expResult = 0;
        int result = instance.getBonificacion();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBonificacion method, of class Cliente.
     */
    @Test
    public void testSetBonificacion() {
        System.out.println("setBonificacion");
        int bonificacion = 0;
        Cliente instance = new Cliente();
        instance.setBonificacion(bonificacion);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEstadoCivil method, of class Cliente.
     */
    @Test
    public void testGetEstadoCivil() {
        System.out.println("getEstadoCivil");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getEstadoCivil();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEstadoCivil method, of class Cliente.
     */
    @Test
    public void testSetEstadoCivil() {
        System.out.println("setEstadoCivil");
        String estadoCivil = "";
        Cliente instance = new Cliente();
        instance.setEstadoCivil(estadoCivil);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumParientes method, of class Cliente.
     */
    @Test
    public void testGetNumParientes() {
        System.out.println("getNumParientes");
        Cliente instance = new Cliente();
        int expResult = 0;
        int result = instance.getNumParientes();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumParientes method, of class Cliente.
     */
    @Test
    public void testSetNumParientes() {
        System.out.println("setNumParientes");
        int numParientes = 0;
        Cliente instance = new Cliente();
        instance.setNumParientes(numParientes);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProfesion method, of class Cliente.
     */
    @Test
    public void testGetProfesion() {
        System.out.println("getProfesion");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getProfesion();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProfesion method, of class Cliente.
     */
    @Test
    public void testSetProfesion() {
        System.out.println("setProfesion");
        String profesion = "";
        Cliente instance = new Cliente();
        instance.setProfesion(profesion);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEstudios method, of class Cliente.
     */
    @Test
    public void testGetEstudios() {
        System.out.println("getEstudios");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getEstudios();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEstudios method, of class Cliente.
     */
    @Test
    public void testSetEstudios() {
        System.out.println("setEstudios");
        String estudios = "";
        Cliente instance = new Cliente();
        instance.setEstudios(estudios);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIngresosAnuales method, of class Cliente.
     */
    @Test
    public void testGetIngresosAnuales() {
        System.out.println("getIngresosAnuales");
        Cliente instance = new Cliente();
        int expResult = 0;
        int result = instance.getIngresosAnuales();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIngresosAnuales method, of class Cliente.
     */
    @Test
    public void testSetIngresosAnuales() {
        System.out.println("setIngresosAnuales");
        int ingresosAnuales = 0;
        Cliente instance = new Cliente();
        instance.setIngresosAnuales(ingresosAnuales);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFechaRegistro method, of class Cliente.
     */
    @Test
    public void testGetFechaRegistro() {
        System.out.println("getFechaRegistro");
        Cliente instance = new Cliente();
        LocalDate expResult = null;
        LocalDate result = instance.getFechaRegistro();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFechaRegistro method, of class Cliente.
     */
    @Test
    public void testSetFechaRegistro() {
        System.out.println("setFechaRegistro");
        LocalDate fechaRegistro = null;
        Cliente instance = new Cliente();
        instance.setFechaRegistro(fechaRegistro);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFechaBaja method, of class Cliente.
     */
    @Test
    public void testGetFechaBaja() {
        System.out.println("getFechaBaja");
        Cliente instance = new Cliente();
        LocalDate expResult = null;
        LocalDate result = instance.getFechaBaja();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFechaBaja method, of class Cliente.
     */
    @Test
    public void testSetFechaBaja() {
        System.out.println("setFechaBaja");
        LocalDate fechaBaja = null;
        Cliente instance = new Cliente();
        instance.setFechaBaja(fechaBaja);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getObservaciones method, of class Cliente.
     */
    @Test
    public void testGetObservaciones() {
        System.out.println("getObservaciones");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getObservaciones();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setObservaciones method, of class Cliente.
     */
    @Test
    public void testSetObservaciones() {
        System.out.println("setObservaciones");
        String observaciones = "";
        Cliente instance = new Cliente();
        instance.setObservaciones(observaciones);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNacionalidad method, of class Cliente.
     */
    @Test
    public void testGetNacionalidad() {
        System.out.println("getNacionalidad");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getNacionalidad();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNacionalidad method, of class Cliente.
     */
    @Test
    public void testSetNacionalidad() {
        System.out.println("setNacionalidad");
        String nacionalidad = "";
        Cliente instance = new Cliente();
        instance.setNacionalidad(nacionalidad);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReferido method, of class Cliente.
     */
    @Test
    public void testGetReferido() {
        System.out.println("getReferido");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getReferido();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReferido method, of class Cliente.
     */
    @Test
    public void testSetReferido() {
        System.out.println("setReferido");
        String referido = "";
        Cliente instance = new Cliente();
        instance.setReferido(referido);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVip method, of class Cliente.
     */
    @Test
    public void testGetVip() {
        System.out.println("getVip");
        Cliente instance = new Cliente();
        String expResult = "";
        String result = instance.getVip();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVip method, of class Cliente.
     */
    @Test
    public void testSetVip() {
        System.out.println("setVip");
        String vip = "";
        Cliente instance = new Cliente();
        instance.setVip(vip);
        fail("The test case is a prototype.");
    }
    
}
