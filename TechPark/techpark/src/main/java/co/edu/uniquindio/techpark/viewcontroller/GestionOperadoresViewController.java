package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.OperadorController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.Operador;
import co.edu.uniquindio.techpark.model.Zona;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

/**
 * ViewController de GestionOperadores.fxml
 *
 * Vista del administrador para gestionar operadores.
 */
public class GestionOperadoresViewController {

    private final OperadorController  operadorController  = new OperadorController();
    private final AtraccionController atraccionController = new AtraccionController();

    // Operador seleccionado en la tabla
    private Operador operadorSeleccionado;

    // =========================================================================
    // Campos de la vista enlazados al FXML
    // =========================================================================

    // --- Tabla de operadores ---
    @FXML private TableView<Operador>              tableOperadores;
    @FXML private TableColumn<Operador, String>    colDocOp;
    @FXML private TableColumn<Operador, String>    colNombreOp;
    @FXML private TableColumn<Operador, Integer>   colEdadOp;
    @FXML private TableColumn<Operador, String>    colAtraccionOp;

    // --- Formulario de registro / edición ---
    @FXML private TextField     txtNombreOp;
    @FXML private TextField     txtDocumentoOp;
    @FXML private TextField     txtEdadOp;
    @FXML private PasswordField txtContraseniaOp;

    // --- Sección de asignación ---
    @FXML private ComboBox<String> comboZonaAsig;
    @FXML private ComboBox<String> comboAtraccionAsig;
    @FXML private Button           btnAsignar;

    // --- Botones CRUD ---
    @FXML private Button btnRegistrarOp;
    @FXML private Button btnActualizarOp;
    @FXML private Button btnEliminarOp;
    @FXML private Button btnLimpiarOp;

    // --- Navegación admin ---
    @FXML private Button btnIrVisitantes;
    @FXML private Button btnIrZonas;
    @FXML private Button btnCerrarSesion;

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
        // Enlace de las columnas con los atributos del modelo Operador
        colDocOp.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colNombreOp.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdadOp.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colAtraccionOp.setCellValueFactory(new PropertyValueFactory<>("nombreAtraccionAsignada"));

        // Evento: Al seleccionar una fila, rellenar el formulario automáticamente
        tableOperadores.getSelectionModel().selectedItemProperty()
            .addListener((obs, old, sel) -> { 
                if (sel != null) {
                    rellenarFormulario(sel); 
                }
            });
    }

    private void cargarOperadores() {
        // CORREGIDO: Se obtiene la lista de operadores de manera segura a través de la instancia del parque
        List<Operador> lista = ParqueController.getParque().obtenerListaOperadores();
        tableOperadores.setItems(FXCollections.observableArrayList(lista));
    }

    /**
     * Carga las zonas en el ComboBox de asignación.
     */
    private void cargarZonas() {
        List<Zona> zonas = atraccionController.obtenerZonas();

        comboZonaAsig.setItems(FXCollections.observableArrayList(
            zonas.stream().map(Zona::getNombreZona).toList()
        ));

        // Listener: Al cambiar la zona, cambia dinámicamente el combo de atracciones
        comboZonaAsig.valueProperty().addListener((obs, old, nueva) -> {
            if (nueva != null) {
                cargarAtracciones(nueva);
            }
        });
    }

    private void cargarAtracciones(String nombreZona) {
        // CORREGIDO: Para mantener desacoplamiento, usamos las zonas de atraccionController
        List<Zona> zonas = atraccionController.obtenerZonas();
        
        List<String> nombres = zonas.stream()
            .filter(z -> z.getNombreZona().equals(nombreZona))
            .flatMap(z -> z.getListaAtracciones().stream())
            .map(Atraccion::getNombreAtraccion)
            .toList();
            
        comboAtraccionAsig.setItems(FXCollections.observableArrayList(nombres));
    }

    private void rellenarFormulario(Operador operador) {
        operadorSeleccionado = operador;

        txtNombreOp.setText(operador.getNombre());
        txtDocumentoOp.setText(operador.getDocumento());
        txtEdadOp.setText(String.valueOf(operador.getEdad()));
        txtContraseniaOp.setText(operador.getContrasenia());
    }

    // =========================================================================
    // Eventos — CRUD
    // =========================================================================

    /**
     * Acción del botón "Registrar Operador".
     */
    @FXML
    public void onRegistrar() {
        String nombre      = txtNombreOp.getText().trim();
        String documento   = txtDocumentoOp.getText().trim();
        String edadTexto   = txtEdadOp.getText().trim();
        String contrasenia = txtContraseniaOp.getText();

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

        String nuevoNombre = txtNombreOp.getText().trim();
        String edadTexto   = txtEdadOp.getText().trim();
        String contrasenia = txtContraseniaOp.getText();

        if (nuevoNombre.isEmpty() || edadTexto.isEmpty() || contrasenia.isEmpty()) {
            AlertaUtil.error("Todos los campos son obligatorios para actualizar.");
            return;
        }

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
        AlertaUtil.exito("Operador eliminado de forma exitosa.");
    }

    /**
     * Acción del botón "Limpiar".
     */
    @FXML
    public void onLimpiar() {
        limpiarFormulario();
        tableOperadores.getSelectionModel().clearSelection();
    }

    private void limpiarFormulario() {
        operadorSeleccionado = null;
        txtNombreOp.clear(); 
        txtDocumentoOp.clear();
        txtEdadOp.clear();   
        txtContraseniaOp.clear();
    }

    // =========================================================================
    // Eventos — Asignación
    // =========================================================================

    /**
     * Acción del botón "Asignar Operador".
     */
    @FXML
    public void onAsignar() {
        if (operadorSeleccionado == null) {
            AlertaUtil.advertencia("Selecciona un operador de la tabla primero.");
            return;
        }

        String zona      = comboZonaAsig.getValue();
        String atraccion = comboAtraccionAsig.getValue();

        // CORREGIDO: Validación segura contra nulos en ComboBoxes
        if (zona == null || atraccion == null || zona.isEmpty() || atraccion.isEmpty()) {
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
        NavegadorUtil.irA((Stage) btnIrVisitantes.getScene().getWindow(),
            "/fxml/GestionVisitantes.fxml");
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