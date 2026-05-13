package co.edu.uniquindio.techpark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ParqueTest {

    private Parque parque = new Parque("Parque", 100);

    // Pruebas de visitante

    @Test
    void registroVisitante (){
        String nombre = "Andres";
        String documento = "123";
        int edad = 19;
        String contrasenia = "1234";
        double estatura = 1.81;

        boolean resultado = parque.agregarVisitante(nombre, documento, edad, contrasenia, estatura);

        assertTrue(resultado, "Estudiante no registrado");
    }

    @Test
    void obtenerListaVisitantes (){
        registroVisitante();
        List<Visitante> visitantesObtenidos = parque.getListaVisitantes();
        assertFalse(visitantesObtenidos.isEmpty(), "Lista de visitantes vacia");
    }

    @Test
    void obtenerVisitante (){
        registroVisitante();
        String documento = "123";
        Visitante visitanteObtenido = parque.obtenerVisitante(documento);

        assertEquals(documento, visitanteObtenido.getDocumento());
    }

    @Test
    void actualizarVisitante (){
        registroVisitante();

        String documento = "123";
        Visitante visitante = parque.obtenerVisitante(documento);
        String nuevoNombre = "Juan";
        int edad = visitante.getEdad();
        double estatura = visitante.getEstatura();
        

        parque.actualizarVisitante(nuevoNombre, documento, edad, estatura);

        Visitante visitanteEncontrado = parque.obtenerVisitante(documento);
        assertEquals(nuevoNombre, visitanteEncontrado.getNombre());
    }

    @Test
    void eliminarVisitante (){
        registroVisitante();
        String documento = "123";
        parque.eliminarVisitante(documento);
        Visitante visitanteObtenido = parque.obtenerVisitante(documento);

        assertNull(visitanteObtenido);
    }

    @Test
    void comprarTicket (){
        registroVisitante();
        String documento = "123";
        double saldoVirtual = 5000;
        Visitante visitanteEncontrado = parque.obtenerVisitante(documento);
        visitanteEncontrado.setSaldoVirtual(saldoVirtual);
        parque.comprarTicket(documento, 0.5, TipoTicket.GENERAL);

        assertNotEquals(saldoVirtual, visitanteEncontrado.getSaldoVirtual());
        assertFalse(visitanteEncontrado.getListaTickets().isEmpty());
    }

    @Test
    void agregarFavorito (){
        registroVisitante();
        agregarZona();
        agregarAtraccion();

        String documento = "123";
        String nombreAtraccion = "atraccion1"; 
        parque.agregarFavorito(documento, nombreAtraccion);

        Visitante visitanteEncontrado = parque.obtenerVisitante(documento);
        assertEquals(nombreAtraccion, visitanteEncontrado.getListaNombreFavoritos().get(0));
        
    }


    // Pruebas de Zonas y Atracciones

    @Test
    void agregarZona (){
        String nombre = "zona1";
        parque.agregarZona(nombre);
        assertEquals(nombre, parque.getListaZonas().get(0).getNombreZona());
    }

    @Test
    void agregarAtraccion (){
        agregarZona();

        String nombreAtraccion = "atraccion1";
        int capacidadMaxima = 10;
        double alturaMinima = 1.50;
        double costoAdicional = 0;
        int tiempoEspera = 5;
        TipoAtraccion tipoAtraccion = TipoAtraccion.MECANICA;
        String nombreZona = "zona1";

        parque.agregarAtraccion(nombreAtraccion, capacidadMaxima, alturaMinima, costoAdicional, tiempoEspera, tipoAtraccion, nombreZona);

        Atraccion atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertNotNull(atraccion);
        
    }

}
