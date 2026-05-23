package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.Zona;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

/**
 * ViewController de ColaVirtualAtraccion.fxml
 *
 * Permite al visitante:
 *  - Seleccionar zona y atracción.
 *  - Consultar el tiempo de espera estimado.
 *  - Unirse a la cola virtual (accesoAtraccion).
 */
public class ColaVirtualAtraccionViewController {

    private final VisitanteController visitanteController = new VisitanteController();
    private final AtraccionController atraccionController = new AtraccionController();

    private final String documentoActivo = ParqueController.getDocumentoSesionActiva();

    @FXML private ComboBox<String> comboZona;
    @FXML private ComboBox<String> comboAtraccion;
    @FXML private Label            lblTiempoEspera;
    @FXML private Label            lblEstadoAcceso;
    @FXML private Button           btnConsultarTiempo;
    @FXML private Button           btnUnirseACola;
    @FXML private Button           btnVolver;

    @FXML
    public void initialize() {
        cargarZonas();

        comboZona.valueProperty().addListener((obs, old, nueva) -> cargarAtracciones(nueva));
    }

    /**
     * Carga las zonas disponibles en el ComboBox de zonas.
     */
    private void cargarZonas() {
        List<Zona> zonas = atraccionController.obtenerZonas();

        comboZona.setItems(FXCollections.observableArrayList(
        zonas.stream().map(Zona::getNombreZona).toList()));
    }

    /**
     * Carga las atracciones de la zona seleccionada en el ComboBox de atracciones.
     */
    private void cargarAtracciones(String nombreZona) {
        List<String> nombres = ParqueController.getParque()
            .getListaZonas().stream()
            .filter(z -> z.getNombreZona().equals(nombreZona))
            .flatMap(z -> z.getListaAtracciones().stream())
            .map(Atraccion::getNombreAtraccion)
            .toList();
        comboAtraccion.setItems(FXCollections.observableArrayList(nombres));
    }

    /**
     * Acción del botón "Consultar Tiempo de Espera".
     */
    @FXML
    public void onConsultarTiempoEspera() {

        String zona      = comboZona.getValue();
        String atraccion = comboAtraccion.getValue();

        if (zona.isEmpty() || atraccion.isEmpty()) {
            AlertaUtil.advertencia("Selecciona una zona y una atracción.");
            return;
        }

        int tiempo = visitanteController.consultarTiempoEspera(zona, atraccion);

        lblTiempoEspera.setText("Tiempo estimado de espera: " + tiempo + " segundos");
    }

    /**
     * Acción del botón "Unirse a la Cola Virtual".
     */
    @FXML
    public void onUnirseACola() {

        String zona      = comboZona.getValue();
        String atraccion = comboAtraccion.getValue();

        if (zona.isEmpty() || atraccion.isEmpty()) {
            AlertaUtil.advertencia("Selecciona una zona y una atracción.");
            return;
        }

        boolean acceso = visitanteController.accederAtraccion(documentoActivo, zona, atraccion);

        if (acceso) {
            lblEstadoAcceso.setText("✓ Estás en la cola virtual.");
            lblEstadoAcceso.setStyle("-fx-text-fill: green;");
        } else {
            lblEstadoAcceso.setText("✗ Acceso denegado. Verifica requisitos o ticket.");
            lblEstadoAcceso.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Acción del botón "Volver" al PanelVisitante.
     */
    @FXML
    public void onVolver() {
        NavegadorUtil.irA((Stage) btnVolver.getScene().getWindow(), "/view/PanelVisitanteView.fxml");
    }
}
