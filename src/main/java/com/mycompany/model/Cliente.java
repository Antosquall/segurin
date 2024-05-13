package com.mycompany.model;

import java.time.LocalDate;

/**
 * Clase que representa un cliente dentro del sistema. Incluye todos los
 * atributos relevantes para un cliente así como métodos para acceder y
 * modificar estos atributos.
 */
public class Cliente {

    /**
     * Identificador único del cliente.
     */
    private int idCliente;

    /**
     * Documento Nacional de Identidad del cliente.
     */
    private String dni;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Apellido del cliente.
     */
    private String apellido;

    /**
     * Dirección del cliente.
     */
    private String direccion;

    /**
     * Teléfono del cliente.
     */
    private String telefono;

    /**
     * Correo electrónico del cliente.
     */
    private String mail;

    /**
     * Fecha de nacimiento del cliente.
     */
    private LocalDate fechaNacimiento;

    /**
     * Género del cliente.
     */
    private String genero;

    /**
     * Total de pólizas asociadas al cliente.
     */
    private int totalPolizas;

    /**
     * Bonificación aplicada al cliente.
     */
    private int bonificacion;

    /**
     * Estado civil del cliente.
     */
    private String estadoCivil;

    /**
     * Número de parientes directos del cliente.
     */
    private int numParientes;

    /**
     * Profesión del cliente.
     */
    private String profesion;

    /**
     * Nivel de estudios alcanzado por el cliente.
     */
    private String estudios;

    /**
     * Ingresos anuales del cliente.
     */
    private int ingresosAnuales;

    /**
     * Fecha de registro del cliente en el sistema.
     */
    private LocalDate fechaRegistro;

    /**
     * Fecha de baja del cliente en el sistema.
     */
    private LocalDate fechaBaja;

    /**
     * Observaciones relevantes sobre el cliente.
     */
    private String observaciones;

    /**
     * Nacionalidad del cliente.
     */
    private String nacionalidad;

    /**
     * Referido por otro cliente.
     */
    private String referido;

    /**
     * Indica si el cliente es considerado VIP.
     */
    private String vip;

    /**
     * Constructor sin parámetros para crear un cliente vacío.
     */
    public Cliente() {
    }

    /**
     * Constructor con parámetros para crear un cliente con toda la información
     * inicial necesaria.
     *
     * @param idCliente Identificador único del cliente.
     * @param dni Documento Nacional de Identidad del cliente.
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param direccion Dirección del cliente.
     * @param telefono Teléfono del cliente.
     * @param mail Correo electrónico del cliente.
     * @param fechaNacimiento Fecha de nacimiento del cliente.
     * @param genero Género del cliente.
     * @param totalPolizas Total de pólizas asociadas al cliente.
     * @param bonificacion Bonificación aplicada al cliente.
     * @param estadoCivil Estado civil del cliente.
     * @param numParientes Número de parientes directos del cliente.
     * @param profesion Profesión del cliente.
     * @param estudios Nivel de estudios alcanzado por el cliente.
     * @param ingresosAnuales Ingresos anuales del cliente.
     * @param fechaRegistro Fecha de registro del cliente en el sistema.
     * @param fechaBaja Fecha de baja del cliente en el sistema.
     * @param observaciones Observaciones relevantes sobre el cliente.
     * @param nacionalidad Nacionalidad del cliente.
     * @param referido Referido por otro cliente.
     * @param vip Indica si el cliente es considerado VIP.
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

    public Cliente(int idCliente, String dni, String nombre, String apellido) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return el identificador único del cliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Obtiene el Documento Nacional de Identidad del cliente.
     *
     * @return el Documento Nacional de Identidad del cliente.
     */
    public String getDNI() {
        return dni;
    }

    public void setDNI(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return el nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del cliente.
     *
     * @return el apellido del cliente.
     */
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return la dirección del cliente.
     */
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el teléfono del cliente.
     *
     * @return el teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return el correo electrónico del cliente.
     */
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Obtiene la fecha de nacimiento del cliente.
     *
     * @return la fecha de nacimiento del cliente.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el género del cliente.
     *
     * @return el género del cliente.
     */
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtiene el total de pólizas asociadas al cliente.
     *
     * @return el total de pólizas asociadas al cliente.
     */
    public int getTotalPolizas() {
        return totalPolizas;
    }

    public void setTotalPolizas(int totalPolizas) {
        this.totalPolizas = totalPolizas;
    }

    /**
     * Obtiene la bonificación aplicada al cliente.
     *
     * @return la bonificación aplicada al cliente.
     */
    public int getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(int bonificacion) {
        this.bonificacion = bonificacion;
    }

    /**
     * Obtiene el estado civil del cliente.
     *
     * @return el estado civil del cliente.
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * Obtiene el número de parientes directos del cliente.
     *
     * @return el número de parientes directos del cliente.
     */
    public int getNumParientes() {
        return numParientes;
    }

    public void setNumParientes(int numParientes) {
        this.numParientes = numParientes;
    }

    /**
     * Obtiene la profesión del cliente.
     *
     * @return la profesión del cliente.
     */
    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    /**
     * Obtiene el nivel de estudios alcanzado por el cliente.
     *
     * @return el nivel de estudios alcanzado por el cliente.
     */
    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    /**
     * Obtiene los ingresos anuales del cliente.
     *
     * @return los ingresos anuales del cliente.
     */
    public int getIngresosAnuales() {
        return ingresosAnuales;
    }

    public void setIngresosAnuales(int ingresosAnuales) {
        this.ingresosAnuales = ingresosAnuales;
    }

    /**
     * Obtiene la fecha de registro del cliente en el sistema.
     *
     * @return la fecha de registro del cliente en el sistema.
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Obtiene la fecha de baja del cliente en el sistema.
     *
     * @return la fecha de baja del cliente en el sistema.
     */
    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    /**
     * Obtiene las observaciones relevantes sobre el cliente.
     *
     * @return las observaciones relevantes sobre el cliente.
     */
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Obtiene la nacionalidad del cliente.
     *
     * @return la nacionalidad del cliente.
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Obtiene el cliente referido por otro cliente.
     *
     * @return el cliente referido por otro cliente.
     */
    public String getReferido() {
        return referido;
    }

    public void setReferido(String referido) {
        this.referido = referido;
    }

    /**
     * Indica si el cliente es considerado VIP.
     *
     * @return true si el cliente es considerado VIP, false en caso contrario.
     */
    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }
}
