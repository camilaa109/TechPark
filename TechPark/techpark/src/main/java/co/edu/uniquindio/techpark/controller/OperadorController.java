package co.edu.uniquindio.techpark.controller;

import co.edu.uniquindio.techpark.model.Operador;
import co.edu.uniquindio.techpark.model.Parque;

/**
 * Controlador de Operadores (Lógica de Negocio).
 *
 * Cubre:
 * - Registro, obtención, actualización y eliminación de operadores.
 * - Asignación de operadores a atracciones.
 */
public class OperadorController {

    private final Parque parque;

    public OperadorController() {
        this.parque = ParqueController.getParque();
    }

    // =========================================================================
    // CRUD de Operadores
    // =========================================================================

    /**
     * Registra un nuevo operador en el parque.
     */
    public void registrarOperador(String nombre, String documento,
                                  int edad, String contrasenia) {
        parque.registrarOperador(nombre, documento, edad, contrasenia);
    }

    /**
     * Obtiene un operador por su documento.
     *
     * @return el Operador encontrado, o null si no existe.
     */
    public Operador obtenerOperador(String documento) {
        return parque.obtenerOperador(documento);
    }

    /**
     * Actualiza los datos de un operador.
     * El documento es inmutable (identificador único).
     */
    public void actualizarOperador(String nuevoNombre, String documento,
                                   int edad, String contrasenia) {
        parque.actualizarOperador(nuevoNombre, documento, edad, contrasenia);
    }

    /**
     * Elimina un operador del sistema.
     */
    public void eliminarOperador(String documento) {
        parque.eliminarOperador(documento);
    }

    // =========================================================================
    // Asignación de operadores a atracciones
    // =========================================================================

    /**
     * Asigna un operador a una atracción específica dentro de una zona.
     */
    public void asignarOperador(String documentoOperador,
                                String nombreZona, String nombreAtraccion) {
        parque.asignarOperador(documentoOperador, nombreZona, nombreAtraccion);
    }
}