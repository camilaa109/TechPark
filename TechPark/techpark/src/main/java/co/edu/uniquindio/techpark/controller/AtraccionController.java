package co.edu.uniquindio.techpark.controller;

import java.util.List;

import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.EstadoAtraccion;
import co.edu.uniquindio.techpark.model.Parque;
import co.edu.uniquindio.techpark.model.TipoAtraccion;
import co.edu.uniquindio.techpark.model.Zona;

/**
 * Controlador de Zonas y Atracciones.
 *
 * Cubre:
 *  - Alta de zonas y atracciones.
 *  - Obtención de atracciones individuales.
 *  - Cambio de estado manual de atracciones.
 *  - Realización de ciclos de atracción.
 *  - Verificación automática de mantenimiento.
 *  - Registro de revisión (vuelta a ACTIVA tras mantenimiento).
 *  - Activación de alerta climática.
 */
public class AtraccionController {

    private final Parque parque = ParqueController.getParque();

    // =========================================================================
    // Zonas
    // =========================================================================

    /**
     * Agrega una nueva zona al parque.
     *
     * @param nombreZona nombre único de la zona.
     */
    public void agregarZona(String nombreZona) {
        parque.agregarZona(nombreZona);

        // TODO (Vista): agregar la zona al ComboBox/ListView de zonas
        //   comboZonas.getItems().add(nombreZona);
        //   AlertaUtil.exito("Zona \"" + nombreZona + "\" creada");
    }

    /**
     * Devuelve la lista de zonas registradas.
     */
    public List<Zona> obtenerZonas() {
        List<Zona> zonas = parque.getListaZonas();

        // TODO (Vista): poblar el ComboBox de zonas disponibles
        //   comboZonas.setItems(FXCollections.observableArrayList(
        //       zonas.stream().map(Zona::getNombreZona).toList()));

        return zonas;
    }

    // =========================================================================
    // Atracciones — alta y consulta
    // =========================================================================

    /**
     * Agrega una nueva atracción a la zona indicada.
     *
     * @param nombreAtraccion  nombre de la atracción.
     * @param capacidadMaxima  cupos por ciclo.
     * @param edadMinima       edad mínima del visitante.
     * @param alturaMinima     altura mínima del visitante (metros).
     * @param costoAdicional   costo extra sobre el ticket base (0 si no aplica).
     * @param tiempoEspera     minutos de duración del ciclo.
     * @param tipoAtraccion    tipo de atracción (MECANICA_ALTURA, etc.).
     * @param nombreZona       zona a la que pertenece.
     */
    public void agregarAtraccion(String nombreAtraccion, int capacidadMaxima,
                                 int edadMinima, double alturaMinima,
                                 double costoAdicional, int tiempoEspera,
                                 TipoAtraccion tipoAtraccion, String nombreZona) {

        parque.agregarAtraccion(nombreAtraccion, capacidadMaxima, edadMinima,
                alturaMinima, costoAdicional, tiempoEspera, tipoAtraccion, nombreZona);

        // TODO (Vista): agregar la atracción al TableView o lista de la zona
        //   refrescarTablaAtracciones(nombreZona);
        //   AlertaUtil.exito("Atracción \"" + nombreAtraccion + "\" agregada");
    }

    /**
     * Obtiene una atracción específica por zona y nombre.
     *
     * @return la Atraccion encontrada, o null si no existe.
     */
    public Atraccion obtenerAtraccion(String nombreZona, String nombreAtraccion) {
        Atraccion atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);

        // TODO (Vista): mostrar los detalles en el panel de detalle de atracción
        //   lblEstado.setText(atraccion.getEstadoAtraccion().toString());
        //   lblCapacidad.setText(String.valueOf(atraccion.getCapacidadMaxima())); etc.

        return atraccion;
    }

    // =========================================================================
    // Estado de atracciones
    // =========================================================================

    /**
     * Cambia manualmente el estado de una atracción (con motivo de cierre).
     *
     * @param estadoAtraccion  nuevo estado a asignar.
     * @param motivoCierre     razón del cierre (puede ser vacío si se activa).
     */
    public void cambiarEstadoAtraccion(String nombreZona, String nombreAtraccion,
                                       EstadoAtraccion estadoAtraccion, String motivoCierre) {

        parque.cambiarEstadoAtraccion(nombreZona, nombreAtraccion, estadoAtraccion, motivoCierre);

        // TODO (Vista): actualizar el indicador de estado en la tabla/detalle
        //   lblEstado.setText(estadoAtraccion.toString());
        //   refrescarTablaAtracciones(nombreZona);
    }

    /**
     * Cambia el estado de una atracción sin motivo de cierre explícito
     * (uso rápido para activar desde la UI de operador).
     */
    public void cambiarEstadoAtraccion(String nombreZona, String nombreAtraccion,
                                       EstadoAtraccion estadoAtraccion) {

        parque.cambiarEstadoAtraccion(nombreZona, nombreAtraccion, estadoAtraccion);

        // TODO (Vista): actualizar el indicador de estado
        //   lblEstado.setText(estadoAtraccion.toString());
    }

    // =========================================================================
    // Ciclo de atracción y mantenimiento
    // =========================================================================

    /**
     * Ejecuta un ciclo completo de la atracción:
     * notifica a los visitantes en cola y libera los cupos.
     */
    public void realizarCicloAtraccion(String nombreZona, String nombreAtraccion) {
        parque.realizarCicloAtraccion(nombreZona, nombreAtraccion);

        // TODO (Vista): refrescar el indicador del tamaño de la cola virtual
        //   lblColaTamaño.setText(String.valueOf(
        //       parque.obtenerAtraccion(nombreZona, nombreAtraccion)
        //             .getColaVirtual().size()));
    }

    /**
     * Verifica todas las atracciones y aplica cierre automático por mantenimiento
     * cuando el contador de visitantes acumulados supera el umbral definido.
     */
    public void verificarMantenimiento() {
        parque.verificarMantenimiento();

        // TODO (Vista): refrescar toda la tabla de atracciones para mostrar
        //   las que pasaron a EN_MANTENIMIENTO
        //   refrescarTablaAtracciones();
    }

    /**
     * Registra la revisión de mantenimiento completada y vuelve la atracción
     * al estado ACTIVA.
     *
     * @param nombreZona      zona de la atracción revisada.
     * @param nombreAtraccion nombre de la atracción que fue revisada.
     */
    public void registrarRevision(String nombreZona, String nombreAtraccion) {
        parque.registrarRevision(nombreZona, nombreAtraccion);

        // TODO (Vista): actualizar el estado en pantalla a ACTIVA
        //   lblEstado.setText(EstadoAtraccion.ACTIVA.toString());
        //   AlertaUtil.exito("Revisión registrada. Atracción activa nuevamente.");
    }

    // =========================================================================
    // Alertas
    // =========================================================================

    /**
     * Activa una alerta climática: cierra todas las atracciones activas y
     * notifica a todos los visitantes con tickets activos.
     *
     * @param motivo descripción del motivo climático (ej: "Tormenta").
     */
    public void activarAlertaClimatica(String motivo) {
        parque.activarAlertaClimatica(motivo);

        // TODO (Vista): mostrar un banner/alerta global en la interfaz de administración
        //   AlertaUtil.advertencia("Alerta climática activada: " + motivo);
        //   refrescarTablaAtracciones();
        //   refrescarPanelNotificaciones();
    }
}