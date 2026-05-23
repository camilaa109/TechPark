package co.edu.uniquindio.techpark.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.Operador;
import co.edu.uniquindio.techpark.model.Ticket;
import co.edu.uniquindio.techpark.model.Visitante;
import co.edu.uniquindio.techpark.model.Zona;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicioLectura {

    private static final ObjectMapper mapper = new ObjectMapper();


    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static List<Visitante> leerDatosVisitantes() {
        List<Visitante> listaVisitantes = new ArrayList<>();
        List<Ticket> listaTickets = new ArrayList<>();

        // 1. Leer el archivo de Visitantes
        try {
            File archivoVisitantes = new File("src//main//resources//data\\visitantedata.json");
            listaVisitantes = mapper.readValue(archivoVisitantes, new TypeReference<List<Visitante>>() {});
        } catch (IOException e) {
            System.err.println("Error al procesar el JSON de visitantes: " + e.getMessage());
            return new ArrayList<>(); // Retorna vacío si falla la base
        }

        // 2. Leer el archivo de Tickets
        try {
            File archivoTickets = new File("src//main//resources//data\\ticketdata.json");
            listaTickets = mapper.readValue(archivoTickets, new TypeReference<List<Ticket>>() {});
        } catch (IOException e) {
            System.err.println("Advertencia: No se pudo procesar el JSON de tickets o está vacío: " + e.getMessage());
            // Si fallan los tickets, al menos devolvemos los visitantes con listas vacías
            return listaVisitantes; 
        }

        // 3. Asociar los tickets con su respectivo visitante
        for (Ticket ticket : listaTickets) {
            String idBuscado = ticket.getIdVisitante();
            
            // Buscamos el visitante que tenga ese documento/ID
            for (Visitante visitante : listaVisitantes) {
                if (visitante.getDocumento() != null && visitante.getDocumento().equals(idBuscado)) {
                    
                    // Inicializamos la lista por si acaso llega a estar null en el JSON
                    if (visitante.getListaTickets() == null) {
                        visitante.setListaTickets(new ArrayList<>());
                    }
                    
                    visitante.getListaTickets().add(ticket);
                    break; // Saltamos al siguiente ticket ya que encontramos al dueño
                }
            }
        }

        return listaVisitantes;
    }

    /**
     * Lee el archivo JSON y lo convierte en una lista de objetos Operador.
     * @return List<Operador> cargada con los datos del JSON
     */
    public static List<Operador> leerDatosOperadores() {
        try {
            // Siguiendo tu misma estructura de rutas del proyecto
            File archivo = new File("src//main//resources//data\\operadordata.json");
            
            // Usamos TypeReference para mapear la lista genérica de Operadores
            return mapper.readValue(archivo, new TypeReference<List<Operador>>() {});
            
        } catch (IOException e) {
            System.err.println("Error al procesar el JSON de operadores: " + e.getMessage());
            // Retornamos una lista vacía para proteger el flujo del programa contra NullPointerException
            return new ArrayList<>();
        }
    }

    /**
     * Lee el archivo JSON y lo convierte en una lista de objetos Atraccion.
     * @return List<Atraccion> cargada con los datos del JSON (con listas de operadores vacías)
     */
    public static List<Atraccion> leerDatosAtracciones() {
        try {
            File archivo = new File("src//main//resources//data\\atracciondata.json");
            return mapper.readValue(archivo, new TypeReference<List<Atraccion>>() {});
        } catch (IOException e) {
            System.err.println("Error al procesar el JSON de atracciones: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lee el archivo JSON y lo convierte en una lista de objetos Zona.
     * @return List<Zona> cargada con los datos del JSON (con listas de atracciones y operadores vacías)
     */
    public static List<Zona> leerDatosZonas() {
        try {
            File archivo = new File("src//main//resources//data\\zonadata.json");
            return mapper.readValue(archivo, new TypeReference<List<Zona>>() {});
        } catch (IOException e) {
            System.err.println("Error al procesar el JSON de zonas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}