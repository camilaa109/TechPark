package co.edu.uniquindio.techpark.controller;

import co.edu.uniquindio.techpark.model.Parque;
import co.edu.uniquindio.techpark.model.Rol;

/**
 * Controlador principal del parque.
 * Gestiona la instancia Singleton del parque y el inicio de sesión.
 * Comparte la instancia entre todos los demás controladores.
 */
public class ParqueController {

    // Instancia singleton del parque (compartida por todos los controladores)
    private static final Parque parque = new Parque("TechPark", 1000);

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
        Rol rol = parque.inicioSesion(documento, contrasenia);

        if (rol != null) {
            documentoSesionActiva = documento;
            rolSesionActiva = rol;
        }

        // TODO (Vista): navegar a la pantalla correspondiente según el rol
        //   if (rol == Rol.VISITANTE)  -> cargar VistaVisitante.fxml
        //   if (rol == Rol.OPERADOR)   -> cargar VistaOperador.fxml
        //   if (rol == Rol.ADMIN)      -> cargar VistaAdmin.fxml
        //   Mostrar alerta si rol == null

        return rol;
    }

    /** Cierra la sesión activa. */
    public void cerrarSesion() {
        documentoSesionActiva = null;
        rolSesionActiva = null;

        // TODO (Vista): redirigir a la pantalla de inicio de sesión
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
