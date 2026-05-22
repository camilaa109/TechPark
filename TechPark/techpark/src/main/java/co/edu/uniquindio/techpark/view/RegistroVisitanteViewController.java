package co.edu.uniquindio.techpark.view;

import co.edu.uniquindio.techpark.controller.AlertaUtil;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import javafx.fxml.FXML;

/**
 * ViewController de RegistroVisitante.fxml
 *
 * Formulario de alta de nuevo visitante. Accesible desde
 * InicioSesion (sin sesión activa).
 */
public class RegistroVisitanteViewController {

    private final VisitanteController visitanteController = new VisitanteController();

    // -------------------------------------------------------------------------
    // Campos de la vista — reemplazar cuando exista RegistroVisitante.fxml
    // -------------------------------------------------------------------------

    // TODO (Vista): descomentar y enlazar con @FXML cuando exista el .fxml
    // @FXML private TextField     txtNombre;
    // @FXML private TextField     txtDocumento;
    // @FXML private TextField     txtEdad;
    // @FXML private PasswordField txtContrasenia;
    // @FXML private TextField     txtEstatura;
    // @FXML private Button        btnRegistrar;
    // @FXML private Hyperlink     lnkVolver;

    // -------------------------------------------------------------------------
    // Inicialización
    // -------------------------------------------------------------------------

    @FXML
    public void initialize() {
        // TODO (Vista): preparar validaciones en tiempo real si se desea
        // txtEdad.textProperty().addListener((obs, old, nuevo) -> {
        //     if (!nuevo.matches("\\d*")) txtEdad.setText(old);
        // });
    }

    // -------------------------------------------------------------------------
    // Eventos
    // -------------------------------------------------------------------------

    /**
     * Acción del botón "Registrarse".
     * Valida los campos y delega el registro al controlador de negocio.
     */
    @FXML
    public void onRegistrar() {

        // TODO (Vista): leer valores de los campos
        // String nombre      = txtNombre.getText().trim();
        // String documento   = txtDocumento.getText().trim();
        // String edadTexto   = txtEdad.getText().trim();
        // String contrasenia = txtContrasenia.getText();
        // String estaturaTexto = txtEstatura.getText().trim();
        String nombre        = "";   // reemplazar
        String documento     = "";   // reemplazar
        String edadTexto     = "0";  // reemplazar
        String contrasenia   = "";   // reemplazar
        String estaturaTexto = "0";  // reemplazar

        // Validación básica de campos vacíos
        if (nombre.isEmpty() || documento.isEmpty() || contrasenia.isEmpty()) {
            AlertaUtil.error("Todos los campos son obligatorios.");
            return;
        }

        int edad;
        double estatura;
        try {
            edad     = Integer.parseInt(edadTexto);
            estatura = Double.parseDouble(estaturaTexto);
        } catch (NumberFormatException e) {
            AlertaUtil.error("Edad y estatura deben ser valores numéricos.");
            return;
        }

        boolean exito = visitanteController.registrarVisitante(nombre, documento, edad, contrasenia, estatura);

        if (exito) {
            AlertaUtil.exito("Visitante registrado correctamente.");
            // TODO (Vista): redirigir a InicioSesion.fxml
            // NavegadorUtil.irA((Stage) btnRegistrar.getScene().getWindow(),
            //     "/fxml/InicioSesion.fxml");
        } else {
            AlertaUtil.error("El documento ya está registrado.");
        }
    }

    /**
     * Acción del enlace "Volver al inicio de sesión".
     */
    @FXML
    public void onVolver() {
        // TODO (Vista): navegar de regreso a InicioSesion.fxml
        // NavegadorUtil.irA((Stage) lnkVolver.getScene().getWindow(),
        //     "/fxml/InicioSesion.fxml");
    }
}
