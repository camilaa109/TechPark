package co.edu.uniquindio.techpark.view;

import co.edu.uniquindio.techpark.controller.AlertaUtil;
import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.OperadorController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.EstadoAtraccion;
import co.edu.uniquindio.techpark.model.Operador;
import javafx.fxml.FXML;

/**
 * ViewController de PanelOperador.fxml
 *
 * Vista principal del operador. Incluye:
 *  - Datos del operador y atracción asignada.
 *  - Panel de Gestión de Atracción embebido (no es un .fxml separado):
 *      · Cambiar estado (ACTIVA / CERRADA).
 *      · Realizar ciclo de atracción.
 *      · Registrar revisión de mantenimiento.
 */
public class PanelOperadorViewController {

    private final OperadorController  operadorController  = new OperadorController();
    private final AtraccionController atraccionController = new AtraccionController();

    private final String documentoActivo = ParqueController.getDocumentoSesionActiva();

    // Datos del operador y su atracción asignada, cargados en initialize()
    private Operador  operadorActivo;
    private String    nombreZonaAsignada;     // necesario para llamadas al AtraccionController
    private Atraccion atraccionAsignada;

    // =========================================================================
    // Campos de la vista — reemplazar cuando exista PanelOperador.fxml
    // =========================================================================

    // --- Encabezado ---
    // TODO (Vista):
    // @FXML private Label lblNombreOperador;
    // @FXML private Label lblAtraccionAsignada;

    // --- Panel embebido: Gestión de Atracción ---

    // Estado
    // TODO (Vista):
    // @FXML private Label            lblEstadoAtraccion;
    // @FXML private TextField        txtMotivoEstado;
    // @FXML private Button           btnActivar;
    // @FXML private Button           btnCerrar;

    // Cola virtual
    // TODO (Vista):
    // @FXML private Label  lblTamanoCola;
    // @FXML private Button btnRealizarCiclo;

    // Mantenimiento
    // TODO (Vista):
    // @FXML private Label  lblVisitantesAcumulados;
    // @FXML private Button btnRegistrarRevision;

    // Navegación
    // TODO (Vista):
    // @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        cargarDatosOperador();
        cargarDatosAtraccion();
    }

    // =========================================================================
    // Carga de datos
    // =========================================================================

    /**
     * Carga los datos del operador activo y los muestra en el encabezado.
     */
    private void cargarDatosOperador() {
        operadorActivo = operadorController.obtenerOperador(documentoActivo);
        if (operadorActivo == null) return;

        // TODO (Vista): mostrar nombre y atracción asignada en las etiquetas
        // lblNombreOperador.setText("Operador: " + operadorActivo.getNombre());
        // lblAtraccionAsignada.setText("Atracción: " + operadorActivo.getNombreAtraccionAsignada());
    }

    /**
     * Carga los datos de la atracción asignada al operador
     * y refresca los indicadores del panel embebido.
     *
     * Requiere que nombreZonaAsignada esté seteado previamente.
     * Si el modelo no expone la zona directamente, deberá buscarse
     * recorriendo las zonas del parque.
     */
    private void cargarDatosAtraccion() {
        if (operadorActivo == null) return;

        String nombreAtraccion = operadorActivo.getNombreAtraccionAsignada();

        // TODO (Modelo): si Operador no guarda la zona, buscarla recorriendo parque.getListaZonas()
        // nombreZonaAsignada = ParqueController.getParque().getListaZonas().stream()
        //     .filter(z -> z.getListaAtracciones().stream()
        //         .anyMatch(a -> a.getNombreAtraccion().equals(nombreAtraccion)))
        //     .map(Zona::getNombreZona)
        //     .findFirst().orElse("");

        if (nombreZonaAsignada == null || nombreZonaAsignada.isEmpty()) return;

        atraccionAsignada = atraccionController.obtenerAtraccion(nombreZonaAsignada, nombreAtraccion);
        if (atraccionAsignada == null) return;

        // TODO (Vista): actualizar los indicadores del panel embebido
        // lblEstadoAtraccion.setText("Estado: " + atraccionAsignada.getEstadoAtraccion());
        // lblTamanoCola.setText("En cola: " + atraccionAsignada.getColaVirtual().size());
        // lblVisitantesAcumulados.setText("Acumulados: " + atraccionAsignada.getVisitantesAcumulados());
    }

    // =========================================================================
    // Eventos — Panel embebido: Estado de atracción
    // =========================================================================

    /**
     * Acción del botón "Activar Atracción".
     */
    @FXML
    public void onActivarAtraccion() {
        if (atraccionAsignada == null) return;

        atraccionController.cambiarEstadoAtraccion(
                nombreZonaAsignada,
                operadorActivo.getNombreAtraccionAsignada(),
                EstadoAtraccion.ACTIVA);

        cargarDatosAtraccion();
        AlertaUtil.exito("Atracción activada.");
    }

    /**
     * Acción del botón "Cerrar Atracción".
     */
    @FXML
    public void onCerrarAtraccion() {
        if (atraccionAsignada == null) return;

        // TODO (Vista): leer el motivo del TextField
        // String motivo = txtMotivoEstado.getText().trim();
        String motivo = ""; // reemplazar

        if (motivo.isEmpty()) {
            AlertaUtil.advertencia("Ingresa un motivo de cierre.");
            return;
        }

        atraccionController.cambiarEstadoAtraccion(
                nombreZonaAsignada,
                operadorActivo.getNombreAtraccionAsignada(),
                EstadoAtraccion.CERRADA,
                motivo);

        cargarDatosAtraccion();
        AlertaUtil.exito("Atracción cerrada.");
    }

    // =========================================================================
    // Eventos — Panel embebido: Ciclo de atracción
    // =========================================================================

    /**
     * Acción del botón "Realizar Ciclo".
     * Despacha la cola virtual y notifica a los visitantes.
     */
    @FXML
    public void onRealizarCiclo() {
        if (atraccionAsignada == null) return;

        atraccionController.realizarCicloAtraccion(
                nombreZonaAsignada,
                operadorActivo.getNombreAtraccionAsignada());

        cargarDatosAtraccion(); // refrescar tamaño de cola
        AlertaUtil.exito("Ciclo realizado. Los visitantes fueron notificados.");
    }

    // =========================================================================
    // Eventos — Panel embebido: Mantenimiento
    // =========================================================================

    /**
     * Acción del botón "Registrar Revisión".
     * Devuelve la atracción al estado ACTIVA tras mantenimiento.
     */
    @FXML
    public void onRegistrarRevision() {
        if (atraccionAsignada == null) return;

        boolean confirmar = AlertaUtil.confirmar(
                "¿Confirmas que la atracción ya fue revisada y está lista para operar?");
        if (!confirmar) return;

        atraccionController.registrarRevision(
                nombreZonaAsignada,
                operadorActivo.getNombreAtraccionAsignada());

        cargarDatosAtraccion();
    }

    // =========================================================================
    // Eventos — Navegación
    // =========================================================================

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
