package co.edu.uniquindio.techpark.view;

import co.edu.uniquindio.techpark.controller.AlertaUtil;
import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Zona;
import javafx.fxml.FXML;

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

    // =========================================================================
    // Campos de la vista — reemplazar cuando exista ColaVirtualAtraccion.fxml
    // =========================================================================

    // TODO (Vista):
    // @FXML private ComboBox<String> comboZona;
    // @FXML private ComboBox<String> comboAtraccion;
    // @FXML private Label            lblTiempoEspera;
    // @FXML private Label            lblEstadoAcceso;
    // @FXML private Button           btnConsultarTiempo;
    // @FXML private Button           btnUnirseACola;
    // @FXML private Button           btnVolver;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        cargarZonas();

        // TODO (Vista): al cambiar la zona, recargar el combo de atracciones
        // comboZona.valueProperty().addListener((obs, old, nueva) -> cargarAtracciones(nueva));
    }

    // =========================================================================
    // Carga de datos
    // =========================================================================

    /**
     * Carga las zonas disponibles en el ComboBox de zonas.
     */
    private void cargarZonas() {
        List<Zona> zonas = atraccionController.obtenerZonas();

        // TODO (Vista): poblar el combo de zonas
        // comboZona.setItems(FXCollections.observableArrayList(
        //     zonas.stream().map(Zona::getNombreZona).toList()));
    }

    /**
     * Carga las atracciones de la zona seleccionada en el ComboBox de atracciones.
     */
    private void cargarAtracciones(String nombreZona) {
        // TODO (Vista): filtrar atracciones de la zona y poblar el combo
        // List<String> nombres = ParqueController.getParque()
        //     .getListaZonas().stream()
        //     .filter(z -> z.getNombreZona().equals(nombreZona))
        //     .flatMap(z -> z.getListaAtracciones().stream())
        //     .map(Atraccion::getNombreAtraccion)
        //     .toList();
        // comboAtraccion.setItems(FXCollections.observableArrayList(nombres));
    }

    // =========================================================================
    // Eventos
    // =========================================================================

    /**
     * Acción del botón "Consultar Tiempo de Espera".
     */
    @FXML
    public void onConsultarTiempoEspera() {

        // TODO (Vista): leer zona y atracción seleccionadas
        // String zona      = comboZona.getValue();
        // String atraccion = comboAtraccion.getValue();
        String zona      = ""; // reemplazar
        String atraccion = ""; // reemplazar

        if (zona.isEmpty() || atraccion.isEmpty()) {
            AlertaUtil.advertencia("Selecciona una zona y una atracción.");
            return;
        }

        int tiempo = visitanteController.consultarTiempoEspera(zona, atraccion);

        // TODO (Vista): mostrar el tiempo en la etiqueta
        // lblTiempoEspera.setText("Tiempo estimado de espera: " + tiempo + " min");
    }

    /**
     * Acción del botón "Unirse a la Cola Virtual".
     */
    @FXML
    public void onUnirseACola() {

        // TODO (Vista): leer zona y atracción seleccionadas
        // String zona      = comboZona.getValue();
        // String atraccion = comboAtraccion.getValue();
        String zona      = ""; // reemplazar
        String atraccion = ""; // reemplazar

        if (zona.isEmpty() || atraccion.isEmpty()) {
            AlertaUtil.advertencia("Selecciona una zona y una atracción.");
            return;
        }

        boolean acceso = visitanteController.accederAtraccion(documentoActivo, zona, atraccion);

        // TODO (Vista): actualizar etiqueta de estado de acceso
        // if (acceso) {
        //     lblEstadoAcceso.setText("✓ Estás en la cola virtual.");
        //     lblEstadoAcceso.setStyle("-fx-text-fill: green;");
        // } else {
        //     lblEstadoAcceso.setText("✗ Acceso denegado. Verifica requisitos o ticket.");
        //     lblEstadoAcceso.setStyle("-fx-text-fill: red;");
        // }
    }

    /**
     * Acción del botón "Volver" al PanelVisitante.
     */
    @FXML
    public void onVolver() {
        // TODO (Vista): navegar de regreso a PanelVisitante.fxml
        // NavegadorUtil.irA((Stage) btnVolver.getScene().getWindow(),
        //     "/fxml/PanelVisitante.fxml");
    }
}
