package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.VisitanteController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * ViewController de RegistroVisitante.fxml
 *
 * Formulario público de alta de nuevo visitante. Accesible desde
 * InicioSesion (sin requerir una sesión activa en el sistema).
 */
public class RegistroVisitanteViewController {

    private final VisitanteController visitanteController = new VisitanteController();

    // =========================================================================
    // Campos de la vista vinculados al archivo FXML
    // =========================================================================

    @FXML private TextField     txtNombre;
    @FXML private TextField     txtDocumento;
    @FXML private TextField     txtEdad;
    @FXML private PasswordField txtContrasenia;
    @FXML private TextField     txtEstatura;
    @FXML private Button        btnRegistrar;
    @FXML private Hyperlink     lnkVolver;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        // Enfocar el primer campo del formulario para mejorar la experiencia de usuario
        txtNombre.requestFocus();

        // Validación en tiempo real (opcional): Impedir caracteres no numéricos en la edad
        txtEdad.textProperty().addListener((obs, viejo, nuevo) -> {
            if (!nuevo.matches("\\d*")) {
                txtEdad.setText(nuevo.replaceAll("[^\\d]", ""));
            }
        });
    }

    // =========================================================================
    // Eventos del Formulario
    // =========================================================================

    /**
     * Acción del botón "Registrar".
     * Extrae los datos de la interfaz de usuario, los valida y procesa el registro.
     */
    @FXML
    public void onRegistrar() {
        // CORREGIDO: Captura de datos en tiempo real desde los nodos de JavaFX
        String nombre      = txtNombre.getText().trim();
        String documento   = txtDocumento.getText().trim();
        String edadTexto   = txtEdad.getText().trim();
        String contrasenia = txtContrasenia.getText();
        String estaturaTexto = txtEstatura.getText().trim();

        // Validación básica de campos vacíos en la capa de presentación
        if (nombre.isEmpty() || documento.isEmpty() || contrasenia.isEmpty() || edadTexto.isEmpty() || estaturaTexto.isEmpty()) {
            AlertaUtil.error("Todos los campos son estrictamente obligatorios.");
            return;
        }

        int edad;
        double estatura;
        try {
            edad     = Integer.parseInt(edadTexto);
            estatura = Double.parseDouble(estaturaTexto);
        } catch (NumberFormatException e) {
            AlertaUtil.error("La edad debe ser un número entero y la estatura un decimal (Ej: 1.70).");
            return;
        }

        // Delegación segura a la capa lógica intermedia
        boolean exito = visitanteController.registrarVisitante(nombre, documento, edad, contrasenia, estatura);

        if (exito) {
            AlertaUtil.exito("¡Registro exitoso! Ya puedes iniciar sesión con tus credenciales.");
            
            // Redirección inmediata hacia la pantalla de login
            NavegadorUtil.irA((Stage) btnRegistrar.getScene().getWindow(), "/view/InicioSesionView.fxml");
        } else {
            AlertaUtil.error("El documento digitado ya se encuentra registrado en el sistema.");
        }
    }

    /**
     * Acción del enlace "Volver al inicio de sesión".
     */
    @FXML
    public void onVolver() {
        // Redirige al usuario al index principal de autenticación
        NavegadorUtil.irA((Stage) lnkVolver.getScene().getWindow(), "/view/InicioSesionView.fxml");
    }
}