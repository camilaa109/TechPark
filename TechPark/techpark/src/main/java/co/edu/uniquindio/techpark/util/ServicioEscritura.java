package co.edu.uniquindio.techpark.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.edu.uniquindio.techpark.model.Visitante;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicioEscritura {

    private static final String RUTA_ARCHIVO = "src/main/resources/visitantes.json";
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); 
        mapper.enable(SerializationFeature.INDENT_OUTPUT); 
    }

    /**
     * Guarda una lista de visitantes en el archivo JSON. 
     * Si el archivo ya tiene datos, los lee primero y los combina.
     */
    public static void guardarVisitantes(List<Visitante> nuevosVisitantes) {
        try {
            File archivo = new File("src//main//resources\\visitantedata.json");
            List<Visitante> listaCompleta = new ArrayList<>();

            // 1. Verificar si el archivo existe para no perder lo que ya está guardado
            if (archivo.exists() && archivo.length() > 0) {
                // Usamos TypeReference para manejar correctamente el tipado de List<Visitante>
                List<Visitante> existentes = mapper.readValue(archivo, new TypeReference<List<Visitante>>() {});
                listaCompleta.addAll(existentes);
            }

            // 2. Agregar los nuevos a la lista total
            listaCompleta.addAll(nuevosVisitantes);

            // 3. Escribir de vuelta al archivo
            mapper.writeValue(archivo, listaCompleta);
            System.out.println("Sincronización con JSON exitosa.");

        } catch (IOException e) {
            System.err.println("Error en la persistencia estática: " + e.getMessage());
        }
    }

    /**
     * Método adicional estático para cargar todos los visitantes al iniciar el sistema
     */
    public static List<Visitante> cargarVisitantes() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(archivo, new TypeReference<List<Visitante>>() {});
        } catch (IOException e) {
            System.err.println("Error al cargar visitantes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
