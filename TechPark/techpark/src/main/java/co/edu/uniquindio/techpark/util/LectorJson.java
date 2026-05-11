package co.edu.uniquindio.techpark.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class LectorJson {

    private final ObjectMapper mapper;

    public LectorJson() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Lee un archivo JSON y lo convierte en un objeto de la clase especificada.
     * @param rutaArchivo Ruta del archivo .json
     * @param claseDestino La clase Java a la que se mapearán los datos
     * @return Instancia de la clase con los datos cargados
     */
    public <T> T leerDesdeArchivo(String rutaArchivo, Class<T> claseDestino) {
        try {
            return mapper.readValue(new File(rutaArchivo), claseDestino);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return null;
        }
    }
}