package co.edu.uniquindio.techpark.controller;

import co.edu.uniquindio.techpark.model.Operador;
import co.edu.uniquindio.techpark.model.Parque;

/**
 * Controlador de Operadores.
 *
 * Cubre:
 *  - Registro, obtención, actualización y eliminación de operadores.
 *  - Asignación de operadores a atracciones.
 */
public class OperadorController {

    private final Parque parque = ParqueController.getParque();

    // =========================================================================
    // CRUD de Operadores
    // =========================================================================

    /**
     * Registra un nuevo operador en el parque.
     */
    public void registrarOperador(String nombre, String documento,
                                  int edad, String contrasenia) {

        parque.registrarOperador(nombre, documento, edad, contrasenia);

        // TODO (Vista): mostrar confirmación y agregar la fila al TableView de operadores
        //   AlertaUtil.exito("Operador registrado correctamente");
        //   tableOperadores.getItems().add(parque.obtenerOperador(documento));
    }

    /**
     * Obtiene un operador por su documento.
     *
     * @return el Operador encontrado, o null si no existe.
     */
    public Operador obtenerOperador(String documento) {
        Operador operador = parque.obtenerOperador(documento);

        // TODO (Vista): rellenar los campos del formulario de detalle del operador
        //   txtNombreOp.setText(operador.getNombre()); etc.

        return operador;
    }

    /**
     * Actualiza los datos de un operador.
     * El documento es inmutable (identificador único).
     */
    public void actualizarOperador(String nuevoNombre, String documento,
                                   int edad, String contrasenia) {

        parque.actualizarOperador(nuevoNombre, documento, edad, contrasenia);

        // TODO (Vista): mostrar confirmación y refrescar la tabla
        //   AlertaUtil.exito("Operador actualizado");
        //   refrescarTablaOperadores();
    }

    /**
     * Elimina un operador del sistema.
     */
    public void eliminarOperador(String documento) {
        parque.eliminarOperador(documento);

        // TODO (Vista): quitar la fila de la tabla y mostrar confirmación
        //   tableOperadores.getItems().removeIf(op -> op.getDocumento().equals(documento));
        //   AlertaUtil.exito("Operador eliminado");
    }

    // =========================================================================
    // Asignación de operadores a atracciones
    // =========================================================================

    /**
     * Asigna un operador a una atracción específica dentro de una zona.
     * Actualiza tanto el operador (nombreAtraccionAsignada) como la atracción
     * (listaOperadoresAsignados).
     *
     * @param documentoOperador documento del operador a asignar.
     * @param nombreZona        zona donde se encuentra la atracción.
     * @param nombreAtraccion   nombre de la atracción destino.
     */
    public void asignarOperador(String documentoOperador,
                                String nombreZona, String nombreAtraccion) {

        parque.asignarOperador(documentoOperador, nombreZona, nombreAtraccion);

        // TODO (Vista): actualizar la etiqueta de atracción asignada en la ficha del operador
        //   lblAtraccionAsignada.setText(nombreAtraccion);
        //   AlertaUtil.exito("Operador asignado a " + nombreAtraccion);
    }
}