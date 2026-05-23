package co.edu.uniquindio.techpark.controller;

import co.edu.uniquindio.techpark.model.Administrador;
import co.edu.uniquindio.techpark.model.Parque;
import co.edu.uniquindio.techpark.model.Rol;

/**
 * Controlador principal del parque (Lógica de Negocio).
 * Gestiona la instancia Singleton del parque y el estado de la sesión activa.
 */
public class ParqueController {

    // Instancia singleton del parque (compartida por todos los controladores)
    private static final Administrador administrador = new Administrador("Camila", "5678", 19, "techpark2026");
    private static final Parque parque = administrador.crearParque("TechPark", 10000);

    // Sesión activa
    private static String documentoSesionActiva;
    private static Rol rolSesionActiva;

    // -------------------------------------------------------------------------
    // Acceso a la instancia del parque
    // -------------------------------------------------------------------------

    public static Parque getParque() {
        return parque;
    }

    // -------------------------------------------------------------------------
    // Inicio de sesión
    // -------------------------------------------------------------------------

    /**
     * Intenta iniciar sesión con documento y contraseña.
     *
     * @return el Rol correspondiente, o null si las credenciales son inválidas.
     */
    public Rol iniciarSesion(String documento, String contrasenia) {

        if (documento.equals(administrador.getDocumento()) && contrasenia.equals(administrador.getContrasenia())){
            return Rol.ADMINISTRADOR;
        }

        Rol rol = parque.inicioSesion(documento, contrasenia);

        if (rol != null) {
            documentoSesionActiva = documento;
            rolSesionActiva = rol;
        }

        return rol;
    }

    /** * Cierra la sesión activa. 
     */
    public void cerrarSesion() {
        documentoSesionActiva = null;
        rolSesionActiva = null;
    }

    // -------------------------------------------------------------------------
    // Getters de sesión
    // -------------------------------------------------------------------------

    public static String getDocumentoSesionActiva() {
        return documentoSesionActiva;
    }

    public static Rol getRolSesionActiva() {
        return rolSesionActiva;
    }
}