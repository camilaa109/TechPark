package co.edu.uniquindio.techpark.view;

import co.edu.uniquindio.techpark.controller.AlertaUtil;
import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.EstadoAtraccion;
import co.edu.uniquindio.techpark.model.TipoAtraccion;
import co.edu.uniquindio.techpark.model.Zona;
import javafx.fxml.FXML;

import java.util.List;

/**
 * ViewController de GestionZonasAtracciones.fxml
 *
 * Vista del administrador para gestionar zonas y atracciones:
 *  - Agregar zonas.
 *  - Agregar atracciones a una zona.
 *  - Ver lista de atracciones con su estado actual.
 *  - Cambiar estado manualmente.
 *  - Ejecutar verificación de mantenimiento automático.
 *  - Activar alerta climática global.
 */
public class GestionZonasAtraccionesViewController {

    private final AtraccionController atraccionController = new AtraccionController();

    // Atracción seleccionada en la tabla
    private Atraccion atraccionSeleccionada;
    private String    zonaDeAtraccionSeleccionada;

    // =========================================================================
    // Campos de la vista — reemplazar cuando exista GestionZonasAtracciones.fxml
    // =========================================================================

    // --- Sección Zonas ---
    // TODO (Vista):
    // @FXML private TextField        txtNombreZona;
    // @FXML private Button           btnAgregarZona;
    // @FXML private ComboBox<String> comboZonas;   // compartido con sección atracciones

    // --- Tabla de atracciones ---
    // TODO (Vista):
    // @FXML private TableView<Atraccion>                   tableAtracciones;
    // @FXML private TableColumn<Atraccion, String>         colNombreAt;
    // @FXML private TableColumn<Atraccion, EstadoAtraccion> colEstadoAt;
    // @FXML private TableColumn<Atraccion, Integer>        colCapacidadAt;
    // @FXML private TableColumn<Atraccion, Integer>        colAcumuladosAt;

    // --- Formulario nueva atracción ---
    // TODO (Vista):
    // @FXML private TextField          txtNombreAt;
    // @FXML private TextField          txtCapacidad;
    // @FXML private TextField          txtEdadMin;
    // @FXML private TextField          txtAlturaMin;
    // @FXML private TextField          txtCostoAdicional;
    // @FXML private TextField          txtTiempoEspera;
    // @FXML private ComboBox<TipoAtraccion> comboTipoAt;
    // @FXML private Button             btnAgregarAtraccion;

    // --- Sección gestión de estado ---
    // TODO (Vista):
    // @FXML private ComboBox<EstadoAtraccion> comboNuevoEstado;
    // @FXML private TextField                 txtMotivoEstado;
    // @FXML private Button                    btnCambiarEstado;

    // --- Acciones globales ---
    // TODO (Vista):
    // @FXML private Button    btnVerificarMantenimiento;
    // @FXML private TextField txtMotivoClimatico;
    // @FXML private Button    btnAlertaClimatica;

    // --- Navegación admin ---
    // TODO (Vista):
    // @FXML private Button btnIrVisitantes;
    // @FXML private Button btnIrOperadores;
    // @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        configurarTabla();
        cargarZonas();
        configurarCombos();
    }

    // =========================================================================
    // Configuración y carga de datos
    // =========================================================================

    private void configurarTabla() {
        // TODO (Vista): enlazar columnas con propiedades del modelo
        // colNombreAt.setCellValueFactory(new PropertyValueFactory<>("nombreAtraccion"));
        // colEstadoAt.setCellValueFactory(new PropertyValueFactory<>("estadoAtraccion"));
        // colCapacidadAt.setCellValueFactory(new PropertyValueFactory<>("capacidadMaxima"));
        // colAcumuladosAt.setCellValueFactory(new PropertyValueFactory<>("visitantesAcumulados"));

        // Al seleccionar una fila, guardar la atracción y su zona
        // tableAtracciones.getSelectionModel().selectedItemProperty()
        //     .addListener((obs, old, sel) -> {
        //         if (sel != null) {
        //             atraccionSeleccionada = sel;
        //             // buscar la zona de esta atracción
        //             zonaDeAtraccionSeleccionada = ParqueController.getParque()
        //                 .getListaZonas().stream()
        //                 .filter(z -> z.getListaAtracciones().contains(sel))
        //                 .map(Zona::getNombreZona)
        //                 .findFirst().orElse("");
        //         }
        //     });
    }

    private void cargarZonas() {
        List<Zona> zonas = atraccionController.obtenerZonas();

        // TODO (Vista): poblar el comboZonas
        // comboZonas.setItems(FXCollections.observableArrayList(
        //     zonas.stream().map(Zona::getNombreZona).toList()));

        // Al cambiar zona, recargar la tabla de atracciones
        // comboZonas.valueProperty().addListener((obs, old, nueva) -> cargarAtracciones(nueva));
    }

    private void cargarAtracciones(String nombreZona) {
        // TODO (Vista): filtrar atracciones de la zona y poblar la tabla
        // List<Atraccion> atracciones = ParqueController.getParque()
        //     .getListaZonas().stream()
        //     .filter(z -> z.getNombreZona().equals(nombreZona))
        //     .flatMap(z -> z.getListaAtracciones().stream())
        //     .toList();
        // tableAtracciones.setItems(FXCollections.observableArrayList(atracciones));
    }

    private void configurarCombos() {
        // TODO (Vista): poblar combo de tipo de atracción y estado
        // comboTipoAt.setItems(FXCollections.observableArrayList(TipoAtraccion.values()));
        // comboNuevoEstado.setItems(FXCollections.observableArrayList(EstadoAtraccion.values()));
    }

    // =========================================================================
    // Eventos — Zonas
    // =========================================================================

    /**
     * Acción del botón "Agregar Zona".
     */
    @FXML
    public void onAgregarZona() {

        // TODO (Vista): leer nombre de zona
        // String nombreZona = txtNombreZona.getText().trim();
        String nombreZona = ""; // reemplazar

        if (nombreZona.isEmpty()) {
            AlertaUtil.error("El nombre de la zona es obligatorio.");
            return;
        }

        atraccionController.agregarZona(nombreZona);
        cargarZonas();
        // TODO (Vista): limpiar el campo
        // txtNombreZona.clear();
        AlertaUtil.exito("Zona \"" + nombreZona + "\" creada.");
    }

    // =========================================================================
    // Eventos — Atracciones
    // =========================================================================

    /**
     * Acción del botón "Agregar Atracción".
     */
    @FXML
    public void onAgregarAtraccion() {

        // TODO (Vista): leer valores del formulario
        // String zona            = comboZonas.getValue();
        // String nombre          = txtNombreAt.getText().trim();
        // String capTexto        = txtCapacidad.getText().trim();
        // String edadMinTexto    = txtEdadMin.getText().trim();
        // String altMinTexto     = txtAlturaMin.getText().trim();
        // String costoTexto      = txtCostoAdicional.getText().trim();
        // String tiempoTexto     = txtTiempoEspera.getText().trim();
        // TipoAtraccion tipo     = comboTipoAt.getValue();
        String zona         = ""; // reemplazar
        String nombre       = ""; // reemplazar
        String capTexto     = "0"; // reemplazar
        String edadMinTexto = "0"; // reemplazar
        String altMinTexto  = "0"; // reemplazar
        String costoTexto   = "0"; // reemplazar
        String tiempoTexto  = "0"; // reemplazar
        TipoAtraccion tipo  = TipoAtraccion.MECANICA_ALTURA; // reemplazar

        if (zona.isEmpty() || nombre.isEmpty()) {
            AlertaUtil.error("Zona y nombre de atracción son obligatorios.");
            return;
        }

        int capacidad, edadMin, tiempoEspera;
        double alturaMin, costoAdicional;
        try {
            capacidad     = Integer.parseInt(capTexto);
            edadMin       = Integer.parseInt(edadMinTexto);
            tiempoEspera  = Integer.parseInt(tiempoTexto);
            alturaMin     = Double.parseDouble(altMinTexto);
            costoAdicional = Double.parseDouble(costoTexto);
        } catch (NumberFormatException e) {
            AlertaUtil.error("Verifica que los campos numéricos sean válidos.");
            return;
        }

        atraccionController.agregarAtraccion(nombre, capacidad, edadMin, alturaMin,
                costoAdicional, tiempoEspera, tipo, zona);

        cargarAtracciones(zona);
        AlertaUtil.exito("Atracción \"" + nombre + "\" agregada a " + zona + ".");
    }

    // =========================================================================
    // Eventos — Estado de atracción
    // =========================================================================

    /**
     * Acción del botón "Cambiar Estado" de la atracción seleccionada.
     */
    @FXML
    public void onCambiarEstado() {
        if (atraccionSeleccionada == null) {
            AlertaUtil.advertencia("Selecciona una atracción de la tabla primero.");
            return;
        }

        // TODO (Vista): leer estado y motivo
        // EstadoAtraccion nuevoEstado = comboNuevoEstado.getValue();
        // String motivo               = txtMotivoEstado.getText().trim();
        EstadoAtraccion nuevoEstado = EstadoAtraccion.ACTIVA; // reemplazar
        String motivo               = "";                      // reemplazar

        atraccionController.cambiarEstadoAtraccion(
                zonaDeAtraccionSeleccionada,
                atraccionSeleccionada.getNombreAtraccion(),
                nuevoEstado,
                motivo);

        cargarAtracciones(zonaDeAtraccionSeleccionada);
        AlertaUtil.exito("Estado actualizado a " + nuevoEstado + ".");
    }

    // =========================================================================
    // Eventos — Acciones globales
    // =========================================================================

    /**
     * Acción del botón "Verificar Mantenimiento".
     * Revisa todas las atracciones y cierra automáticamente las que superen
     * el umbral de visitantes acumulados.
     */
    @FXML
    public void onVerificarMantenimiento() {
        atraccionController.verificarMantenimiento();

        // TODO (Vista): refrescar la tabla con la zona actualmente seleccionada
        // cargarAtracciones(comboZonas.getValue());
        AlertaUtil.exito("Verificación completada. Revisa los estados actualizados.");
    }

    /**
     * Acción del botón "Activar Alerta Climática".
     * Cierra todas las atracciones activas y notifica a todos los visitantes.
     */
    @FXML
    public void onActivarAlertaClimatica() {

        // TODO (Vista): leer el motivo climático
        // String motivo = txtMotivoClimatico.getText().trim();
        String motivo = ""; // reemplazar

        if (motivo.isEmpty()) {
            AlertaUtil.error("Ingresa el motivo de la alerta climática.");
            return;
        }

        boolean confirmar = AlertaUtil.confirmar(
                "¿Activar alerta climática por \"" + motivo + "\"? "
                + "Esto cerrará TODAS las atracciones activas.");
        if (!confirmar) return;

        atraccionController.activarAlertaClimatica(motivo);

        // TODO (Vista): refrescar tabla de atracciones
        // cargarAtracciones(comboZonas.getValue());
        AlertaUtil.advertencia("Alerta climática activada. Todas las atracciones han sido cerradas.");
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
    public void onIrAOperadores() {
        // TODO (Vista): navegar a GestionOperadores.fxml
        // NavegadorUtil.irA((Stage) btnIrOperadores.getScene().getWindow(),
        //     "/fxml/GestionOperadores.fxml");
    }

    @FXML
    public void onCerrarSesion() {
        new ParqueController().cerrarSesion();
        // TODO (Vista): redirigir a InicioSesion.fxml
        // NavegadorUtil.irA((Stage) btnCerrarSesion.getScene().getWindow(),
        //     "/fxml/InicioSesion.fxml");
    }
}
