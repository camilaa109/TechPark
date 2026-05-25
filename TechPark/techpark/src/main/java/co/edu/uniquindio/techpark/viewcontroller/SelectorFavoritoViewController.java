package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.model.Zona;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

/**
 * ViewController de SelectorFavoritoView.fxml
 *
 * Ventana modal para seleccionar una atracción y añadirla a favoritos.
 * Cuando el usuario confirma, invoca el callback {@code onConfirmado}
 * con el nombre de la atracción elegida, y cierra la ventana.
 */
public class SelectorFavoritoViewController {

    private final AtraccionController atraccionController = new AtraccionController();

    /** Callback que recibe el nombre de la atracción elegida. */
    private Consumer<String> onConfirmado;

    @FXML private ComboBox<String> comboZona;
    @FXML private ComboBox<String> comboAtraccion;
    @FXML private Button           btnConfirmar;
    @FXML private Button           btnCancelar;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        cargarZonas();

        // Al cambiar de zona, recargar las atracciones de esa zona
        comboZona.valueProperty().addListener((obs, old, zona) -> {
            comboAtraccion.setValue(null);
            if (zona != null) cargarAtracciones(zona);
            else comboAtraccion.getItems().clear();
        });
    }

    // =========================================================================
    // API pública — el llamador inyecta el callback antes de mostrar la ventana
    // =========================================================================

    public void setOnConfirmado(Consumer<String> callback) {
        this.onConfirmado = callback;
    }

    // =========================================================================
    // Carga de datos
    // =========================================================================

    private void cargarZonas() {
        List<Zona> zonas = atraccionController.obtenerZonas();
        List<String> nombres = zonas.stream().map(Zona::getNombreZona).toList();
        comboZona.setItems(FXCollections.observableArrayList(nombres));
    }

    private void cargarAtracciones(String nombreZona) {
        List<Zona> zonas = atraccionController.obtenerZonas();
        zonas.stream()
                .filter(z -> z.getNombreZona().equals(nombreZona))
                .findFirst()
                .ifPresent(z -> {
                    List<String> nombres = z.getListaAtracciones()
                            .stream()
                            .map(a -> a.getNombreAtraccion())
                            .toList();
                    comboAtraccion.setItems(FXCollections.observableArrayList(nombres));
                });
    }

    // =========================================================================
    // Eventos
    // =========================================================================

    @FXML
    public void onConfirmar() {
        String atraccion = comboAtraccion.getValue();

        if (comboZona.getValue() == null) {
            AlertaUtil.advertencia("Selecciona una zona primero.");
            return;
        }
        if (atraccion == null) {
            AlertaUtil.advertencia("Selecciona una atracción.");
            return;
        }

        if (onConfirmado != null) {
            onConfirmado.accept(atraccion);
        }

        cerrar();
    }

    @FXML
    public void onCancelar() {
        cerrar();
    }

    private void cerrar() {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }
}