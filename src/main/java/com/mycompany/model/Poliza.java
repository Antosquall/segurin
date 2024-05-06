package com.mycompany.model;

import java.time.LocalDate;

/**
 * Clase que representa una póliza de seguro dentro del sistema.
 * Contiene todos los atributos relevantes para una póliza y métodos para acceder y modificar estos atributos.
 */
public class Poliza {

    private int idPoliza;
    private int idCliente;
    private String idRecibo;
    private String numeroPoliza;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String tipoCobertura;
    private String coberturaAdicional;
    private String comercial;
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

    public int getIdPoliza() { return idPoliza; }
    public void setIdPoliza(int idPoliza) { this.idPoliza = idPoliza; }

    public int getID_Cliente() { return idCliente; }
    public void setID_Cliente(int idCliente) { this.idCliente = idCliente; }

    public String getIdRecibo() { return idRecibo; }
    public void setIdRecibo(String idRecibo) { this.idRecibo = idRecibo; }

    public String getNumeroPoliza() { return numeroPoliza; }
    public void setNumeroPoliza(String numeroPoliza) { this.numeroPoliza = numeroPoliza; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getTipoCobertura() { return tipoCobertura; }
    public void setTipoCobertura(String tipoCobertura) { this.tipoCobertura = tipoCobertura; }

    public String getCoberturaAdicional() { return coberturaAdicional; }
    public void setCoberturaAdicional(String coberturaAdicional) { this.coberturaAdicional = coberturaAdicional; }

    public String getComercial() { return comercial; }
    public void setComercial(String comercial) { this.comercial = comercial; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
