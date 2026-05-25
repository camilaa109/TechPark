package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.Visitante;
import co.edu.uniquindio.techpark.model.Zona;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
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
    @FXML private Label            lblTituloCola;
    @FXML private Label            lblTamanioCola;
    @FXML private ListView<Visitante> listViewCola;
    @FXML private Button           btnConsultarTiempo;
    @FXML private Button           btnUnirseACola;
    @FXML private Button           btnVolver;

    @FXML
    public void initialize() {
        cargarZonas();

        comboZona.valueProperty().addListener((obs, old, nueva) -> {
        comboAtraccion.setValue(null);
        ocultarCola();
        // Limpiamos ambos labels al cambiar de zona
        lblEstadoAcceso.setText("");
        lblTiempoEspera.setText(""); 
        
        cargarAtracciones(nueva);
    });

    comboAtraccion.valueProperty().addListener((obs, old, atraccion) -> {
        // Limpiamos ambos labels al cambiar de atracción
        lblEstadoAcceso.setText("");
        lblTiempoEspera.setText("");
        
        if (atraccion != null) cargarCola(comboZona.getValue(), atraccion);
        else ocultarCola();
    });

        comboZona.valueProperty().addListener((obs, old, nueva) -> {
            comboAtraccion.setValue(null);
            ocultarCola();
            cargarAtracciones(nueva);
        });

        comboAtraccion.valueProperty().addListener((obs, old, atraccion) -> {
            if (atraccion != null) cargarCola(comboZona.getValue(), atraccion);
            else ocultarCola();
        });

        listViewCola.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Visitante v, boolean empty) {
                super.updateItem(v, empty);
                if (empty || v == null) setText(null);
                else setText((getIndex() + 1) + ".  " + v.getNombre());
            }
        });
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
     * Carga la cola virtual de la atracción seleccionada en el ListView.
     */
    private void cargarCola(String nombreZona, String nombreAtraccion) {
        if (nombreZona == null || nombreAtraccion == null) { ocultarCola(); return; }

        Atraccion atraccion = ParqueController.getParque()
                .getListaZonas().stream()
                .filter(z -> z.getNombreZona().equals(nombreZona))
                .flatMap(z -> z.getListaAtracciones().stream())
                .filter(a -> a.getNombreAtraccion().equals(nombreAtraccion))
                .findFirst().orElse(null);

        if (atraccion == null || atraccion.getColaVirtual() == null) { ocultarCola(); return; }

        List<Visitante> visitantes = new ArrayList<>(atraccion.getColaVirtual());
        int tamano = visitantes.size();

        lblTituloCola.setVisible(true);
        lblTituloCola.setManaged(true);
        lblTamanioCola.setText(tamano + " persona" + (tamano != 1 ? "s" : "") + " en cola");
        lblTamanioCola.setVisible(true);
        lblTamanioCola.setManaged(true);
        listViewCola.setItems(FXCollections.observableArrayList(visitantes));
        listViewCola.setVisible(true);
        listViewCola.setManaged(true);
    }

    private void ocultarCola() {
        lblTituloCola.setVisible(false);  lblTituloCola.setManaged(false);
        lblTamanioCola.setVisible(false); lblTamanioCola.setManaged(false);
        listViewCola.setVisible(false);   listViewCola.setManaged(false);
        listViewCola.getItems().clear();
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

        lblTiempoEspera.setText("Tiempo estimado de espera: " + tiempo/60 + " minutos");
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

        String acceso = visitanteController.accederAtraccion(documentoActivo, zona, atraccion);

        lblEstadoAcceso.setText(acceso);
        cargarCola(zona, atraccion);
    }

    /**
     * Acción del botón "Volver" al PanelVisitante.
     */
    @FXML
    public void onVolver() {
        NavegadorUtil.irA((Stage) btnVolver.getScene().getWindow(), "/view/PanelVisitanteView.fxml");
    }
}