package com.mycompany.model;

import java.time.LocalDate;

/**
 * Representa un recibo asociado a una póliza de seguro dentro del sistema.
 */
public class Recibo {
    private int idRecibo;
    private String numRecibo;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String totalPagar;
    private int idPoliza;
    private String estadoRecibo;
    private String tipoPago;

    /**
     * Constructor por defecto.
     */
    public Recibo() {
    }

    /**
     * Obtiene el ID del recibo.
     * @return el ID del recibo.
     */
    public int getIdRecibo() {
        return idRecibo;
    }

    /**
     * Establece el ID del recibo.
     * @param idRecibo el ID único del recibo.
     */
    public void setIdRecibo(int idRecibo) {
        this.idRecibo = idRecibo;
    }

    /**
     * Obtiene el número del recibo.
     * @return el número del recibo.
     */
    public String getNumRecibo() {
        return numRecibo;
    }

    /**
     * Establece el número del recibo.
     * @param numRecibo el número del recibo.
     */
    public void setNumRecibo(String numRecibo) {
        this.numRecibo = numRecibo;
    }

    /**
     * Obtiene la fecha de emisión del recibo.
     * @return la fecha de emisión.
     */
    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Establece la fecha de emisión del recibo.
     * @param fechaEmision la fecha de emisión del recibo.
     */
    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Obtiene la fecha de vencimiento del recibo.
     * @return la fecha de vencimiento.
     */
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Establece la fecha de vencimiento del recibo.
     * @param fechaVencimiento la fecha en la que vence el recibo.
     */
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Obtiene el total a pagar del recibo.
     * @return el total a pagar.
     */
    public String getTotalPagar() {
        return totalPagar;
    }

    /**
     * Establece el total a pagar del recibo.
     * @param totalPagar el monto total a pagar.
     */
    public void setTotalPagar(String totalPagar) {
        this.totalPagar = totalPagar;
    }

    /**
     * Obtiene el ID de la póliza asociada al recibo.
     * @return el ID de la póliza.
     */
    public int getIdPoliza() {
        return idPoliza;
    }

    /**
     * Establece el ID de la póliza asociada al recibo.
     * @param idPoliza el ID de la póliza.
     */
    public void setIdPoliza(int idPoliza) {
        this.idPoliza = idPoliza;
    }

    /**
     * Obtiene el estado del recibo.
     * @return el estado del recibo.
     */
    public String getEstadoRecibo() {
        return estadoRecibo;
    }

    /**
     * Establece el estado del recibo.
     * @param estadoRecibo el estado actual del recibo.
     */
    public void setEstadoRecibo(String estadoRecibo) {
        this.estadoRecibo = estadoRecibo;
    }

    /**
     * Obtiene el tipo de pago del recibo.
     * @return el tipo de pago.
     */
    public String getTipoPago() {
        return tipoPago;
    }

    /**
     * Establece el tipo de pago del recibo.
     * @param tipoPago el método de pago utilizado.
     */
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
}
