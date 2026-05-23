package co.edu.uniquindio.techpark.viewcontroller; // Ajustado para consistencia con los ViewControllers

import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Visitante;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

/**
 * ViewController de GestionVisitantes.fxml
 *
 * Vista del administrador para gestionar visitantes de manera segura y desacoplada.
 */
public class GestionVisitantesViewController {

    private final VisitanteController visitanteController = new VisitanteController();

    // Visitante seleccionado actualmente en la tabla
    private Visitante visitanteSeleccionado;

    // =========================================================================
    // Campos de la vista vinculados al archivo FXML
    // =========================================================================

    // --- Tabla principal ---
    @FXML private TableView<Visitante>          tableVisitantes;
    @FXML private TableColumn<Visitante, String>  colDocumento;
    @FXML private TableColumn<Visitante, String>  colNombre;
    @FXML private TableColumn<Visitante, Integer> colEdad;
    @FXML private TableColumn<Visitante, Double>  colEstatura;

    // --- Formulario de detalle / edición ---
    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;   // Debe configurarse como editable=false en SceneBuilder
    @FXML private TextField txtEdad;
    @FXML private TextField txtEstatura;

    // --- Botones de acción ---
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    // --- Navegación del administrador ---
    @FXML private Button btnIrOperadores;
    @FXML private Button btnIrZonas;
    @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        configurarTabla();
        cargarVisitantes();
    }

    // =========================================================================
    // Configuración y carga de datos
    // =========================================================================

    /**
     * Configura las columnas de la tabla y el listener de selección.
     */
    private void configurarTabla() {
        // Enlace de columnas con los atributos correspondientes en el Modelo Visitante
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colEstatura.setCellValueFactory(new PropertyValueFactory<>("estatura"));

        // Listener: Al hacer clic sobre un visitante en la tabla, se autocompleta el formulario
        tableVisitantes.getSelectionModel().selectedItemProperty()
            .addListener((obs, old, seleccionado) -> {
                if (seleccionado != null) {
                    rellenarFormulario(seleccionado);
                }
            });
    }

    /**
     * Carga (o recarga) la lista de visitantes en la tabla de la interfaz.
     */
    private void cargarVisitantes() {
        List<Visitante> lista = visitanteController.obtenerListaVisitantes();
        tableVisitantes.setItems(FXCollections.observableArrayList(lista));
    }

    /**
     * Rellena el formulario de edición con los datos del visitante seleccionado.
     */
    private void rellenarFormulario(Visitante visitante) {
        visitanteSeleccionado = visitante;

        txtNombre.setText(visitante.getNombre());
        txtDocumento.setText(visitante.getDocumento());
        txtEdad.setText(String.valueOf(visitante.getEdad()));
        txtEstatura.setText(String.valueOf(visitante.getEstatura()));
    }

    // =========================================================================
    // Eventos del Formulario (CRUD)
    // =========================================================================

    /**
     * Acción del botón "Actualizar".
     * Modifica los datos del visitante seleccionado usando la lógica del negocio.
     */
    @FXML
    public void onActualizar() {
        if (visitanteSeleccionado == null) {
            AlertaUtil.advertencia("Selecciona un visitante de la tabla primero.");
            return;
        }

        // CORREGIDO: Captura real de lo digitado por el Administrador
        String nuevoNombre   = txtNombre.getText().trim();
        String edadTexto     = txtEdad.getText().trim();
        String estaturaTexto = txtEstatura.getText().trim();

        if (nuevoNombre.isEmpty() || edadTexto.isEmpty() || estaturaTexto.isEmpty()) {
            AlertaUtil.error("Todos los campos del formulario son obligatorios.");
            return;
        }

        int edad;
        double estatura;
        try {
            edad     = Integer.parseInt(edadTexto);
            estatura = Double.parseDouble(estaturaTexto);
        } catch (NumberFormatException e) {
            AlertaUtil.error("La edad debe ser entera y la estatura un valor decimal (ej: 1.75).");
            return;
        }

        // Invocación a la capa lógica pura
        visitanteController.actualizarVisitante(
                nuevoNombre,
                visitanteSeleccionado.getDocumento(),
                edad,
                estatura);

        cargarVisitantes();
        limpiarFormulario();
        tableVisitantes.getSelectionModel().clearSelection();
        AlertaUtil.exito("Visitante actualizado correctamente.");
    }

    /**
     * Acción del botón "Eliminar".
     */
    @FXML
    public void onEliminar() {
        if (visitanteSeleccionado == null) {
            AlertaUtil.advertencia("Selecciona un visitante de la tabla primero.");
            return;
        }

        boolean confirmar = AlertaUtil.confirmar(
                "¿Está seguro de eliminar al visitante " + visitanteSeleccionado.getNombre() + "?");
        if (!confirmar) return;

        // Mandamos a borrar en el modelo mediante el controlador intermedio
        visitanteController.eliminarVisitante(visitanteSeleccionado.getDocumento());

        cargarVisitantes();
        limpiarFormulario();
        tableVisitantes.getSelectionModel().clearSelection();
        AlertaUtil.exito("Visitante eliminado con éxito.");
    }

    /**
     * Acción del botón "Limpiar".
     */
    @FXML
    public void onLimpiar() {
        limpiarFormulario();
        tableVisitantes.getSelectionModel().clearSelection();
    }

    private void limpiarFormulario() {
        visitanteSeleccionado = null;
        txtNombre.clear();
        txtDocumento.clear();
        txtEdad.clear();
        txtEstatura.clear();
    }

    // =========================================================================
    // Eventos — Navegación entre vistas de administrador
    // =========================================================================

    @FXML
    public void onIrAOperadores() {
        NavegadorUtil.irA((Stage) btnIrOperadores.getScene().getWindow(),
            "/fxml/GestionOperadores.fxml");
    }

    @FXML
    public void onIrAZonas() {
        NavegadorUtil.irA((Stage) btnIrZonas.getScene().getWindow(),
            "/fxml/GestionZonasAtracciones.fxml");
    }

    @FXML
    public void onCerrarSesion() {
        new ParqueController().cerrarSesion();
        NavegadorUtil.irA((Stage) btnCerrarSesion.getScene().getWindow(),
            "/fxml/InicioSesion.fxml");
    }
}