package co.edu.uniquindio.techpark.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * ViewController de MapaParqueView.fxml
 *
 * Muestra el mapa del parque en una ventana modal independiente.
 */
public class MapaParqueViewController {

    @FXML private ImageView imgMapa;
    @FXML private Label     lblEstado;
    @FXML private Button    btnCerrar;

    /** Ruta de la imagen dentro de src/main/resources */
    private static final String RUTA_MAPA = "/images/Parque-de-atracciones-mapa.jpg";

    @FXML
    public void initialize() {
        cargarMapa();
    }

    // =========================================================================
    // Carga de imagen
    // =========================================================================

    private void cargarMapa() {
        try {
            var stream = getClass().getResourceAsStream(RUTA_MAPA);

            if (stream == null) {
                lblEstado.setText("Mapa no disponible — coloca la imagen en src/main/resources/imagenes/Parque-de-atracciones-mapa.jpg");
                return;
            }

            Image imagen = new Image(stream);
            imgMapa.setImage(imagen);
            lblEstado.setText("Mapa del Parque TechPark");

        } catch (Exception e) {
            lblEstado.setText("Error al cargar el mapa: " + e.getMessage());
        }
    }

    // =========================================================================
    // Eventos
    // =========================================================================

    @FXML
    public void onCerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}