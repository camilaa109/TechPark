package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Notificacion;
import co.edu.uniquindio.techpark.model.Ticket;
import co.edu.uniquindio.techpark.model.TipoTicket;
import co.edu.uniquindio.techpark.model.Visitante;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
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
 *   - Navegación a Cola Virtual, Mapa del Parque y cierre de sesión.
 */
public class PanelVisitanteViewController {

    // -------------------------------------------------------------------------
    // Controlador de negocio e identidad de sesión
    // -------------------------------------------------------------------------

    private final VisitanteController visitanteController = new VisitanteController();

    private final String documentoActivo = ParqueController.getDocumentoSesionActiva();

    // -------------------------------------------------------------------------
    // Campos FXML — Encabezado / resumen de cuenta
    // -------------------------------------------------------------------------

    @FXML private Label lblNombreBienvenida;
    @FXML private Label lblSaldo;
    @FXML private Label lblPrecioTicket;

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
    @FXML private Button           btnAgregarFavorito;
    @FXML private Button           btnEliminarFavorito;

    // -------------------------------------------------------------------------
    // Campos FXML — Sección de Notificaciones
    // -------------------------------------------------------------------------

    @FXML private ListView<Notificacion> listViewNotificaciones;
    @FXML private Button                 btnEliminarNotificacion;

    // -------------------------------------------------------------------------
    // Campos FXML — Navegación
    // -------------------------------------------------------------------------

    @FXML private Button btnVerMapa;
    @FXML private Button btnIrAColaVirtual;
    @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        if (documentoActivo == null || documentoActivo.isBlank()) {
            AlertaUtil.error("Error crítico: no hay una sesión activa. Reinicia la aplicación.");
            deshabilitarPanelCompleto();
            return;
        }

        configurarCellFactories();
        configurarComboTicket();
        cargarTodosLosDatos();
        iniciarActualizacionAutomatica();
    }

    // =========================================================================
    // Configuración de componentes
    // =========================================================================

    private void configurarCellFactories() {

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

        listViewNotificaciones.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Notificacion item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.mensaje());
                }
            }
        });
    }

    private void configurarComboTicket() {
        comboTipoTicket.setItems(FXCollections.observableArrayList(TipoTicket.values()));
        comboTipoTicket.valueProperty().addListener((obs, old, seleccionado) -> {
            if (seleccionado != null) {
                lblPrecioTicket.setText(String.format("Precio: $%.2f", seleccionado.getPrecio()));
            } else {
                lblPrecioTicket.setText("");
            }
        });
    }

    // =========================================================================
    // Carga y sincronización de datos
    // =========================================================================

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
            comboTipoTicket.setValue(null);
            cargarResumenCuenta();
            cargarTickets();
        } else {
            AlertaUtil.error("Saldo insuficiente para adquirir el ticket seleccionado.");
        }
    }

    // =========================================================================
    // Eventos — Gestión de Favoritos
    // =========================================================================

    @FXML
    public void onAgregarFavorito() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/SelectorFavoritoView.fxml"));
            Parent root = loader.load();

            SelectorFavoritoViewController controller = loader.getController();
            controller.setOnConfirmado(nombreAtraccion -> {
                visitanteController.agregarFavorito(documentoActivo, nombreAtraccion);
                cargarFavoritos();
                AlertaUtil.exito("\"" + nombreAtraccion + "\" añadida a favoritos.");
            });

            Stage stage = new Stage();
            stage.setTitle("Agregar Favorito");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAgregarFavorito.getScene().getWindow());
            stage.show();

        } catch (Exception e) {
            AlertaUtil.error("No se pudo abrir el selector: " + e.getMessage());
        }
    }

    @FXML
    public void onEliminarFavorito() {
        String seleccionado = listViewFavoritos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            AlertaUtil.advertencia("Selecciona una atracción de la lista para eliminarla de favoritos.");
            return;
        }

        visitanteController.eliminarFavorito(documentoActivo, seleccionado);
        cargarFavoritos();
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
    public void onVerMapa() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/MapaParqueView.fxml"));
            Parent root = loader.load();

            Stage mapaStage = new Stage();
            mapaStage.setTitle("Mapa del Parque — TechPark");
            mapaStage.setScene(new Scene(root));
            mapaStage.setResizable(true);
            mapaStage.initModality(Modality.WINDOW_MODAL);
            mapaStage.initOwner(btnVerMapa.getScene().getWindow());
            mapaStage.show();

        } catch (Exception e) {
            AlertaUtil.error("No se pudo abrir el mapa: " + e.getMessage());
        }
    }

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

    private void iniciarActualizacionAutomatica() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> {
            Task<List<Notificacion>> task = new Task<>() {
                @Override
                protected List<Notificacion> call() {
                    return visitanteController.obtenerNotificaciones(documentoActivo);
                }
            };
            task.setOnSucceeded(ev ->
                listViewNotificaciones.setItems(
                    FXCollections.observableArrayList(
                        task.getValue() != null ? task.getValue() : Collections.emptyList()))
            );
            new Thread(task).start();
        }));
        listViewNotificaciones.refresh();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void deshabilitarPanelCompleto() {
        btnComprarTicket.setDisable(true);
        btnEliminarNotificacion.setDisable(true);
        btnAgregarFavorito.setDisable(true);
        btnEliminarFavorito.setDisable(true);
        btnIrAColaVirtual.setDisable(true);
        btnVerMapa.setDisable(true);
        comboTipoTicket.setDisable(true);
    }
}