package co.edu.uniquindio.techpark.viewcontroller;

import co.edu.uniquindio.techpark.controller.AtraccionController;
import co.edu.uniquindio.techpark.controller.OperadorController;
import co.edu.uniquindio.techpark.controller.ParqueController;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.EstadoAtraccion;
import co.edu.uniquindio.techpark.model.Operador;
import co.edu.uniquindio.techpark.model.Zona;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * ViewController de PanelOperador.fxml
 *
 * Vista principal del operador enlazada de forma segura mediante el patrón MVC.
 */
public class PanelOperadorViewController {

    private final OperadorController  operadorController  = new OperadorController();
    private final AtraccionController atraccionController = new AtraccionController();

    private final String documentoActivo = ParqueController.getDocumentoSesionActiva();

    // Datos de sesión cargados en memoria interna
    private Operador  operadorActivo;
    private String    nombreZonaAsignada;     
    private Atraccion atraccionAsignada;

    // =========================================================================
    // Campos de la vista vinculados al archivo FXML
    // =========================================================================

    // --- Ficha Perfil del Operador ---
    @FXML private Label lblNombreOperador;
    @FXML private Label lblDocumentoOperador;
    @FXML private Label lblAtraccionAsignada;

    // --- Contenedor de Gestión y Ficha Técnica de Atracción ---
    @FXML private VBox  panelGestionAtraccion; // Se oculta o muestra si tiene asignación
    @FXML private Label lblNombreAtraccion;
    @FXML private Label lblEstadoAtraccion;
    @FXML private Label lblCapacidadAtraccion;
    @FXML private Label lblTamanioCola;

    // --- Formulario de Apertura / Cierre ---
    @FXML private TextField txtMotivoCierre;
    @FXML private Button    btnAbrirAtraccion;
    @FXML private Button    btnCerrarAtraccion;

    // --- Acciones de Operación ---
    @FXML private Button btnRealizarCiclo;
    @FXML private Button btnRegistrarRevision;

    // --- Sesión ---
    @FXML private Button btnCerrarSesion;

    // =========================================================================
    // Inicialización
    // =========================================================================

    @FXML
    public void initialize() {
        cargarDatosSesion();
        configurarInterfazInicial();
    }

    // =========================================================================
    // Carga de datos y sincronización de UI
    // =========================================================================

    /**
     * Identifica al operador logueado y localiza qué atracción tiene a cargo.
     */
    private void cargarDatosSesion() {
        if (documentoActivo != null) {
            this.operadorActivo = operadorController.obtenerOperador(documentoActivo);
            
            if (operadorActivo != null && operadorActivo.getNombreAtraccionAsignada() != null) {
                String nombreAtraccion = operadorActivo.getNombreAtraccionAsignada();
                
                // Buscamos la zona correspondiente de forma desacoplada
                List<Zona> zonas = atraccionController.obtenerZonas();
                for (Zona zona : zonas) {
                    Atraccion encontrada = zona.getListaAtracciones().stream()
                            .filter(a -> a.getNombreAtraccion().equals(nombreAtraccion))
                            .findFirst()
                            .orElse(null);
                    
                    if (encontrada != null) {
                        this.nombreZonaAsignada = zona.getNombreZona();
                        this.atraccionAsignada = encontrada;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Muestra la información en las etiquetas correspondientes y oculta paneles si no hay asignación.
     */
    private void configurarInterfazInicial() {
        if (operadorActivo == null) {
            AlertaUtil.error("Error crítico: Sesión inconsistente.");
            return;
        }

        // Poblar datos del perfil del empleado
        lblNombreOperador.setText("Operador: " + operadorActivo.getNombre());
        lblDocumentoOperador.setText("Documento: " + operadorActivo.getDocumento());

        if (atraccionAsignada != null) {
            lblAtraccionAsignada.setText("Asignado a: " + atraccionAsignada.getNombreAtraccion());
            panelGestionAtraccion.setVisible(true);
            actualizarFichaTecnica();
        } else {
            lblAtraccionAsignada.setText("Asignado a: Ninguna Atracción");
            panelGestionAtraccion.setVisible(false); // Oculta el panel si el Admin no le ha puesto trabajo
        }
    }

    /**
     * Sincroniza dinámicamente las etiquetas con los valores vivos del Modelo.
     */
    private void actualizarFichaTecnica() {
        if (atraccionAsignada == null) return;

        lblNombreAtraccion.setText(atraccionAsignada.getNombreAtraccion());
        lblEstadoAtraccion.setText("Estado: " + atraccionAsignada.getEstadoAtraccion());
        lblCapacidadAtraccion.setText("Capacidad máxima: " + atraccionAsignada.getCapacidadMaxima() + " pers.");
        
        // Manejo seguro del tamaño de la cola virtual
        if (atraccionAsignada.getColaVirtual() != null) {
            lblTamanioCola.setText("Visitantes en cola: " + atraccionAsignada.getColaVirtual().size());
        } else {
            lblTamanioCola.setText("Visitantes en cola: 0");
        }

        // Habilitar o deshabilitar botones visuales según el estado real de la atracción
        boolean esMantenimiento = (atraccionAsignada.getEstadoAtraccion() == EstadoAtraccion.EN_MANTENIMIENTO);
        btnRegistrarRevision.setDisable(!esMantenimiento);
        btnRealizarCiclo.setDisable(atraccionAsignada.getEstadoAtraccion() != EstadoAtraccion.ACTIVA);
    }

    // =========================================================================
    // Eventos — Control de Flujo de la Atracción
    // =========================================================================

    /**
     * Acción del botón "Abrir Atracción".
     * Pone la atracción en estado ACTIVA.
     */
    @FXML
    public void onAbrirAtraccion() {
        if (atraccionAsignada == null) return;

        atraccionController.cambiarEstadoAtraccion(
                nombreZonaAsignada,
                atraccionAsignada.getNombreAtraccion(),
                EstadoAtraccion.ACTIVA,
                ""
        );

        txtMotivoCierre.clear();
        actualizarFichaTecnica();
        AlertaUtil.exito("La atracción ahora se encuentra ACTIVA para el público.");
    }

    /**
     * Acción del botón "Cerrar Atracción".
     * Cambia el estado a CERRADA requiriendo un motivo de justificación.
     */
    @FXML
    public void onCerrarAtraccion() {
        if (atraccionAsignada == null) return;

        String motivo = txtMotivoCierre.getText().trim();

        if (motivo.isEmpty()) {
            AlertaUtil.advertencia("Por favor, escriba el motivo del cierre de la atracción.");
            return;
        }

        atraccionController.cambiarEstadoAtraccion(
                nombreZonaAsignada,
                atraccionAsignada.getNombreAtraccion(),
                EstadoAtraccion.CERRADA,
                motivo
        );

        actualizarFichaTecnica();
        AlertaUtil.exito("Atracción guardada en estado CERRADA.");
    }

    /**
     * Acción del botón "Realizar Ciclo".
     * Despacha el juego, evacúa la cola y envía alertas automáticas a los clientes.
     */
    @FXML
    public void onRealizarCiclo() {
        if (atraccionAsignada == null) return;

        // Se invoca la ejecución en el core a través del controlador intermedio
        atraccionController.realizarCicloAtraccion(
                nombreZonaAsignada,
                operadorActivo.getNombreAtraccionAsignada()
        );

        actualizarFichaTecnica(); // Refresca el tamaño de la cola virtual reducido
        AlertaUtil.exito("Ciclo realizado. Los visitantes del turno actual fueron notificados.");
    }

    /**
     * Acción del botón "Registrar Revisión".
     * Libera la atracción del estado de mantenimiento preventivo.
     */
    @FXML
    public void onRegistrarRevision() {
        if (atraccionAsignada == null) return;

        boolean confirmar = AlertaUtil.confirmar(
                "¿Confirmas que la atracción ya fue inspeccionada mecánicamente y está lista para operar?");
        if (!confirmar) return;

        atraccionController.registrarRevision(
                nombreZonaAsignada,
                operadorActivo.getNombreAtraccionAsignada()
        );

        actualizarFichaTecnica();
        AlertaUtil.exito("Revisión completada. Estado restablecido.");
    }

    // =========================================================================
    // Eventos — Cierre de Sesión de Usuario
    // =========================================================================

    /**
     * Acción del botón "Cerrar Sesión".
     */
    @FXML
    public void onCerrarSesion() {
        new ParqueController().cerrarSesion();
        NavegadorUtil.irA((Stage) btnCerrarSesion.getScene().getWindow(), "/view/InicioSesionView.fxml");
    }
}