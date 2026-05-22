package co.edu.uniquindio.techpark.controller;

import java.util.List;

import co.edu.uniquindio.techpark.model.Notificacion;
import co.edu.uniquindio.techpark.model.Parque;
import co.edu.uniquindio.techpark.model.TipoTicket;
import co.edu.uniquindio.techpark.model.Visitante;

/**
 * Controlador de Visitantes.
 *
 * Cubre:
 *  - Registro, obtención, actualización y eliminación de visitantes.
 *  - Compra de tickets.
 *  - Favoritos.
 *  - Notificaciones.
 *  - Consulta de tiempo de espera.
 *  - Acceso a atracciones.
 */
public class VisitanteController {

    private final Parque parque = ParqueController.getParque();

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

        boolean resultado = parque.agregarVisitante(nombre, documento, edad, contrasenia, estatura);

        // TODO (Vista): mostrar alerta de éxito o error según 'resultado'
        //   Si true  -> AlertaUtil.exito("Visitante registrado correctamente")
        //   Si false -> AlertaUtil.error("El documento ya está registrado")

        return resultado;
    }

    /**
     * Devuelve la lista completa de visitantes registrados.
     */
    public List<Visitante> obtenerListaVisitantes() {
        List<Visitante> visitantes = parque.getListaVisitantes();

        // TODO (Vista): poblar un TableView<Visitante> con esta lista
        //   tableVisitantes.setItems(FXCollections.observableArrayList(visitantes));

        return visitantes;
    }

    /**
     * Obtiene un visitante por su documento.
     *
     * @return el Visitante encontrado, o null si no existe.
     */
    public Visitante obtenerVisitante(String documento) {
        Visitante visitante = parque.obtenerVisitante(documento);

        // TODO (Vista): rellenar los campos del formulario de detalle
        //   txtNombre.setText(visitante.getNombre()); etc.

        return visitante;
    }

    /**
     * Actualiza los datos editables de un visitante.
     * El documento es inmutable (identificador único).
     */
    public void actualizarVisitante(String nuevoNombre, String documento,
                                    int edad, double estatura) {

        parque.actualizarVisitante(nuevoNombre, documento, edad, estatura);

        // TODO (Vista): mostrar confirmación y refrescar la tabla/detalle
        //   AlertaUtil.exito("Visitante actualizado");
        //   refrescarTablaVisitantes();
    }

    /**
     * Elimina un visitante del sistema por su documento.
     */
    public void eliminarVisitante(String documento) {
        parque.eliminarVisitante(documento);

        // TODO (Vista): quitar la fila del TableView y mostrar confirmación
        //   tableVisitantes.getItems().removeIf(v -> v.getDocumento().equals(documento));
        //   AlertaUtil.exito("Visitante eliminado");
    }

    // =========================================================================
    // Tickets
    // =========================================================================

    /**
     * Compra un ticket para el visitante indicado.
     *
     * @param documento      documento del visitante comprador.
     * @param costoAdicional costo extra del ticket (puede ser 0.0).
     * @param tipoTicket     tipo de ticket a adquirir.
     */
    public void comprarTicket(String documento, double costoAdicional, TipoTicket tipoTicket) {
        parque.comprarTicket(documento, costoAdicional, tipoTicket);

        // TODO (Vista): actualizar el saldo mostrado al visitante y la lista de tickets
        //   lblSaldo.setText(String.valueOf(visitante.getSaldoVirtual()));
        //   listaTickets.setItems(FXCollections.observableArrayList(visitante.getListaTickets()));
    }

    // =========================================================================
    // Favoritos
    // =========================================================================

    /**
     * Agrega una atracción a la lista de favoritos del visitante.
     *
     * @param documento      documento del visitante.
     * @param nombreAtraccion nombre de la atracción a marcar como favorita.
     */
    public void agregarFavorito(String documento, String nombreAtraccion) {
        parque.agregarFavorito(documento, nombreAtraccion);

        // TODO (Vista): actualizar la lista de favoritos en la vista del visitante
        //   listaFavoritos.setItems(FXCollections.observableArrayList(
        //       visitante.getListaNombreFavoritos()));
    }

    // =========================================================================
    // Notificaciones
    // =========================================================================

    /**
     * Devuelve las notificaciones pendientes del visitante.
     */
    public List<Notificacion> obtenerNotificaciones(String documento) {
        List<Notificacion> notificaciones = parque.obtenerNotificaciones(documento);

        // TODO (Vista): mostrar en un ListView o panel de notificaciones
        //   listViewNotificaciones.setItems(
        //       FXCollections.observableArrayList(notificaciones));

        return notificaciones;
    }

    /**
     * Elimina (marca como leída/descartada) una notificación específica.
     *
     * @param documento      documento del visitante dueño de la notificación.
     * @param idNotificacion identificador único de la notificación.
     */
    public void eliminarNotificacion(String documento, String idNotificacion) {
        parque.eliminarNotificacion(documento, idNotificacion);

        // TODO (Vista): quitar el ítem de la lista de notificaciones en pantalla
        //   listViewNotificaciones.getItems()
        //       .removeIf(n -> n.id().equals(idNotificacion));
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
        int tiempo = parque.consultarTiempoEspera(nombreZona, nombreAtraccion);

        // TODO (Vista): mostrar el tiempo en una etiqueta
        //   lblTiempoEspera.setText(tiempo + " min");

        return tiempo;
    }

    /**
     * Valida el acceso de un visitante a una atracción y lo encola en la cola virtual.
     *
     * @return true si el acceso es válido (visitante cumple requisitos y la atracción está activa).
     */
    public boolean accederAtraccion(String documento, String nombreZona, String nombreAtraccion) {
        boolean esValido = parque.accesoAtraccion(documento, nombreZona, nombreAtraccion);

        // TODO (Vista): mostrar mensaje de acceso permitido / denegado
        //   if (esValido)
        //       AlertaUtil.exito("Acceso concedido. Estás en la cola virtual.");
        //   else
        //       AlertaUtil.error("Acceso denegado. Verifica los requisitos o tu ticket.");

        return esValido;
    }
}