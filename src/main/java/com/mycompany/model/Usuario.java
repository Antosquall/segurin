package com.mycompany.model;

/**
 * Clase que representa un usuario en el sistema.
 * Esta clase se mapea a una tabla en la base de datos utilizando JPA.
 */

public class Usuario {

    /**
     * Identificador único del usuario.
     * Se genera automáticamente por la base de datos.
     */
    private int id;

    /**
     * Nombre de usuario para acceder al sistema.
     */
    private String nombreUsuario;

    /**
     * Contraseña del usuario para autenticarse en el sistema.
     */
    private String password;

    /**
     * Rol del usuario que define sus permisos en el sistema.
     */
    private String rol;

    /**
     * Constructor sin parámetros.
     * Requerido por JPA para las operaciones de la base de datos.
     */
    public Usuario() {
    }

    /**
     * Constructor que inicializa un usuario con id, nombre de usuario, contraseña y rol.
     *
     * @param id El identificador del usuario.
     * @param nombreUsuario El nombre de usuario.
     * @param password La contraseña del usuario.
     * @param rol El rol del usuario.
     */
    public Usuario(int id, String nombreUsuario, String password, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Constructor que inicializa un usuario con nombre de usuario y rol.
     * Útil para creaciones rápidas donde la contraseña puede no ser inicialmente requerida.
     *
     * @param nombreUsuario El nombre de usuario.
     * @param rol El rol del usuario.
     */
    public Usuario(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    /**
     * Obtiene el ID del usuario.
     *
     * @return El identificador del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param id El nuevo identificador del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return El nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param nombreUsuario El nuevo nombre de usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La nueva contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol El nuevo rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}
