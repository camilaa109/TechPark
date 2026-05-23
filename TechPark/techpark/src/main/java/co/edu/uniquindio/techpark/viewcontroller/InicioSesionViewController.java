package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.model.Rol;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * ViewController de InicioSesion.fxml
 *
 * Maneja el formulario de login de manera desacoplada y redirige a la vista
 * correspondiente según el Rol retornado por la lógica de negocio.
 */
public class InicioSesionViewController {

    private final ParqueController parqueController = new ParqueController();

    @FXML private TextField txtDocumento;
    @FXML private PasswordField txtContrasenia;
    @FXML private Button btnIniciarSesion;
    @FXML private Label lblError;

    @FXML
    public void initialize() {
        txtDocumento.requestFocus();
        lblError.setVisible(false);
    }

    /**
     * Acción del botón "Iniciar Sesión".
     * Lee las credenciales, intenta el login y navega según el Rol.
     */
    @FXML
    public void onIniciarSesion() {
        String documento   = txtDocumento.getText().trim();
        String contrasenia = txtContrasenia.getText();

        // Validación segura en la vista antes de ir a la lógica
        if (documento.isEmpty() || contrasenia.isEmpty()) {
            lblError.setText("Por favor, rellene todos los campos.");
            lblError.setVisible(true);
            return;
        }

        // Invoca la validación en la capa lógica pura
        Rol rol = parqueController.iniciarSesion(documento, contrasenia);

        if (rol == null) {
            lblError.setText("Documento o contraseña incorrectos.");
            lblError.setVisible(true);
            return;
        }

        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        switch (rol) {
            case VISITANTE -> NavegadorUtil.irA(stage, "/view/PanelVisitanteView.fxml");
            case OPERADOR  -> NavegadorUtil.irA(stage, "/view/PanelOperadorView.fxml");
            case ADMINISTRADOR     -> NavegadorUtil.irA(stage, "/view/GestionVisitantesView.fxml");
        }
    }

    /**
     * Acción del enlace/botón "¿No tienes cuenta? Regístrate".
     */
    @FXML
    public void onIrARegistro() {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        NavegadorUtil.irA(stage, "/view/RegistroVisitanteView.fxml");
    }
}