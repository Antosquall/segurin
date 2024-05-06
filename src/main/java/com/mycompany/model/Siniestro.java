package com.mycompany.model;

import java.time.LocalDate;

/**
 * Clase que representa un siniestro en el sistema.
 * 
 * Un siniestro es un suceso que causa daños y que puede estar cubierto por una póliza de seguro.
 * Esta clase se utiliza para almacenar y gestionar la información relacionada con los siniestros.
 */
public class Siniestro {

    /**
     * Identificador único del siniestro.
     */
    private int idSiniestro;

    /**
     * Obtiene el identificador único del siniestro.
     * @return el identificador del siniestro.
     */
    public int getIdSiniestro() {
        return idSiniestro;
    }

    /**
     * Establece el identificador único del siniestro.
     * @param idSiniestro el identificador del siniestro.
     */
    public void setIdSiniestro(int idSiniestro) {
        this.idSiniestro = idSiniestro;
    }

    /**
     * Número identificativo del siniestro.
     */
    private String numSiniestro;

    /**
     * Obtiene el número identificativo del siniestro.
     * @return el número identificativo del siniestro.
     */
    public String getNumSiniestro() {
        return numSiniestro;
    }

    /**
     * Establece el número identificativo del siniestro.
     * @param numSiniestro el número identificativo del siniestro.
     */
    public void setNumSiniestro(String numSiniestro) {
        this.numSiniestro = numSiniestro;
    }

    /**
     * Fecha en la que ocurrió el siniestro.
     */
    private LocalDate fechaSiniestro;

    /**
     * Obtiene la fecha en la que ocurrió el siniestro.
     * @return la fecha del siniestro.
     */
    public LocalDate getFechaSiniestro() {
        return fechaSiniestro;
    }

    /**
     * Establece la fecha en la que ocurrió el siniestro.
     * @param fechaSiniestro la fecha del siniestro.
     */
    public void setFechaSiniestro(LocalDate fechaSiniestro) {
        this.fechaSiniestro = fechaSiniestro;
    }

    /**
     * Descripción del siniestro, incluyendo los detalles del suceso ocurrido.
     */
    private String descripcion;

    /**
     * Obtiene la descripción del siniestro.
     * @return la descripción del siniestro.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del siniestro.
     * @param descripcion la descripción del siniestro.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Identificador de la póliza de seguro asociada al siniestro.
     */
    private int idPoliza;

    /**
     * Obtiene el identificador de la póliza de seguro asociada al siniestro.
     * @return el identificador de la póliza.
     */
    public int getIdPoliza() {
        return idPoliza;
    }

    /**
     * Establece el identificador de la póliza de seguro asociada al siniestro.
     * @param idPoliza el identificador de la póliza.
     */
    public void setIdPoliza(int idPoliza) {
        this.idPoliza = idPoliza;
    }

    /**
     * Lugar donde ocurrió el siniestro.
     */
    private String lugarSiniestro;

    /**
     * Obtiene el lugar donde ocurrió el siniestro.
     * @return el lugar del siniestro.
     */
    public String getLugarSiniestro() {
        return lugarSiniestro;
    }

    /**
     * Establece el lugar donde ocurrió el siniestro.
     * @param lugarSiniestro el lugar del siniestro.
     */
    public void setLugarSiniestro(String lugarSiniestro) {
        this.lugarSiniestro = lugarSiniestro;
    }

    /**
     * Tipo de siniestro (por ejemplo, incendio, robo, etc.).
     */
    private String tipoSiniestro;

    /**
     * Obtiene el tipo de siniestro.
     * @return el tipo de siniestro.
     */
    public String getTipoSiniestro() {
        return tipoSiniestro;
    }

    /**
     * Establece el tipo de siniestro.
     * @param tipoSiniestro el tipo de siniestro.
     */
    public void setTipoSiniestro(String tipoSiniestro) {
        this.tipoSiniestro = tipoSiniestro;
    }

    /**
     * Importe total reclamado por el siniestro a la compañía de seguros.
     */
    private String totalReclamado;

    /**
     * Obtiene el importe total reclamado por el siniestro.
     * @return el importe total reclamado.
     */
    public String getTotalReclamado() {
        return totalReclamado;
    }

    /**
     * Establece el importe total reclamado por el siniestro.
     * @param totalReclamado el importe total reclamado.
     */
    public void setTotalReclamado(String totalReclamado) {
        this.totalReclamado = totalReclamado;
    }

    /**
     * Estado actual del siniestro (por ejemplo, abierto, cerrado, pendiente de resolución).
     */
    private String estadoSiniestro;

    /**
     * Obtiene el estado actual del siniestro.
     * @return el estado del siniestro.
     */
    public String getEstadoSiniestro() {
        return estadoSiniestro;
    }

    /**
     * Establece el estado actual del siniestro.
     * @param estadoSiniestro el estado del siniestro.
     */
    public void setEstadoSiniestro(String estadoSiniestro) {
        this.estadoSiniestro = estadoSiniestro;
    }

    /**
     * Fecha de resolución del siniestro, si ya se ha resuelto.
     */
    private LocalDate fechaResolucion;

    /**
     * Obtiene la fecha de resolución del siniestro.
     * @return la fecha de resolución del siniestro.
     */
    public LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    /**
     * Establece la fecha de resolución del siniestro.
     * @param fechaResolucion la fecha de resolución del siniestro.
     */
    public void setFechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    @Override
    public String toString() {
        return "Siniestro{" +
                "idSiniestro=" + idSiniestro +
                ", numSiniestro='" + numSiniestro + '\'' +
                ", fechaSiniestro=" + fechaSiniestro +
                ", descripcion='" + descripcion + '\'' +
                ", idPoliza='" + idPoliza + '\'' +
                ", lugarSiniestro='" + lugarSiniestro + '\'' +
                ", tipoSiniestro='" + tipoSiniestro + '\'' +
                ", totalReclamado='" + totalReclamado + '\'' +
                ", estadoSiniestro='" + estadoSiniestro + '\'' +
                ", fechaResolucion='" + fechaResolucion + '\'' +
                '}';
    }
}
