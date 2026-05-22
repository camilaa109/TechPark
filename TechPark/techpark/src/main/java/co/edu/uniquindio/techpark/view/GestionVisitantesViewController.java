package co.edu.uniquindio.techpark.view;

import co.edu.uniquindio.techpark.controller.AlertaUtil;
import co.edu.uniquindio.techpark.controller.VisitanteController;
import co.edu.uniquindio.techpark.model.Visitante;
import javafx.fxml.FXML;

import java.util.List;

/**
 * ViewController de GestionVisitantes.fxml
 *
 * Vista del administrador para gestionar visitantes:
 *  - Listar todos los visitantes.
 *  - Ver detalle de un visitante seleccionado.
 *  - Actualizar datos (nombre, edad, estatura).
 *  - Eliminar visitante.
 */
public class GestionVisitantesViewController {

    private final VisitanteController visitanteController = new VisitanteController();

    // Visitante seleccionado actualmente en la tabla
    private Visitante visitanteSeleccionado;

    // =========================================================================
    // Campos de la vista — reemplazar cuando exista GestionVisitantes.fxml
    // =========================================================================

    // --- Tabla principal ---
    // TODO (Vista):
    // @FXML private TableView<Visitante>       tableVisitantes;
    // @FXML private TableColumn<Visitante, String> colDocumento;
    // @FXML private TableColumn<Visitante, String> colNombre;
    // @FXML private TableColumn<Visitante, Integer> colEdad;
    // @FXML private TableColumn<Visitante, Double>  colEstatura;

    // --- Formulario de detalle / edición ---
    // TODO (Vista):
    // @FXML private TextField txtNombre;
    // @FXML private TextField txtDocumento;   // solo lectura
    // @FXML private TextField txtEdad;
    // @FXML private TextField txtEstatura;

    // --- Botones ---
    // TODO (Vista):
    // @FXML private Button btnActualizar;
    // @FXML private Button btnEliminar;
    // @FXML private Button btnLimpiar;

    // --- Navegación admin ---
    // TODO (Vista):
    // @FXML private Button btnIrOperadores;
    // @FXML private Button btnIrZonas;
    // @FXML private Button btnCerrarSesion;

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
        // TODO (Vista): enlazar columnas con propiedades del modelo
        // colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        // colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        // colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        // colEstatura.setCellValueFactory(new PropertyValueFactory<>("estatura"));

        // Al seleccionar una fila, rellenar el formulario de edición
        // tableVisitantes.getSelectionModel().selectedItemProperty()
        //     .addListener((obs, old, seleccionado) -> {
        //         if (seleccionado != null) rellenarFormulario(seleccionado);
        //     });
    }

    /**
     * Carga (o recarga) la lista de visitantes en la tabla.
     */
    private void cargarVisitantes() {
        List<Visitante> lista = visitanteController.obtenerListaVisitantes();

        // TODO (Vista): poblar la tabla
        // tableVisitantes.setItems(FXCollections.observableArrayList(lista));
    }

    /**
     * Rellena el formulario de edición con los datos del visitante seleccionado.
     */
    private void rellenarFormulario(Visitante visitante) {
        visitanteSeleccionado = visitante;

        // TODO (Vista): setear los campos del formulario
        // txtNombre.setText(visitante.getNombre());
        // txtDocumento.setText(visitante.getDocumento());
        // txtEdad.setText(String.valueOf(visitante.getEdad()));
        // txtEstatura.setText(String.valueOf(visitante.getEstatura()));
    }

    // =========================================================================
    // Eventos
    // =========================================================================

    /**
     * Acción del botón "Actualizar".
     * Guarda los cambios del formulario en el visitante seleccionado.
     */
    @FXML
    public void onActualizar() {
        if (visitanteSeleccionado == null) {
            AlertaUtil.advertencia("Selecciona un visitante de la tabla primero.");
            return;
        }

        // TODO (Vista): leer valores del formulario
        // String nuevoNombre = txtNombre.getText().trim();
        // String edadTexto   = txtEdad.getText().trim();
        // String estaturaTexto = txtEstatura.getText().trim();
        String nuevoNombre   = ""; // reemplazar
        String edadTexto     = "0"; // reemplazar
        String estaturaTexto = "0"; // reemplazar

        int edad;
        double estatura;
        try {
            edad     = Integer.parseInt(edadTexto);
            estatura = Double.parseDouble(estaturaTexto);
        } catch (NumberFormatException e) {
            AlertaUtil.error("Edad y estatura deben ser valores numéricos.");
            return;
        }

        visitanteController.actualizarVisitante(
                nuevoNombre,
                visitanteSeleccionado.getDocumento(),
                edad,
                estatura);

        cargarVisitantes();
        limpiarFormulario();
        AlertaUtil.exito("Visitante actualizado correctamente.");
    }

    /**
     * Acción del botón "Eliminar".
     * Elimina el visitante seleccionado tras confirmación.
     */
    @FXML
    public void onEliminar() {
        if (visitanteSeleccionado == null) {
            AlertaUtil.advertencia("Selecciona un visitante de la tabla primero.");
            return;
        }

        boolean confirmar = AlertaUtil.confirmar(
                "¿Eliminar al visitante " + visitanteSeleccionado.getNombre() + "?");
        if (!confirmar) return;

        visitanteController.eliminarVisitante(visitanteSeleccionado.getDocumento());

        cargarVisitantes();
        limpiarFormulario();
    }

    /**
     * Acción del botón "Limpiar" — deselecciona y vacía el formulario.
     */
    @FXML
    public void onLimpiar() {
        limpiarFormulario();
        // TODO (Vista): deseleccionar la fila de la tabla
        // tableVisitantes.getSelectionModel().clearSelection();
    }

    private void limpiarFormulario() {
        visitanteSeleccionado = null;
        // TODO (Vista): vaciar los campos
        // txtNombre.clear();
        // txtDocumento.clear();
        // txtEdad.clear();
        // txtEstatura.clear();
    }

    // =========================================================================
    // Eventos — Navegación entre vistas de administrador
    // =========================================================================

    @FXML
    public void onIrAOperadores() {
        // TODO (Vista): navegar a GestionOperadores.fxml
        // NavegadorUtil.irA((Stage) btnIrOperadores.getScene().getWindow(),
        //     "/fxml/GestionOperadores.fxml");
    }

    @FXML
    public void onIrAZonas() {
        // TODO (Vista): navegar a GestionZonasAtracciones.fxml
        // NavegadorUtil.irA((Stage) btnIrZonas.getScene().getWindow(),
        //     "/fxml/GestionZonasAtracciones.fxml");
    }

    @FXML
    public void onCerrarSesion() {
        new co.edu.uniquindio.techpark.controller.ParqueController().cerrarSesion();
        // TODO (Vista): redirigir a InicioSesion.fxml
        // NavegadorUtil.irA((Stage) btnCerrarSesion.getScene().getWindow(),
        //     "/fxml/InicioSesion.fxml");
    }
}
