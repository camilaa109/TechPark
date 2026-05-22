package co.edu.uniquindio.techpark.view;

import co.edu.uniquindio.techpark.controller.AlertaUtil;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Notificacion;
import co.edu.uniquindio.techpark.model.TipoTicket;
import co.edu.uniquindio.techpark.model.Visitante;
import javafx.fxml.FXML;

import java.util.List;

/**
 * ViewController de PanelVisitante.fxml
 *
 * Vista principal del visitante. Incluye:
 *  - Resumen de cuenta (nombre, saldo, tickets).
 *  - Favoritos.
 *  - Panel de Notificaciones embebido (no es un .fxml separado).
 *  - Acceso a ColaVirtualAtraccion.
 */
public class PanelVisitanteViewController {

    private final VisitanteController visitanteController = new VisitanteController();

    // Documento del visitante activo en sesión
    private final String documentoActivo = ParqueController.getDocumentoSesionActiva();

    // =========================================================================
    // Campos de la vista — reemplazar cuando exista PanelVisitante.fxml
    // =========================================================================

    // --- Encabezado / resumen ---
    // TODO (Vista):
    // @FXML private Label  lblNombreBienvenida;
    // @FXML private Label  lblSaldo;

    // --- Sección tickets ---
    // TODO (Vista):
    // @FXML private ListView<String> listViewTickets;
    // @FXML private ComboBox<TipoTicket> comboTipoTicket;
    // @FXML private TextField txtCostoAdicional;
    // @FXML private Button btnComprarTicket;

    // --- Sección favoritos ---
    // TODO (Vista):
    // @FXML private ListView<String>  listViewFavoritos;
    // @FXML private ComboBox<String>  comboAtraccionFavorito;
    // @FXML private Button            btnAgregarFavorito;

    // --- Panel de Notificaciones (embebido en PanelVisitante.fxml) ---
    // TODO (Vista):
    // @FXML private ListView<Notificacion> listViewNotificaciones;
    // @FXML private Button                 btnEliminarNotificacion;

    // --- Navegación ---
    // TODO (Vista):
    // @FXML private Button btnIrAColaVirtual;
    // @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        cargarDatosVisitante();
        cargarNotificaciones();
    }

    // =========================================================================
    // Carga de datos
    // =========================================================================

    /**
     * Carga y muestra los datos del visitante activo: nombre, saldo y tickets.
     */
    private void cargarDatosVisitante() {
        Visitante visitante = visitanteController.obtenerVisitante(documentoActivo);
        if (visitante == null) return;

        // TODO (Vista): poblar campos con los datos del visitante
        // lblNombreBienvenida.setText("Bienvenido, " + visitante.getNombre());
        // lblSaldo.setText("Saldo: $" + visitante.getSaldoVirtual());
        // listViewTickets.setItems(FXCollections.observableArrayList(
        //     visitante.getListaTickets().stream()
        //         .map(t -> t.getTipoTicket().toString())
        //         .toList()));
    }

    /**
     * Carga las notificaciones pendientes del visitante activo.
     * Llamado en initialize() y cada vez que se elimina una notificación.
     */
    private void cargarNotificaciones() {
        List<Notificacion> notificaciones = visitanteController.obtenerNotificaciones(documentoActivo);

        // TODO (Vista): poblar el ListView de notificaciones
        // listViewNotificaciones.setItems(
        //     FXCollections.observableArrayList(notificaciones));
    }

    // =========================================================================
    // Eventos — Tickets
    // =========================================================================

    /**
     * Acción del botón "Comprar Ticket".
     */
    @FXML
    public void onComprarTicket() {

        // TODO (Vista): leer valores del combo y campo de costo
        // TipoTicket tipo          = comboTipoTicket.getValue();
        // String     costoTexto    = txtCostoAdicional.getText().trim();
        TipoTicket tipo       = TipoTicket.GENERAL; // reemplazar
        double     costoAdicional = 0.0;            // reemplazar por Double.parseDouble(costoTexto)

        visitanteController.comprarTicket(documentoActivo, costoAdicional, tipo);

        cargarDatosVisitante(); // refrescar saldo y lista de tickets
        AlertaUtil.exito("Ticket comprado exitosamente.");
    }

    // =========================================================================
    // Eventos — Favoritos
    // =========================================================================

    /**
     * Acción del botón "Agregar Favorito".
     */
    @FXML
    public void onAgregarFavorito() {

        // TODO (Vista): leer atracción seleccionada del combo
        // String nombreAtraccion = comboAtraccionFavorito.getValue();
        String nombreAtraccion = ""; // reemplazar

        if (nombreAtraccion.isEmpty()) {
            AlertaUtil.advertencia("Selecciona una atracción para agregar a favoritos.");
            return;
        }

        visitanteController.agregarFavorito(documentoActivo, nombreAtraccion);

        // TODO (Vista): agregar el ítem al ListView sin recargar todo
        // listViewFavoritos.getItems().add(nombreAtraccion);
        AlertaUtil.exito("Atracción agregada a favoritos.");
    }

    // =========================================================================
    // Eventos — Panel de Notificaciones (embebido)
    // =========================================================================

    /**
     * Acción del botón "Eliminar Notificación" dentro del panel embebido.
     */
    @FXML
    public void onEliminarNotificacion() {

        // TODO (Vista): obtener la notificación seleccionada en el ListView
        // Notificacion seleccionada = listViewNotificaciones.getSelectionModel()
        //                                                   .getSelectedItem();
        // if (seleccionada == null) {
        //     AlertaUtil.advertencia("Selecciona una notificación para eliminar.");
        //     return;
        // }
        // String idNotificacion = seleccionada.id();
        String idNotificacion = ""; // reemplazar

        visitanteController.eliminarNotificacion(documentoActivo, idNotificacion);
        cargarNotificaciones(); // refrescar el panel
    }

    // =========================================================================
    // Eventos — Navegación
    // =========================================================================

    /**
     * Acción del botón "Ver Cola Virtual / Atracciones".
     */
    @FXML
    public void onIrAColaVirtual() {
        // TODO (Vista): navegar a ColaVirtualAtraccion.fxml
        // NavegadorUtil.irA((Stage) btnIrAColaVirtual.getScene().getWindow(),
        //     "/fxml/ColaVirtualAtraccion.fxml");
    }

    /**
     * Acción del botón "Cerrar Sesión".
     */
    @FXML
    public void onCerrarSesion() {
        new ParqueController().cerrarSesion();
        // TODO (Vista): redirigir a InicioSesion.fxml
        // NavegadorUtil.irA((Stage) btnCerrarSesion.getScene().getWindow(),
        //     "/fxml/InicioSesion.fxml");
    }
}
