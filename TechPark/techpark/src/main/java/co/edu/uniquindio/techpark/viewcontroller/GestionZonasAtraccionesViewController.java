package co.edu.uniquindio.techpark.viewcontroller; // Ajustado al paquete correcto de ViewControllers

import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.EstadoAtraccion;
import co.edu.uniquindio.techpark.model.TipoAtraccion;
import co.edu.uniquindio.techpark.model.Zona;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

/**
 * ViewController de GestionZonasAtracciones.fxml
 *
 * Vista del administrador para gestionar zonas y atracciones de manera desacoplada.
 */
public class GestionZonasAtraccionesViewController {

    private final AtraccionController atraccionController = new AtraccionController();

    // Atracción seleccionada en la tabla
    private Atraccion atraccionSeleccionada;
    private String    zonaDeAtraccionSeleccionada;

    // =========================================================================
    // Campos de la vista vinculados al archivo view
    // =========================================================================

    // --- Sección Zonas ---
    @FXML private TextField txtNombreZona;
    @FXML private Button    btnAgregarZona;

    // --- Filtro y Tabla de Atracciones ---
    @FXML private ComboBox<String>              comboZonas;
    @FXML private TableView<Atraccion>          tableAtracciones;
    @FXML private TableColumn<Atraccion, String> colNombreAtra;
    @FXML private TableColumn<Atraccion, String> colTipoAtra;
    @FXML private TableColumn<Atraccion, String> colEstadoAtra;
    @FXML private TableColumn<Atraccion, Integer> colEsperaAtra;

    // --- Formulario de registro de Atracciones ---
    @FXML private TextField           txtNombreAtra;
    @FXML private TextField           txtCapacidadAtra;
    @FXML private TextField           txtEdadMinAtra;
    @FXML private TextField           txtAlturaMinAtra;
    @FXML private TextField           txtCostoAtra;
    @FXML private TextField           txtTiempoAtra;
    @FXML private ComboBox<TipoAtraccion> comboTipoAtra;
    @FXML private Button              btnAgregarAtra;

    // --- Acciones de Estado ---
    @FXML private ComboBox<EstadoAtraccion> comboCambiarEstado;
    @FXML private TextField                 txtMotivoCierre;
    @FXML private Button                    btnCambiarEstado;
    @FXML private Button                    btnVerificarMantenimiento;

    // --- Alerta Climática ---
    @FXML private TextField txtMotivoClima;
    @FXML private Button    btnAlertaClimaticas;

    // --- Navegación admin ---
    @FXML private Button btnIrVisitantes;
    @FXML private Button btnIrOperadores;
    @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        configurarTablasYCombos();
        cargarZonas();
    }

    // =========================================================================
    // Configuración y carga de datos
    // =========================================================================

    private void configurarTablasYCombos() {
        // Enlace de las columnas con los atributos del modelo Atraccion
        colNombreAtra.setCellValueFactory(new PropertyValueFactory<>("nombreAtraccion"));
        colTipoAtra.setCellValueFactory(new PropertyValueFactory<>("tipoAtraccion"));
        colEstadoAtra.setCellValueFactory(new PropertyValueFactory<>("estadoAtraccion"));
        colEsperaAtra.setCellValueFactory(new PropertyValueFactory<>("tiempoEspera"));

        // Cargar los ENUMS en sus respectivos ComboBoxes de la UI
        comboTipoAtra.setItems(FXCollections.observableArrayList(TipoAtraccion.values()));
        comboCambiarEstado.setItems(FXCollections.observableArrayList(EstadoAtraccion.values()));

        // Listener: Al filtrar por Zona en el ComboBox superior, cargar sus atracciones
        comboZonas.valueProperty().addListener((obs, old, nuevaZona) -> {
            if (nuevaZona != null) {
                cargarAtracciones(nuevaZona);
            }
        });

        // Listener: Al seleccionar una atracción de la tabla, memorizarla para cambios de estado
        tableAtracciones.getSelectionModel().selectedItemProperty()
            .addListener((obs, old, seleccionada) -> {
                if (seleccionada != null) {
                    atraccionSeleccionada = seleccionada;
                    zonaDeAtraccionSeleccionada = comboZonas.getValue();
                    
                    // Pre-cargar el estado actual en el combo de edición manual
                    comboCambiarEstado.setValue(seleccionada.getEstadoAtraccion());
                }
            });
    }

    private void cargarZonas() {
        List<Zona> zonas = atraccionController.obtenerZonas();
        List<String> nombresZonas = zonas.stream().map(Zona::getNombreZona).toList();
        
        comboZonas.setItems(FXCollections.observableArrayList(nombresZonas));
    }

    private void cargarAtracciones(String nombreZona) {
        List<Zona> zonas = atraccionController.obtenerZonas();
        
        Zona zonaSeleccionada = zonas.stream()
                .filter(z -> z.getNombreZona().equals(nombreZona))
                .findFirst()
                .orElse(null);

        if (zonaSeleccionada != null) {
            tableAtracciones.setItems(FXCollections.observableArrayList(zonaSeleccionada.getListaAtracciones()));
        } else {
            tableAtracciones.getItems().clear();
        }
    }

    // =========================================================================
    // Eventos — Gestión de Zonas
    // =========================================================================

    @FXML
    public void onAgregarZona() {
        String nombreZona = txtNombreZona.getText().trim();

        if (nombreZona.isEmpty()) {
            AlertaUtil.error("El nombre de la zona no puede estar vacío.");
            return;
        }

        atraccionController.agregarZona(nombreZona);
        txtNombreZona.clear();
        cargarZonas();
        AlertaUtil.exito("Zona \"" + nombreZona + "\" creada exitosamente.");
    }

    // =========================================================================
    // Eventos — Gestión de Atracciones
    // =========================================================================

    @FXML
    public void onAgregarAtraccion() {
        String zonaSeleccionada = comboZonas.getValue();
        if (zonaSeleccionada == null || zonaSeleccionada.isEmpty()) {
            AlertaUtil.advertencia("Selecciona primero una zona en el filtro superior para añadir la atracción.");
            return;
        }

        String nombre = txtNombreAtra.getText().trim();
        TipoAtraccion tipo = comboTipoAtra.getValue();

        if (nombre.isEmpty() || tipo == null) {
            AlertaUtil.error("El nombre y el tipo de atracción son obligatorios.");
            return;
        }

        try {
            int capacidad     = Integer.parseInt(txtCapacidadAtra.getText().trim());
            int edadMin       = Integer.parseInt(txtEdadMinAtra.getText().trim());
            double alturaMin  = Double.parseDouble(txtAlturaMinAtra.getText().trim());
            double costoExtra = Double.parseDouble(txtCostoAtra.getText().trim());
            int tiempoEspera  = Integer.parseInt(txtTiempoAtra.getText().trim());

            atraccionController.agregarAtraccion(
                    nombre, capacidad, edadMin, alturaMin, costoExtra, tiempoEspera, tipo, zonaSeleccionada
            );

            cargarAtracciones(zonaSeleccionada);
            limpiarFormularioAtraccion();
            AlertaUtil.exito("Atracción \"" + nombre + "\" agregada.");

        } catch (NumberFormatException e) {
            AlertaUtil.error("Verifica los campos numéricos (capacidad, edad, altura, costo y tiempo).");
        }
    }

    private void limpiarFormularioAtraccion() {
        txtNombreAtra.clear();
        txtCapacidadAtra.clear();
        txtEdadMinAtra.clear();
        txtAlturaMinAtra.clear();
        txtCostoAtra.clear();
        txtTiempoAtra.clear();
        comboTipoAtra.setValue(null);
    }

    // =========================================================================
    // Eventos — Control de Estados y Mantenimiento
    // =========================================================================

    @FXML
    public void onCambiarEstadoManual() {
        if (atraccionSeleccionada == null || zonaDeAtraccionSeleccionada == null) {
            AlertaUtil.advertencia("Selecciona una atracción de la tabla primero.");
            return;
        }

        EstadoAtraccion nuevoEstado = comboCambiarEstado.getValue();
        String motivo = txtMotivoCierre.getText().trim();

        if (nuevoEstado == null) {
            AlertaUtil.error("Selecciona un nuevo estado.");
            return;
        }

        // Si pasa a mantenimiento, fuera de servicio o cerrada, opcionalmente exige motivo
        if (nuevoEstado != EstadoAtraccion.ACTIVA && motivo.isEmpty()) {
            AlertaUtil.advertencia("Es recomendable registrar un motivo para estados de cierre.");
        }

        atraccionController.cambiarEstadoAtraccion(
                zonaDeAtraccionSeleccionada, atraccionSeleccionada.getNombreAtraccion(), nuevoEstado, motivo
        );

        cargarAtracciones(zonaDeAtraccionSeleccionada);
        txtMotivoCierre.clear();
        tableAtracciones.getSelectionModel().clearSelection();
        AlertaUtil.exito("Estado actualizado manualmente.");
    }

    @FXML
    public void onVerificarMantenimientoAuto() {
        atraccionController.verificarMantenimiento();
        
        // Sincronizar la vista si hay algún filtro seleccionado
        if (comboZonas.getValue() != null) {
            cargarAtracciones(comboZonas.getValue());
        }
        AlertaUtil.exito("Verificación de mantenimiento completada automáticamente.");
    }

    // =========================================================================
    // Eventos — Alertas Globales
    // =========================================================================

    @FXML
    public void onActivarAlertaClimatica() {
        String motivo = txtMotivoClima.getText().trim();

        if (motivo.isEmpty()) {
            AlertaUtil.error("Por favor, ingresa el motivo climático (Ej: Tormenta Eléctrica).");
            return;
        }

        boolean confirmar = AlertaUtil.confirmar(
                "¿Activar alerta climática por \"" + motivo + "\"? Esto cerrará TODAS las atracciones activas.");
        if (!confirmar) return;

        atraccionController.activarAlertaClimatica(motivo);
        txtMotivoClima.clear();

        if (comboZonas.getValue() != null) {
            cargarAtracciones(comboZonas.getValue());
        }
        AlertaUtil.advertencia("Alerta climática activada. Todas las atracciones aplicables han sido cerradas.");
    }

    // =========================================================================
    // Eventos — Navegación
    // =========================================================================

    @FXML
    public void onIrAVisitantes() {
        NavegadorUtil.irA((Stage) btnIrVisitantes.getScene().getWindow(), "/view/GestionVisitantesView.fxml");
    }

    @FXML
    public void onIrAOperadores() {
        NavegadorUtil.irA((Stage) btnIrOperadores.getScene().getWindow(), "/view/GestionOperadoresView.fxml");
    }

    @FXML
    public void onCerrarSesion() {
        new ParqueController().cerrarSesion();
        NavegadorUtil.irA((Stage) btnCerrarSesion.getScene().getWindow(), "/view/InicioSesionView.fxml");
    }
}