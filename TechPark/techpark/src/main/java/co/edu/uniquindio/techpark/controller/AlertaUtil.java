package co.edu.uniquindio.techpark.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Utilidad de alertas para JavaFX.
 *
 * Centraliza la creación de diálogos informativos, de error, advertencia
 * y confirmación. Todos los controladores deben usar esta clase en lugar
 * de instanciar Alert directamente, garantizando consistencia visual.
 *
 * Uso típico desde un controlador:
 * <pre>
 *   AlertaUtil.exito("Visitante registrado correctamente");
 *   AlertaUtil.error("El documento ya existe");
 *   boolean confirmar = AlertaUtil.confirmar("¿Deseas eliminar este registro?");
 * </pre>
 */
public class AlertaUtil {

    private AlertaUtil() {
        // Clase utilitaria — no instanciar
    }

    // =========================================================================
    // Alertas simples
    // =========================================================================

    /** Muestra un diálogo de información (éxito). */
    public static void exito(String mensaje) {
        mostrar(AlertType.INFORMATION, "Éxito", mensaje);
    }

    /** Muestra un diálogo de error. */
    public static void error(String mensaje) {
        mostrar(AlertType.ERROR, "Error", mensaje);
    }

    /** Muestra un diálogo de advertencia. */
    public static void advertencia(String mensaje) {
        mostrar(AlertType.WARNING, "Advertencia", mensaje);
    }

    // =========================================================================
    // Alerta con título personalizado
    // =========================================================================

    /** Muestra un diálogo con tipo, título y mensaje personalizados. */
    public static void mostrar(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // TODO (Vista): aplicar hoja de estilos del parque si se desea
        //   alert.getDialogPane().getStylesheets().add(
        //       AlertaUtil.class.getResource("/styles/techpark.css").toExternalForm());

        alert.showAndWait();
    }

    // =========================================================================
    // Confirmación
    // =========================================================================

    /**
     * Muestra un diálogo de confirmación con botones "Aceptar" y "Cancelar".
     *
     * @return true si el usuario presionó Aceptar, false si canceló o cerró el diálogo.
     */
    public static boolean confirmar(String mensaje) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar acción");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // TODO (Vista): personalizar estilos del panel de confirmación
        //   alert.getDialogPane().getStylesheets().add(
        //       AlertaUtil.class.getResource("/styles/techpark.css").toExternalForm());

        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}