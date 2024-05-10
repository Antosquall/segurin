package com.mycompany.model;

import java.time.LocalDate;

/**
 * Clase que representa una póliza de seguro dentro del sistema.
 * Contiene todos los atributos relevantes para una póliza y métodos para acceder y modificar estos atributos.
 */
public class Poliza {

    /**
     * Identificador único de la póliza.
     */
    private int idPoliza;

    /**
     * Identificador del cliente asociado a la póliza.
     */
    private int idCliente;

    /**
     * Identificador del recibo asociado a la póliza.
     */
    private String idRecibo;

    /**
     * Número de la póliza.
     */
    private String numeroPoliza;

    /**
     * Fecha de emisión de la póliza.
     */
    private LocalDate fechaEmision;

    /**
     * Fecha de vencimiento de la póliza.
     */
    private LocalDate fechaVencimiento;

    /**
     * Tipo de cobertura de la póliza.
     */
    private String tipoCobertura;

    /**
     * Cobertura adicional de la póliza.
     */
    private String coberturaAdicional;

    /**
     * Agente comercial asociado a la póliza.
     */
    private String comercial;

    /**
     * Comentarios adicionales sobre la póliza.
     */
    private String comentarios;

    /**
     * Constructor sin parámetros para crear una póliza vacía.
     */
    public Poliza() {
    }

    /**
     * Constructor con parámetros para inicializar una póliza con toda la información relevante.
     *
     * @param idPoliza           Identificador único de la póliza.
     * @param idCliente          Identificador del cliente asociado a la póliza.
     * @param idRecibo           Identificador del recibo asociado a la póliza.
     * @param numeroPoliza       Número de la póliza.
     * @param fechaEmision       Fecha de emisión de la póliza.
     * @param fechaVencimiento   Fecha de vencimiento de la póliza.
     * @param tipoCobertura      Tipo de cobertura de la póliza.
     * @param coberturaAdicional Cobertura adicional de la póliza.
     * @param comercial          Agente comercial asociado a la póliza.
     * @param comentarios        Comentarios adicionales sobre la póliza.
     */
    public Poliza(int idPoliza, int idCliente, String idRecibo, String numeroPoliza, LocalDate fechaEmision,
                  LocalDate fechaVencimiento, String tipoCobertura, String coberturaAdicional, String comercial, String comentarios) {
        this.idPoliza = idPoliza;
        this.idCliente = idCliente;
        this.idRecibo = idRecibo;
        this.numeroPoliza = numeroPoliza;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.tipoCobertura = tipoCobertura;
        this.coberturaAdicional = coberturaAdicional;
        this.comercial = comercial;
        this.comentarios = comentarios;
    }

    // Métodos de acceso y modificación para cada atributo de la póliza, documentados para clarificar su propósito.

    /**
     * Obtiene el identificador único de la póliza.
     * @return el identificador único de la póliza.
     */
    public int getIdPoliza() { return idPoliza; }
    public void setIdPoliza(int idPoliza) { this.idPoliza = idPoliza; }

    /**
     * Obtiene el identificador del cliente asociado a la póliza.
     * @return el identificador del cliente asociado a la póliza.
     */
    public int getID_Cliente() { return idCliente; }
    public void setID_Cliente(int idCliente) { this.idCliente = idCliente; }

    /**
     * Obtiene el identificador del recibo asociado a la póliza.
     * @return el identificador del recibo asociado a la póliza.
     */
    public String getIdRecibo() { return idRecibo; }
    public void setIdRecibo(String idRecibo) { this.idRecibo = idRecibo; }

    /**
     * Obtiene el número de la póliza.
     * @return el número de la póliza.
     */
    public String getNumeroPoliza() { return numeroPoliza; }
    public void setNumeroPoliza(String numeroPoliza) { this.numeroPoliza = numeroPoliza; }

    /**
     * Obtiene la fecha de emisión de la póliza.
     * @return la fecha de emisión de la póliza.
     */
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    /**
     * Obtiene la fecha de vencimiento de la póliza.
     * @return la fecha de vencimiento de la póliza.
     */
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    /**
     * Obtiene el tipo de cobertura de la póliza.
     * @return el tipo de cobertura de la póliza.
     */
    public String getTipoCobertura() { return tipoCobertura; }
    public void setTipoCobertura(String tipoCobertura) { this.tipoCobertura = tipoCobertura; }

    /**
     * Obtiene la cobertura adicional de la póliza.
     * @return la cobertura adicional de la póliza.
     */
    public String getCoberturaAdicional() { return coberturaAdicional; }
    public void setCoberturaAdicional(String coberturaAdicional) { this.coberturaAdicional = coberturaAdicional; }

    /**
     * Obtiene el agente comercial asociado a la póliza.
     * @return el agente comercial asociado a la póliza.
     */
    public String getComercial() { return comercial; }
    public void setComercial(String comercial) { this.comercial = comercial; }

    /**
     * Obtiene los comentarios adicionales sobre la póliza.
     * @return los comentarios adicionales sobre la póliza.
     */
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
