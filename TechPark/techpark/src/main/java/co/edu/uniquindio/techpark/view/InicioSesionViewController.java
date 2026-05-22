package co.edu.uniquindio.techpark.view;

import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.model.Rol;
import javafx.fxml.FXML;

/**
 * ViewController de InicioSesion.fxml
 *
 * Maneja el formulario de login y redirige a la vista
 * correspondiente según el Rol retornado.
 */
public class InicioSesionViewController {

    private final ParqueController parqueController = new ParqueController();

    // -------------------------------------------------------------------------
    // Campos de la vista — reemplazar cuando exista InicioSesion.fxml
    // -------------------------------------------------------------------------

    // TODO (Vista): descomentar y enlazar con @FXML cuando exista el .fxml
    // @FXML private TextField txtDocumento;
    // @FXML private PasswordField txtContrasenia;
    // @FXML private Button btnIniciarSesion;
    // @FXML private Label lblError;

    // -------------------------------------------------------------------------
    // Inicialización
    // -------------------------------------------------------------------------

    @FXML
    public void initialize() {
        // TODO (Vista): configurar listeners, limpiar campos, enfocar txtDocumento
        // txtDocumento.requestFocus();
        // lblError.setVisible(false);
    }

    // -------------------------------------------------------------------------
    // Eventos
    // -------------------------------------------------------------------------

    /**
     * Acción del botón "Iniciar Sesión".
     * Lee las credenciales, intenta el login y navega según el Rol.
     */
    @FXML
    public void onIniciarSesion() {

        // TODO (Vista): leer valores de los campos
        // String documento  = txtDocumento.getText().trim();
        // String contrasenia = txtContrasenia.getText();
        String documento   = "";   // reemplazar por txtDocumento.getText().trim()
        String contrasenia = "";   // reemplazar por txtContrasenia.getText()

        Rol rol = parqueController.iniciarSesion(documento, contrasenia);

        if (rol == null) {
            // TODO (Vista): mostrar mensaje de error en la etiqueta
            // lblError.setText("Documento o contraseña incorrectos");
            // lblError.setVisible(true);
            return;
        }

        // TODO (Vista): navegar a la pantalla correspondiente según el rol
        // Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        // switch (rol) {
        //     case VISITANTE -> NavegadorUtil.irA(stage, "/fxml/PanelVisitante.fxml");
        //     case OPERADOR  -> NavegadorUtil.irA(stage, "/fxml/PanelOperador.fxml");
        //     case ADMIN     -> NavegadorUtil.irA(stage, "/fxml/GestionVisitantes.fxml");
        // }
    }

    /**
     * Acción del enlace/botón "¿No tienes cuenta? Regístrate".
     */
    @FXML
    public void onIrARegistro() {
        // TODO (Vista): navegar a RegistroVisitante.fxml
        // Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        // NavegadorUtil.irA(stage, "/fxml/RegistroVisitante.fxml");
    }
}
