package co.edu.uniquindio.techpark.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.edu.uniquindio.techpark.model.Visitante;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicioLectura {

    private static final ObjectMapper mapper = new ObjectMapper();


    static {
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Lee el archivo JSON y lo convierte en una lista de objetos Visitante.
     * @param rutaArchivo Nombre o ruta del archivo .json
     * @return List<Visitante> cargada con los datos del JSON
     */
    public static List<Visitante> leerDatosVisitantes() {
        try {
            File archivo = new File("src//main//resources\\visitantedata.json");
            
            // Usamos TypeReference porque vamos a leer una Lista genérica
            return mapper.readValue(archivo, new TypeReference<List<Visitante>>() {});
            
        } catch (IOException e) {
            System.err.println("Error al procesar el JSON de visitantes: " + e.getMessage());
            // Retornamos una lista vacía para evitar NullPointerException en el resto del programa
            return new ArrayList<>();
        }
    }
}