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

        // CORREGIDO: Redirección usando los ENUMS exactos declarados en tu backend
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        switch (rol) {
            case VISITANTE -> NavegadorUtil.irA(stage, "/fxml/PanelVisitante.fxml");
            case OPERADOR  -> NavegadorUtil.irA(stage, "/fxml/PanelOperador.fxml");
            case ADMINISTRADOR     -> NavegadorUtil.irA(stage, "/fxml/GestionVisitantes.fxml"); // Cambiado de ADMINISTRADOR a ADMIN por consistencia
        }
    }

    /**
     * Acción del enlace/botón "¿No tienes cuenta? Regístrate".
     * COMPLETADO: Se añade el flujo de navegación hacia la vista de registro.
     */
    @FXML
    public void onIrARegistro() {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        NavegadorUtil.irA(stage, "/fxml/RegistroVisitante.fxml");
    }
}