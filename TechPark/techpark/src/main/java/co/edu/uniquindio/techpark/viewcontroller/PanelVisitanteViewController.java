package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Notificacion;
import co.edu.uniquindio.techpark.model.Ticket;
import co.edu.uniquindio.techpark.model.TipoTicket;
import co.edu.uniquindio.techpark.model.Visitante;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

/**
 * ViewController de PanelVisitanteView.fxml
 *
 * Vista principal del visitante autenticado. Gestiona:
 *   - Resumen de cuenta (nombre y saldo).
 *   - Listado y compra de tickets.
 *   - Atracciones favoritas.
 *   - Notificaciones con opción de eliminación.
 *   - Navegación a Cola Virtual y cierre de sesión.
 */
public class PanelVisitanteViewController {

    // -------------------------------------------------------------------------
    // Controlador de negocio e identidad de sesión
    // -------------------------------------------------------------------------

    private final VisitanteController visitanteController = new VisitanteController();

    /**
     * Documento del visitante en sesión activa.
     * Se obtiene al construir el campo para que esté disponible desde initialize().
     */
    private final String documentoActivo = ParqueController.getDocumentoSesionActiva();

    // -------------------------------------------------------------------------
    // Campos FXML — Encabezado / resumen de cuenta
    // -------------------------------------------------------------------------

    @FXML private Label lblNombreBienvenida;
    @FXML private Label lblSaldo;

    // -------------------------------------------------------------------------
    // Campos FXML — Sección de Tickets
    // -------------------------------------------------------------------------

    @FXML private ListView<Ticket>     listViewTickets;
    @FXML private ComboBox<TipoTicket> comboTipoTicket;
    @FXML private Button               btnComprarTicket;

    // -------------------------------------------------------------------------
    // Campos FXML — Sección de Favoritos
    // -------------------------------------------------------------------------

    @FXML private ListView<String> listViewFavoritos;

    // -------------------------------------------------------------------------
    // Campos FXML — Sección de Notificaciones
    // -------------------------------------------------------------------------

    @FXML private ListView<Notificacion> listViewNotificaciones;
    @FXML private Button                 btnEliminarNotificacion;

    // -------------------------------------------------------------------------
    // Campos FXML — Navegación
    // -------------------------------------------------------------------------

    @FXML private Button btnIrAColaVirtual;
    @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    /**
     * Punto de entrada de JavaFX tras la inyección de los campos FXML.
     * Configura los componentes y carga todos los datos de sesión.
     */
    @FXML
    public void initialize() {
        // Guardia: sesión inválida impide cualquier operación posterior.
        if (documentoActivo == null || documentoActivo.isBlank()) {
            AlertaUtil.error("Error crítico: no hay una sesión activa. Reinicia la aplicación.");
            deshabilitarPanelCompleto();
            return;
        }

        configurarCellFactories();
        configurarComboTicket();
        cargarTodosLosDatos();
    }

    // =========================================================================
    // Configuración de componentes
    // =========================================================================

    /**
     * Define cómo se renderizan los objetos del modelo en cada ListView.
     */
    private void configurarCellFactories() {

        // Ticket: ID, tipo, estado y precio formateados.
        listViewTickets.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Ticket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("[%s]  %s  •  Estado: %s  •  $%.2f",
                            item.getIdTicket(),
                            item.getTipoTicket(),
                            item.getEstadoTicket(),
                            item.getPrecioTicket()));
                }
            }
        });

        // Notificacion (record): muestra el mensaje; id() se usa solo para eliminación.
        listViewNotificaciones.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Notificacion item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Ajusta los accessors según los componentes reales del record Notificacion.
                    setText(item.mensaje());
                }
            }
        });

        // Favoritos son String: la celda predeterminada es suficiente.
    }

    /** Carga los valores del enum TipoTicket en el ComboBox. */
    private void configurarComboTicket() {
        comboTipoTicket.setItems(FXCollections.observableArrayList(TipoTicket.values()));
    }

    // =========================================================================
    // Carga y sincronización de datos
    // =========================================================================

    /** Recarga todos los paneles. Útil tras operaciones que alteran el estado. */
    private void cargarTodosLosDatos() {
        cargarResumenCuenta();
        cargarTickets();
        cargarFavoritos();
        cargarNotificaciones();
    }

    private void cargarResumenCuenta() {
        Visitante v = visitanteController.obtenerVisitante(documentoActivo);
        if (v != null) {
            lblNombreBienvenida.setText("¡Bienvenido, " + v.getNombre() + "!");
            lblSaldo.setText(String.format("Saldo Disponible: $%.2f", v.getSaldoVirtual()));
        } else {
            lblNombreBienvenida.setText("¡Bienvenido!");
            lblSaldo.setText("Saldo Disponible: —");
        }
    }

    private void cargarTickets() {
        List<Ticket> tickets = visitanteController.obtenerTickets(documentoActivo);
        listViewTickets.setItems(
                FXCollections.observableArrayList(tickets != null ? tickets : Collections.emptyList()));
    }

    private void cargarFavoritos() {
        List<String> favs = visitanteController.obtenerFavoritos(documentoActivo);
        listViewFavoritos.setItems(
                FXCollections.observableArrayList(favs != null ? favs : Collections.emptyList()));
    }

    private void cargarNotificaciones() {
        List<Notificacion> lista = visitanteController.obtenerNotificaciones(documentoActivo);
        listViewNotificaciones.setItems(
                FXCollections.observableArrayList(lista != null ? lista : Collections.emptyList()));
    }

    // =========================================================================
    // Eventos — Compra de Tickets
    // =========================================================================

    @FXML
    public void onComprarTicket() {
        TipoTicket tipo = comboTipoTicket.getValue();

        if (tipo == null) {
            AlertaUtil.advertencia("Por favor, selecciona un tipo de ticket antes de continuar.");
            return;
        }

        boolean exito = visitanteController.comprarTicket(documentoActivo, tipo);

        if (exito) {
            AlertaUtil.exito("¡Compra exitosa! Tu ticket ha sido añadido a tu cuenta.");
            comboTipoTicket.setValue(null);   // Limpia la selección tras compra exitosa
            cargarResumenCuenta();
            cargarTickets();
        } else {
            AlertaUtil.error("Saldo insuficiente para adquirir el ticket seleccionado.");
        }
    }

    // =========================================================================
    // Eventos — Gestión de Notificaciones
    // =========================================================================

    @FXML
    public void onEliminarNotificacion() {
        Notificacion seleccionada = listViewNotificaciones.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            AlertaUtil.advertencia("Selecciona una notificación de la lista para eliminarla.");
            return;
        }

        visitanteController.eliminarNotificacion(documentoActivo, seleccionada.id());
        cargarNotificaciones();
    }

    // =========================================================================
    // Eventos — Navegación
    // =========================================================================

    @FXML
    public void onIrAColaVirtual() {
        NavegadorUtil.irA(
                (Stage) btnIrAColaVirtual.getScene().getWindow(),
                "/view/ColaVirtualAtraccionView.fxml");
    }

    @FXML
    public void onCerrarSesion() {
        new ParqueController().cerrarSesion();
        NavegadorUtil.irA(
                (Stage) btnCerrarSesion.getScene().getWindow(),
                "/view/InicioSesionView.fxml");
    }

    // =========================================================================
    // Utilitario interno
    // =========================================================================

    /**
     * Deshabilita todos los controles interactivos cuando la sesión no es válida.
     * Previene NullPointerExceptions por operaciones sobre un documento nulo.
     */
    private void deshabilitarPanelCompleto() {
        btnComprarTicket.setDisable(true);
        btnEliminarNotificacion.setDisable(true);
        btnIrAColaVirtual.setDisable(true);
        comboTipoTicket.setDisable(true);
    }
}