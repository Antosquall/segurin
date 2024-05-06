package com.mycompany.model;

import java.time.LocalDate;

/**
 * Clase que representa un cliente dentro del sistema.
 * Incluye todos los atributos relevantes para un cliente así como métodos para acceder y modificar estos atributos.
 */
public class Cliente {
    private int idCliente;
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String mail;
    private LocalDate fechaNacimiento;
    private String genero;
    private int totalPolizas;
    private int bonificacion;
    private String estadoCivil;
    private int numParientes;
    private String profesion;
    private String estudios;
    private int ingresosAnuales;
    private LocalDate fechaRegistro;
    private LocalDate fechaBaja;
    private String observaciones;
    private String nacionalidad;
    private String referido;
    private String vip;

    /**
     * Constructor sin parámetros para crear un cliente vacío.
     */
    public Cliente() {
    }

    /**
     * Constructor con parámetros para crear un cliente con toda la información inicial necesaria.
     *
     * @param idCliente        Identificador único del cliente.
     * @param dni              Documento Nacional de Identidad del cliente.
     * @param nombre           Nombre del cliente.
     * @param apellido         Apellido del cliente.
     * @param direccion        Dirección del cliente.
     * @param telefono         Teléfono del cliente.
     * @param mail             Correo electrónico del cliente.
     * @param fechaNacimiento  Fecha de nacimiento del cliente.
     * @param genero           Género del cliente.
     * @param totalPolizas     Total de pólizas asociadas al cliente.
     * @param bonificacion     Bonificación aplicada al cliente.
     * @param estadoCivil      Estado civil del cliente.
     * @param numParientes     Número de parientes directos del cliente.
     * @param profesion        Profesión del cliente.
     * @param estudios         Nivel de estudios alcanzado por el cliente.
     * @param ingresosAnuales  Ingresos anuales del cliente.
     * @param fechaRegistro    Fecha de registro del cliente en el sistema.
     * @param fechaBaja        Fecha de baja del cliente en el sistema.
     * @param observaciones    Observaciones relevantes sobre el cliente.
     * @param nacionalidad     Nacionalidad del cliente.
     * @param referido         Referido por otro cliente.
     * @param vip              Indica si el cliente es considerado VIP.
     */
    public Cliente(int idCliente, String dni, String nombre, String apellido, String direccion, String telefono,
                   String mail, LocalDate fechaNacimiento, String genero, int totalPolizas, int bonificacion,
                   String estadoCivil, int numParientes, String profesion, String estudios, int ingresosAnuales,
                   LocalDate fechaRegistro, LocalDate fechaBaja, String observaciones, String nacionalidad,
                   String referido, String vip) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.totalPolizas = totalPolizas;
        this.bonificacion = bonificacion;
        this.estadoCivil = estadoCivil;
        this.numParientes = numParientes;
        this.profesion = profesion;
        this.estudios = estudios;
        this.ingresosAnuales = ingresosAnuales;
        this.fechaRegistro = fechaRegistro;
        this.fechaBaja = fechaBaja;
        this.observaciones = observaciones;
        this.nacionalidad = nacionalidad;
        this.referido = referido;
        this.vip = vip;
    }

    // Métodos getters y setters para cada propiedad del cliente. Documentados adecuadamente para facilitar la comprensión de su función.

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getDNI() { return dni; }
    public void setDNI(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getTotalPolizas() { return totalPolizas; }
    public void setTotalPolizas(int totalPolizas) { this.totalPolizas = totalPolizas; }

    public int getBonificacion() { return bonificacion; }
    public void setBonificacion(int bonificacion) { this.bonificacion = bonificacion; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public int getNumParientes() { return numParientes; }
    public void setNumParientes(int numParientes) { this.numParientes = numParientes; }

    public String getProfesion() { return profesion; }
    public void setProfesion(String profesion) { this.profesion = profesion; }

    public String getEstudios() { return estudios; }
    public void setEstudios(String estudios) { this.estudios = estudios; }

    public int getIngresosAnuales() { return ingresosAnuales; }
    public void setIngresosAnuales(int ingresosAnuales) { this.ingresosAnuales = ingresosAnuales; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public LocalDate getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(LocalDate fechaBaja) { this.fechaBaja = fechaBaja; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public String getReferido() { return referido; }
    public void setReferido(String referido) { this.referido = referido; }

    public String getVip() { return vip; }
    public void setVip(String vip) { this.vip = vip; }
}
