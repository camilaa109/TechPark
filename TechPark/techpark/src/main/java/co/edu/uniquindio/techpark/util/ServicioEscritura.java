package co.edu.uniquindio.techpark.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.edu.uniquindio.techpark.model.Visitante;
import co.edu.uniquindio.techpark.model.Operador;
import co.edu.uniquindio.techpark.model.Ticket;
import co.edu.uniquindio.techpark.model.Atraccion;
import co.edu.uniquindio.techpark.model.Zona;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicioEscritura {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); 
        mapper.enable(SerializationFeature.INDENT_OUTPUT); 
    }

    /**
     * Guarda la lista de visitantes y, de forma automática, extrae y guarda 
     * todos los tickets asociados a ellos en su respectivo archivo JSON.
     * @param visitantes Lista con todos los visitantes de la aplicación.
     */
    public static void guardarVisitantes(List<Visitante> visitantes) {
        try {
            File archivoVisitantes = new File("src//main//resources//data\\visitantedata.json");
            List<Visitante> listaAsegurada = visitantes != null ? visitantes : new ArrayList<>();
            
            // 1. Guardar la lista de visitantes en su JSON (sobrescribe por completo)
            mapper.writeValue(archivoVisitantes, listaAsegurada);
            System.out.println("Persistencia de visitantes guardada con éxito.");

            // 2. EXTRAER AUTOMÁTICAMENTE LOS TICKETS
            List<Ticket> todosLosTickets = new ArrayList<>();
            for (Visitante v : listaAsegurada) {
                if (v.getListaTickets() != null) {
                    todosLosTickets.addAll(v.getListaTickets());
                }
            }

            // 3. Guardar los tickets extraídos en ticketdata.json de forma automática
            guardarTickets(todosLosTickets);

        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de visitantes y tickets: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de tickets, reemplazando por completo el contenido del archivo JSON.
     * @param tickets Lista con todos los tickets a guardar de forma definitiva.
     */
    public static void guardarTickets(List<Ticket> tickets) {
        try {
            File archivo = new File("src//main//resources//data\\ticketdata.json");
            mapper.writeValue(archivo, tickets != null ? tickets : new ArrayList<>());
            System.out.println("Persistencia de tickets guardada con éxito.");
        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de tickets: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de operadores, reemplazando por completo el contenido del archivo JSON.
     * @param operadores Lista con todos los operadores a guardar de forma definitiva.
     */
    public static void guardarOperadores(List<Operador> operadores) {
        try {
            File archivo = new File("src//main//resources//data\\operadordata.json");
            mapper.writeValue(archivo, operadores != null ? operadores : new ArrayList<>());
            System.out.println("Persistencia de operadores guardada con éxito.");
        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de operadores: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de atracciones, reemplazando por completo el contenido del archivo JSON.
     * @param atracciones Lista con todas las atracciones a guardar de forma definitiva.
     */
    public static void guardarAtracciones(List<Atraccion> atracciones) {
        try {
            File archivo = new File("src//main//resources//data\\atracciondata.json");
            mapper.writeValue(archivo, atracciones != null ? atracciones : new ArrayList<>());
            System.out.println("Persistencia de atracciones guardada con éxito.");
        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de atracciones: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de zonas, reemplazando por completo el contenido del archivo JSON.
     * @param zonas Lista con todas las zonas a guardar de forma definitiva.
     */
    public static void guardarZonas(List<Zona> zonas) {
        try {
            File archivo = new File("src//main//resources//data\\zonadata.json");
            mapper.writeValue(archivo, zonas != null ? zonas : new ArrayList<>());
            System.out.println("Persistencia de zonas guardada con éxito.");
        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de zonas: " + e.getMessage());
        }
    }
}