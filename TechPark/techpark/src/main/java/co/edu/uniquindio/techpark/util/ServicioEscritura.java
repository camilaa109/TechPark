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
     * Guarda la lista de visitantes garantizando que su lista de tickets interna se escriba vacía [],
     * y de forma automática extrae y guarda sus tickets en ticketdata.json con su estructura limpia.
     * @param visitantes Lista con todos los visitantes activos en la memoria de la aplicación.
     */
    public static void guardarVisitantes(List<Visitante> visitantes) {
        try {
            File archivoVisitantes = new File("src//main//resources//data\\visitantedata.json");
            List<Visitante> listaVisitantesFiltrados = new ArrayList<>();
            List<Ticket> todosLosTickets = new ArrayList<>();

            if (visitantes != null) {
                for (Visitante originalVisitante : visitantes) {
                    // 1. Extraemos los tickets reales antes de limpiar la lista para el archivo JSON
                    if (originalVisitante.getListaTickets() != null) {
                        todosLosTickets.addAll(originalVisitante.getListaTickets());
                    }

                    // 2. Creamos un clon temporal del visitante para el JSON
                    Visitante copiaVisitante = new Visitante();
                    copiaVisitante.setNombre(originalVisitante.getNombre());
                    copiaVisitante.setDocumento(originalVisitante.getDocumento());
                    copiaVisitante.setEdad(originalVisitante.getEdad());
                    copiaVisitante.setContrasenia(originalVisitante.getContrasenia());
                    copiaVisitante.setEstatura(originalVisitante.getEstatura());
                    copiaVisitante.setSaldoVirtual(originalVisitante.getSaldoVirtual());
                    copiaVisitante.setListaFavoritos(originalVisitante.getListaFavoritos());
                    
                    // 🔴 ESTRATEGIA: Forzamos a que la lista de tickets se escriba vacía [] en visitantedata.json
                    copiaVisitante.setListaTickets(new ArrayList<>());

                    listaVisitantesFiltrados.add(copiaVisitante);
                }
            }

            // 3. Escribir los visitantes con estructura limpia en visitantedata.json (sobrescribe por completo)
            mapper.writeValue(archivoVisitantes, listaVisitantesFiltrados);
            System.out.println("Persistencia de visitantes guardada.");

            // 4. Delegamos el guardado automático de los tickets extraídos
            guardarTickets(todosLosTickets);

        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de visitantes y tickets: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de tickets en su archivo JSON, reemplazando por completo el contenido previo.
     * @param tickets Lista con todos los tickets extraídos a persistir.
     */
    public static void guardarTickets(List<Ticket> tickets) {
        try {
            File archivoTickets = new File("src//main//resources//data\\ticketdata.json");
            
            // Al escribir directamente, Jackson vacía el archivo previo y almacena la lista exacta de tickets
            mapper.writeValue(archivoTickets, tickets != null ? tickets : new ArrayList<>());
            System.out.println("Persistencia de tickets guardada.");
            
        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de tickets: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de operadores en su archivo JSON garantizando la estructura limpia
     * con sus respectivas zonas y atracciones asignadas en formato de texto plano.
     * @param operadores Lista con todos los operadores activos en el sistema.
     */
    public static void guardarOperadores(List<Operador> operadores) {
        try {
            File archivoOperadores = new File("src//main//resources//data\\operadordata.json");
            List<Operador> listaOperadoresFiltrados = new ArrayList<>();

            if (operadores != null) {
                for (Operador originalOperador : operadores) {
                    // Creamos un clon temporal del operador para el JSON
                    Operador copiaOperador = new Operador();
                    
                    // Atributos heredados de Persona/Empleado y propios
                    copiaOperador.setNombre(originalOperador.getNombre());
                    copiaOperador.setDocumento(originalOperador.getDocumento());
                    copiaOperador.setEdad(originalOperador.getEdad());
                    copiaOperador.setContrasenia(originalOperador.getContrasenia());
                    
                    // Atributos de asignación en formato String (coherentes con los JSONs anteriores)
                    copiaOperador.setNombreZonaAsignada(originalOperador.getNombreZonaAsignada());
                    copiaOperador.setNombreAtraccionAsignada(originalOperador.getNombreAtraccionAsignada());

                    listaOperadoresFiltrados.add(copiaOperador);
                }
            }

            // Escribir la lista de operadores (sobrescribe por completo el archivo vaciándolo primero)
            mapper.writeValue(archivoOperadores, listaOperadoresFiltrados);
            System.out.println("Persistencia de operadores guardada.");

        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de operadores: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de zonas garantizando que sus listas internas se escriban vacías [],
     * y de forma automática extrae y guarda sus atracciones en atracciondata.json
     * con su estructura limpia.
     * @param zonas Lista con todas las zonas vivas en la memoria del parque.
     */
    public static void guardarZonas(List<Zona> zonas) {
        try {
            File archivoZonas = new File("src//main//resources//data\\zonadata.json");
            List<Zona> listaZonasFiltradas = new ArrayList<>();
            List<Atraccion> todasLasAtracciones = new ArrayList<>();

            if (zonas != null) {
                for (Zona originalZona : zonas) {
                    // 1. Extraemos las atracciones reales antes de limpiar la zona para el archivo
                    if (originalZona.getListaAtracciones() != null) {
                        todasLasAtracciones.addAll(originalZona.getListaAtracciones());
                    }

                    // 2. Creamos un clon temporal de la zona para el JSON
                    Zona copiaZona = new Zona();
                    copiaZona.setIdZona(originalZona.getIdZona());
                    copiaZona.setNombreZona(originalZona.getNombreZona());
                    
                    // 🔴 ESTRATEGIA: Forzamos las listas vacías [] exactamente como lo requieres
                    copiaZona.setListaAtracciones(new ArrayList<>());
                    copiaZona.setListaOperadores(new ArrayList<>());

                    listaZonasFiltradas.add(copiaZona);
                }
            }

            // 3. Escribir las zonas con estructura limpia en zonadata.json
            mapper.writeValue(archivoZonas, listaZonasFiltradas);
            System.out.println("Persistencia de zonas guardada.");

            // 4. Delegamos el guardado automático de las atracciones extraídas
            guardarAtracciones(todasLasAtracciones);

        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de zonas y atracciones: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de atracciones en su archivo JSON garantizando la estructura limpia
     * con el nombreZona correspondiente y sin serializar objetos de operadores.
     * @param atracciones Lista de atracciones a persistir.
     */
    public static void guardarAtracciones(List<Atraccion> atracciones) {
        try {
            File archivoAtracciones = new File("src//main//resources//data\\atracciondata.json");
            List<Atraccion> listaAtraccionesFiltradas = new ArrayList<>();

            if (atracciones != null) {
                for (Atraccion originalAtraccion : atracciones) {
                    // Creamos un clon temporal de la atracción para el JSON
                    Atraccion copiaAtraccion = new Atraccion();
                    
                    copiaAtraccion.setIdAtraccion(originalAtraccion.getIdAtraccion());
                    copiaAtraccion.setNombreAtraccion(originalAtraccion.getNombreAtraccion());
                    copiaAtraccion.setCapacidadMaxima(originalAtraccion.getCapacidadMaxima());
                    copiaAtraccion.setRequisitosSeguridad(originalAtraccion.getRequisitosSeguridad());
                    copiaAtraccion.setCostoAdicional(originalAtraccion.getCostoAdicional());
                    copiaAtraccion.setVisitantesAcumulados(originalAtraccion.getVisitantesAcumulados());
                    copiaAtraccion.setTiempoEsperaSegundos(originalAtraccion.getTiempoEsperaSegundos());
                    copiaAtraccion.setMotivoCierre(originalAtraccion.getMotivoCierre());
                    copiaAtraccion.setTipoAtraccion(originalAtraccion.getTipoAtraccion());
                    copiaAtraccion.setEstadoAtraccion(originalAtraccion.getEstadoAtraccion());
                    copiaAtraccion.setNombreZona(originalAtraccion.getNombreZona()); // Conserva la zona coherente
                    
                    // 🔴 ESTRATEGIA: Forzamos a que la lista de operadores se guarde vacía []
                    copiaAtraccion.setListaOperadoresAsignados(new ArrayList<>());

                    listaAtraccionesFiltradas.add(copiaAtraccion);
                }
            }

            // Escribir las atracciones con la estructura exacta requerida
            mapper.writeValue(archivoAtracciones, listaAtraccionesFiltradas);
            System.out.println("Persistencia de atracciones guardada.");

        } catch (IOException e) {
            System.err.println("Error al guardar el JSON de atracciones: " + e.getMessage());
        }
    }
}