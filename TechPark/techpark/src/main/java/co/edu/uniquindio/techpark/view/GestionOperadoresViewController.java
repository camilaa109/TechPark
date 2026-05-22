package co.edu.uniquindio.techpark.view;

import co.edu.uniquindio.techpark.controller.AlertaUtil;
import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.OperadorController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.Operador;
import co.edu.uniquindio.techpark.model.Zona;
import javafx.fxml.FXML;

import java.util.List;

/**
 * ViewController de GestionOperadores.fxml
 *
 * Vista del administrador para gestionar operadores:
 *  - Registrar nuevo operador.
 *  - Listar, actualizar y eliminar operadores.
 *  - Asignar operador a una atracción específica.
 */
public class GestionOperadoresViewController {

    private final OperadorController  operadorController  = new OperadorController();
    private final AtraccionController atraccionController = new AtraccionController();

    // Operador seleccionado en la tabla
    private Operador operadorSeleccionado;

    // =========================================================================
    // Campos de la vista — reemplazar cuando exista GestionOperadores.fxml
    // =========================================================================

    // --- Tabla de operadores ---
    // TODO (Vista):
    // @FXML private TableView<Operador>              tableOperadores;
    // @FXML private TableColumn<Operador, String>    colDocOp;
    // @FXML private TableColumn<Operador, String>    colNombreOp;
    // @FXML private TableColumn<Operador, Integer>   colEdadOp;
    // @FXML private TableColumn<Operador, String>    colAtraccionOp;

    // --- Formulario de registro / edición ---
    // TODO (Vista):
    // @FXML private TextField     txtNombreOp;
    // @FXML private TextField     txtDocumentoOp;
    // @FXML private TextField     txtEdadOp;
    // @FXML private PasswordField txtContraseniaOp;

    // --- Sección de asignación ---
    // TODO (Vista):
    // @FXML private ComboBox<String> comboZonaAsig;
    // @FXML private ComboBox<String> comboAtraccionAsig;
    // @FXML private Button           btnAsignar;

    // --- Botones CRUD ---
    // TODO (Vista):
    // @FXML private Button btnRegistrarOp;
    // @FXML private Button btnActualizarOp;
    // @FXML private Button btnEliminarOp;
    // @FXML private Button btnLimpiarOp;

    // --- Navegación admin ---
    // TODO (Vista):
    // @FXML private Button btnIrVisitantes;
    // @FXML private Button btnIrZonas;
    // @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        configurarTabla();
        cargarOperadores();
        cargarZonas();
    }

    // =========================================================================
    // Configuración y carga de datos
    // =========================================================================

    private void configurarTabla() {
        // TODO (Vista): enlazar columnas con propiedades del modelo
        // colDocOp.setCellValueFactory(new PropertyValueFactory<>("documento"));
        // colNombreOp.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        // colEdadOp.setCellValueFactory(new PropertyValueFactory<>("edad"));
        // colAtraccionOp.setCellValueFactory(new PropertyValueFactory<>("nombreAtraccionAsignada"));

        // Al seleccionar una fila, rellenar el formulario
        // tableOperadores.getSelectionModel().selectedItemProperty()
        //     .addListener((obs, old, sel) -> { if (sel != null) rellenarFormulario(sel); });
    }

    private void cargarOperadores() {
        // TODO (Vista): obtener lista y poblar la tabla
        // La lista de operadores debe estar expuesta en Parque o OperadorController
        // tableOperadores.setItems(FXCollections.observableArrayList(
        //     ParqueController.getParque().getListaOperadores()));
    }

    /**
     * Carga las zonas en el ComboBox de asignación.
     */
    private void cargarZonas() {
        List<Zona> zonas = atraccionController.obtenerZonas();

        // TODO (Vista): poblar comboZonaAsig
        // comboZonaAsig.setItems(FXCollections.observableArrayList(
        //     zonas.stream().map(Zona::getNombreZona).toList()));

        // Al cambiar la zona, recargar el combo de atracciones
        // comboZonaAsig.valueProperty().addListener((obs, old, nueva) -> cargarAtracciones(nueva));
    }

    private void cargarAtracciones(String nombreZona) {
        // TODO (Vista): poblar comboAtraccionAsig con las atracciones de la zona
        // List<String> nombres = ParqueController.getParque().getListaZonas().stream()
        //     .filter(z -> z.getNombreZona().equals(nombreZona))
        //     .flatMap(z -> z.getListaAtracciones().stream())
        //     .map(Atraccion::getNombreAtraccion)
        //     .toList();
        // comboAtraccionAsig.setItems(FXCollections.observableArrayList(nombres));
    }

    private void rellenarFormulario(Operador operador) {
        operadorSeleccionado = operador;

        // TODO (Vista): setear campos del formulario
        // txtNombreOp.setText(operador.getNombre());
        // txtDocumentoOp.setText(operador.getDocumento());
        // txtEdadOp.setText(String.valueOf(operador.getEdad()));
        // txtContraseniaOp.setText(operador.getContrasenia());
    }

    // =========================================================================
    // Eventos — CRUD
    // =========================================================================

    /**
     * Acción del botón "Registrar Operador".
     */
    @FXML
    public void onRegistrar() {

        // TODO (Vista): leer campos del formulario
        // String nombre      = txtNombreOp.getText().trim();
        // String documento   = txtDocumentoOp.getText().trim();
        // String edadTexto   = txtEdadOp.getText().trim();
        // String contrasenia = txtContraseniaOp.getText();
        String nombre      = ""; // reemplazar
        String documento   = ""; // reemplazar
        String edadTexto   = "0"; // reemplazar
        String contrasenia = ""; // reemplazar

        if (nombre.isEmpty() || documento.isEmpty() || contrasenia.isEmpty()) {
            AlertaUtil.error("Nombre, documento y contraseña son obligatorios.");
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadTexto);
        } catch (NumberFormatException e) {
            AlertaUtil.error("La edad debe ser un número entero.");
            return;
        }

        operadorController.registrarOperador(nombre, documento, edad, contrasenia);
        cargarOperadores();
        limpiarFormulario();
        AlertaUtil.exito("Operador registrado correctamente.");
    }

    /**
     * Acción del botón "Actualizar Operador".
     */
    @FXML
    public void onActualizar() {
        if (operadorSeleccionado == null) {
            AlertaUtil.advertencia("Selecciona un operador de la tabla primero.");
            return;
        }

        // TODO (Vista): leer campos del formulario
        // String nuevoNombre = txtNombreOp.getText().trim();
        // String edadTexto   = txtEdadOp.getText().trim();
        // String contrasenia = txtContraseniaOp.getText();
        String nuevoNombre = ""; // reemplazar
        String edadTexto   = "0"; // reemplazar
        String contrasenia = ""; // reemplazar

        int edad;
        try {
            edad = Integer.parseInt(edadTexto);
        } catch (NumberFormatException e) {
            AlertaUtil.error("La edad debe ser un número entero.");
            return;
        }

        operadorController.actualizarOperador(
                nuevoNombre,
                operadorSeleccionado.getDocumento(),
                edad,
                contrasenia);

        cargarOperadores();
        limpiarFormulario();
        AlertaUtil.exito("Operador actualizado correctamente.");
    }

    /**
     * Acción del botón "Eliminar Operador".
     */
    @FXML
    public void onEliminar() {
        if (operadorSeleccionado == null) {
            AlertaUtil.advertencia("Selecciona un operador de la tabla primero.");
            return;
        }

        boolean confirmar = AlertaUtil.confirmar(
                "¿Eliminar al operador " + operadorSeleccionado.getNombre() + "?");
        if (!confirmar) return;

        operadorController.eliminarOperador(operadorSeleccionado.getDocumento());
        cargarOperadores();
        limpiarFormulario();
    }

    /**
     * Acción del botón "Limpiar".
     */
    @FXML
    public void onLimpiar() {
        limpiarFormulario();
        // TODO (Vista): deseleccionar fila
        // tableOperadores.getSelectionModel().clearSelection();
    }

    private void limpiarFormulario() {
        operadorSeleccionado = null;
        // TODO (Vista): vaciar campos
        // txtNombreOp.clear(); txtDocumentoOp.clear();
        // txtEdadOp.clear();   txtContraseniaOp.clear();
    }

    // =========================================================================
    // Eventos — Asignación
    // =========================================================================

    /**
     * Acción del botón "Asignar Operador".
     * Asigna el operador seleccionado a la atracción elegida en los combos.
     */
    @FXML
    public void onAsignar() {
        if (operadorSeleccionado == null) {
            AlertaUtil.advertencia("Selecciona un operador de la tabla primero.");
            return;
        }

        // TODO (Vista): leer zona y atracción seleccionadas
        // String zona      = comboZonaAsig.getValue();
        // String atraccion = comboAtraccionAsig.getValue();
        String zona      = ""; // reemplazar
        String atraccion = ""; // reemplazar

        if (zona.isEmpty() || atraccion.isEmpty()) {
            AlertaUtil.advertencia("Selecciona una zona y una atracción para asignar.");
            return;
        }

        operadorController.asignarOperador(operadorSeleccionado.getDocumento(), zona, atraccion);
        cargarOperadores();
        AlertaUtil.exito("Operador asignado a " + atraccion + ".");
    }

    // =========================================================================
    // Eventos — Navegación entre vistas de administrador
    // =========================================================================

    @FXML
    public void onIrAVisitantes() {
        // TODO (Vista): navegar a GestionVisitantes.fxml
        // NavegadorUtil.irA((Stage) btnIrVisitantes.getScene().getWindow(),
        //     "/fxml/GestionVisitantes.fxml");
    }

    @FXML
    public void onIrAZonas() {
        // TODO (Vista): navegar a GestionZonasAtracciones.fxml
        // NavegadorUtil.irA((Stage) btnIrZonas.getScene().getWindow(),
        //     "/fxml/GestionZonasAtracciones.fxml");
    }

    @FXML
    public void onCerrarSesion() {
        new ParqueController().cerrarSesion();
        // TODO (Vista): redirigir a InicioSesion.fxml
        // NavegadorUtil.irA((Stage) btnCerrarSesion.getScene().getWindow(),
        //     "/fxml/InicioSesion.fxml");
    }
}
