package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Notificacion;
import co.edu.uniquindio.techpark.model.Ticket;
import co.edu.uniquindio.techpark.model.TipoTicket;
import co.edu.uniquindio.techpark.model.Visitante; // Asegúrate de apuntar a tu paquete real de utilidades

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

/**
 * ViewController de PanelVisitante.fxml
 *
 * Vista principal del visitante conectada al sistema de forma desacoplada.
 */
public class PanelVisitanteViewController {

    private final VisitanteController visitanteController = new VisitanteController();

    // Documento del visitante activo en sesión
    private final String documentoActivo = ParqueController.getDocumentoSesionActiva();

    // =========================================================================
    // Campos de la vista vinculados al archivo FXML
    // =========================================================================

    // --- Encabezado / resumen ---
    @FXML private Label lblNombreBienvenida;
    @FXML private Label lblSaldo;

    // --- Sección tickets ---
    @FXML private ListView<Ticket> listViewTickets;
    @FXML private ComboBox<TipoTicket> comboTipoTicket;
    @FXML private Button               btnComprarTicket;

    // --- Sección favoritos ---
    @FXML private ListView<String> listViewFavoritos;

    // --- Sección notificaciones (Embebido) ---
    @FXML private ListView<Notificacion> listViewNotificaciones;
    @FXML private Button                 btnEliminarNotificacion;

    // --- Navegación ---
    @FXML private Button btnIrAColaVirtual;
    @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        if (documentoActivo == null) {
            AlertaUtil.error("Error crítico: Sesión de usuario no válida.");
            return;
        }

        // Cargar tipos de ticket en el ComboBox desde el Enum
        comboTipoTicket.setItems(FXCollections.observableArrayList(TipoTicket.values()));

        // Personalizar cómo se despliega el objeto Ticket dentro de las celdas de la lista
        listViewTickets.setCellFactory(param -> new javafx.scene.control.ListCell<Ticket>() {
            @Override
            protected void updateItem(Ticket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Estructura el String en base a los atributos reales de tu modelo Ticket.java
                    setText(item.getIdTicket() + " - " + item.getTipoTicket() + " (" + item.getEstadoTicket() + ") - Price: $" + item.getPrecioTicket());
                }
            }
        });

        // Renderizar la información completa en pantalla
        cargarResumenCuenta();
        cargarTickets();
        cargarFavoritos();
        cargarNotificaciones();
    }

    // =========================================================================
    // Carga de datos y sincronización de UI
    // =========================================================================

    private void cargarResumenCuenta() {
        Visitante v = visitanteController.obtenerVisitante(documentoActivo);
        if (v != null) {
            lblNombreBienvenida.setText("¡Bienvenido, " + v.getNombre() + "!");
            // Se asume el método getSaldo() en el modelo, renderizado simple
            lblSaldo.setText("Saldo Disponible: $" + v.getSaldoVirtual());
        }
    }

    private void cargarTickets() {
        List<Ticket> tickets = visitanteController.obtenerTickets(documentoActivo);
        listViewTickets.setItems(FXCollections.observableArrayList(tickets));
    }

    private void cargarFavoritos() {
        List<String> favs = visitanteController.obtenerFavoritos(documentoActivo);
        listViewFavoritos.setItems(FXCollections.observableArrayList(favs));
    }

    private void cargarNotificaciones() {
        List<Notificacion> lista = visitanteController.obtenerNotificaciones(documentoActivo);
        listViewNotificaciones.setItems(FXCollections.observableArrayList(lista));
    }

    // =========================================================================
    // Eventos — Compra de Tickets
    // =========================================================================

    /**
     * Acción del botón "Comprar Ticket".
     */
    @FXML
    public void onComprarTicket() {
        TipoTicket tipo = comboTipoTicket.getValue();

        if (tipo == null) {
            AlertaUtil.advertencia("Por favor, selecciona un tipo de ticket de la lista.");
            return;
        }

        boolean exito = visitanteController.comprarTicket(documentoActivo, tipo);

        if (exito) {
            AlertaUtil.exito("¡Compra exitosa! Tu ticket ha sido añadido a tu cuenta.");
            // Refrescar los componentes que cambian tras la transacción monetaria
            cargarResumenCuenta();
            cargarTickets();
        } else {
            AlertaUtil.error("No tienes saldo suficiente para adquirir este ticket.");
        }
    }

    // =========================================================================
    // Eventos — Gestión de Notificaciones
    // =========================================================================

    /**
     * Acción del botón "Eliminar Notificación".
     */
    @FXML
    public void onEliminarNotificacion() {
        // CORREGIDO: Captura real del objeto seleccionado en el ListView genérico
        Notificacion seleccionada = listViewNotificaciones.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            AlertaUtil.advertencia("Selecciona una notificación de la lista para eliminar.");
            return;
        }

        // Enviamos el identificador dinámico de la notificación al core
        visitanteController.eliminarNotificacion(documentoActivo, seleccionada.id());
        
        // Sincronizar el panel visual inmediatamente
        cargarNotificaciones();
    }

    // =========================================================================
    // Eventos — Navegación
    // =========================================================================

    /**
     * Acción del botón "Ver Cola Virtual / Atracciones".
     */
    @FXML
    public void onIrAColaVirtual() {
        // CORREGIDO: Enrutamiento habilitado hacia las colas de las atracciones
        NavegadorUtil.irA((Stage) btnIrAColaVirtual.getScene().getWindow(), 
            "/fxml/ColaVirtualAtraccion.fxml");
    }

    /**
     * Acción del botón "Cerrar Sesión".
     */
    @FXML
    public void onCerrarSesion() {
        new ParqueController().cerrarSesion();
        NavegadorUtil.irA((Stage) btnCerrarSesion.getScene().getWindow(), 
            "/fxml/InicioSesion.fxml");
    }
}