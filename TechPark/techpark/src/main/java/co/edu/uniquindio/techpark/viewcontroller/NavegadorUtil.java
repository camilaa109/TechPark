package co.edu.uniquindio.techpark.viewcontroller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * Clase utilitaria para gestionar la navegación y el cambio de pantallas (vistas FXML)
 * en la aplicación JavaFX.
 */
public class NavegadorUtil {

    /**
     * Cambia la escena actual del Stage por una nueva vista FXML.
     *
     * @param stage      El Stage (ventana) actual donde se renderizará la nueva vista.
     * @param rutaFxml   La ruta absoluta o relativa del archivo .fxml (ej: "/fxml/PanelVisitante.fxml").
     */
    public static void irA(Stage stage, String rutaFxml) {
        try {
            // 1. Validar que el stage y la ruta no sean nulos
            if (stage == null) {
                throw new IllegalArgumentException("El Stage no puede ser nulo.");
            }
            
            // 2. Cargar el archivo FXML
            Parent root = FXMLLoader.load(Objects.requireNonNull(NavegadorUtil.class.getResource(rutaFxml)));
            
            // 3. Obtener la escena actual o crear una nueva si no existe
            Scene escenaActual = stage.getScene();
            if (escenaActual == null) {
                escenaActual = new Scene(root);
                stage.setScene(escenaActual);
            } else {
                escenaActual.setRoot(root);
            }
            
            // 4. Ajustar el tamaño de la ventana al nuevo contenido y centrarla (opcional)
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();

        } catch (NullPointerException e) {
            AlertaUtil.error("No se encontró el archivo de la vista: " + rutaFxml);
            e.printStackTrace();
        } catch (IOException e) {
            AlertaUtil.error("No se pudo cargar la interfaz de usuario en: " + rutaFxml);
            e.printStackTrace();
        }
    }
}
