package co.edu.uniquindio.techpark.controller;

import java.util.List;
import co.edu.uniquindio.techpark.model.Notificacion;
import co.edu.uniquindio.techpark.model.Parque;
import co.edu.uniquindio.techpark.model.Ticket;
import co.edu.uniquindio.techpark.model.TipoTicket;
import co.edu.uniquindio.techpark.model.Visitante;

/**
 * Controlador de Visitantes (Lógica de Negocio).
 *
 * Cubre:
 * - Registro, obtención, actualización y eliminación de visitantes.
 * - Compra de tickets.
 * - Favoritos.
 * - Notificaciones.
 * - Consulta de tiempo de espera.
 * - Acceso a atracciones.
 */
public class VisitanteController {

    private final Parque parque;

    public VisitanteController() {
        this.parque = ParqueController.getParque();
    }

    // =========================================================================
    // CRUD de Visitantes
    // =========================================================================

    /**
     * Registra un nuevo visitante en el parque.
     *
     * @return true si el registro fue exitoso, false si ya existe el documento.
     */
    public boolean registrarVisitante(String nombre, String documento,
                                      int edad, String contrasenia, double estatura) {
        return parque.agregarVisitante(nombre, documento, edad, contrasenia, estatura);
    }

    /**
     * Devuelve la lista completa de visitantes registrados.
     */
    public List<Visitante> obtenerListaVisitantes() {
        return parque.getListaVisitantes();
    }

    /**
     * Obtiene un visitante por su documento.
     *
     * @return el Visitante encontrado, o null si no existe.
     */
    public Visitante obtenerVisitante(String documento) {
        return parque.obtenerVisitante(documento);
    }

    /**
     * Actualiza los datos editables de un visitante.
     * El documento es inmutable (identificador único).
     */
    public void actualizarVisitante(String nuevoNombre, String documento,
                                    int edad, double estatura) {
        parque.actualizarVisitante(nuevoNombre, documento, edad, estatura);
    }

    /**
     * Elimina un visitante del sistema por su documento.
     */
    public void eliminarVisitante(String documento) {
        parque.eliminarVisitante(documento);
    }

    // =========================================================================
    // Tickets
    // =========================================================================

    /**
     * Compra un ticket para el visitante indicado.
     */
    public boolean comprarTicket(String documento, TipoTicket tipoTicket) {
        return parque.comprarTicket(documento, tipoTicket);
    }

    public List<Ticket> obtenerTickets(String documento){
        return parque.obtenerTickets(documento);
    }

    // =========================================================================
    // Favoritos
    // =========================================================================

    /**
     * Agrega una atracción a la lista de favoritos del visitante.
     */
    public void agregarFavorito(String documento, String nombreAtraccion) {
        parque.agregarFavorito(documento, nombreAtraccion);
    }

    public List<String> obtenerFavoritos(String documento){
        return parque.obtenerFavoritos(documento);
    }

    // =========================================================================
    // Notificaciones
    // =========================================================================

    /**
     * Devuelve las notificaciones pendientes del visitante.
     */
    public List<Notificacion> obtenerNotificaciones(String documento) {
        return parque.obtenerNotificaciones(documento);
    }

    /**
     * Elimina (marca como leída/descartada) una notificación específica.
     */
    public void eliminarNotificacion(String documento, String idNotificacion) {
        parque.eliminarNotificacion(documento, idNotificacion);
    }

    // =========================================================================
    // Tiempo de espera y acceso
    // =========================================================================

    /**
     * Consulta el tiempo de espera estimado para una atracción.
     *
     * @return minutos estimados de espera.
     */
    public int consultarTiempoEspera(String nombreZona, String nombreAtraccion) {
        return parque.consultarTiempoEspera(nombreZona, nombreAtraccion);
    }

    /**
     * Valida el acceso de un visitante a una atracción y lo encola en la cola virtual.
     *
     * @return true si el acceso es válido.
     */
    public boolean accederAtraccion(String documento, String nombreZona, String nombreAtraccion) {
        return parque.accesoAtraccion(documento, nombreZona, nombreAtraccion);
    }
}