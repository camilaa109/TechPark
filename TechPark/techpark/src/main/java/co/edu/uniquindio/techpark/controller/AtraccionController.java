package co.edu.uniquindio.techpark.controller;

import java.util.List;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.EstadoAtraccion;
import co.edu.uniquindio.techpark.model.Parque;
import co.edu.uniquindio.techpark.model.TipoAtraccion;
import co.edu.uniquindio.techpark.model.Zona;

/**
 * Controlador de Zonas y Atracciones (Lógica de Negocio).
 */
public class AtraccionController {

    private final Parque parque;

    public AtraccionController() {
        // Obtenemos la instancia única del parque
        this.parque = ParqueController.getParque();
    }

    /**
     * Agrega una nueva zona al parque.
     */
    public void agregarZona(String nombreZona) {
        parque.agregarZona(nombreZona);
    }

    /**
     * Devuelve la lista de zonas registradas.
     */
    public List<Zona> obtenerZonas() {
        return parque.getListaZonas();
    }

    /**
     * Agrega una nueva atracción a la zona indicada.
     */
    public void agregarAtraccion(String nombreAtraccion, int capacidadMaxima,
                                 int edadMinima, double alturaMinima,
                                 double costoAdicional, int tiempoEspera,
                                 TipoAtraccion tipoAtraccion, String nombreZona) {

        parque.agregarAtraccion(nombreAtraccion, capacidadMaxima, edadMinima,
                alturaMinima, costoAdicional, tiempoEspera, tipoAtraccion, nombreZona);
    }

    /**
     * Obtiene una atracción específica por zona y nombre.
     */
    public Atraccion obtenerAtraccion(String nombreZona, String nombreAtraccion) {
        return parque.obtenerAtraccion(nombreZona, nombreAtraccion);
    }

    /**
     * Cambia manualmente el estado de una atracción (con motivo de cierre).
     */
    public void cambiarEstadoAtraccion(String nombreZona, String nombreAtraccion,
                                       EstadoAtraccion estadoAtraccion, String motivoCierre) {
        parque.cambiarEstadoAtraccion(nombreZona, nombreAtraccion, estadoAtraccion, motivoCierre);
    }

    /**
     * Cambia el estado de una atracción sin motivo de cierre explícito.
     */
    public void cambiarEstadoAtraccion(String nombreZona, String nombreAtraccion,
                                       EstadoAtraccion estadoAtraccion) {
        parque.cambiarEstadoAtraccion(nombreZona, nombreAtraccion, estadoAtraccion);
    }

    /**
     * Ejecuta un ciclo completo de la atracción.
     */
    public void realizarCicloAtraccion(String nombreZona, String nombreAtraccion) {
        parque.realizarCicloAtraccion(nombreZona, nombreAtraccion);
    }

    /**
     * Verifica todas las atracciones y aplica cierre automático por mantenimiento.
     */
    public void verificarMantenimiento() {
        parque.verificarMantenimiento();
    }

    /**
     * Registra la revisión de mantenimiento completada y vuelve la atracción al estado ACTIVA.
     */
    public void registrarRevision(String nombreZona, String nombreAtraccion) {
        parque.registrarRevision(nombreZona, nombreAtraccion);
    }

    /**
     * Activa una alerta climática afectando a las atracciones.
     */
    public void activarAlertaClimatica(String motivo) {
        parque.activarAlertaClimatica(motivo);
    }
}