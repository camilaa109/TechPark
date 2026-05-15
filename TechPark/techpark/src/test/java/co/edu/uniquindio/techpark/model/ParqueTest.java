package co.edu.uniquindio.techpark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import co.edu.uniquindio.techpark.util.ServicioLectura;

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

    // Pruebas de empleados
    
    @Test
    void registroOperadores (){
        String nombre = "Pablo";
        String documento = "234";
        int edad = 30;
        String contrasenia = "2345";

        parque.registrarOperador(nombre, documento, edad, contrasenia);

        Operador operadorEncontrado = parque.obtenerOperador(documento);
        assertNotNull(operadorEncontrado);
    }

    @Test
    void asignarOperador (){
        registroOperadores();
        agregarZona();
        agregarAtraccion();
        
        String documento = "234";
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";

        parque.asignarOperador(documento, nombreZona, nombreAtraccion);

        Operador operadorEncontrado = parque.obtenerOperador(documento);
        Atraccion atraccionEncontrada = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(nombreAtraccion, operadorEncontrado.getNombreAtraccionAsignada());
        assertEquals(documento, atraccionEncontrada.getListaDocumentoOperadoresAsignados().get(0));
    }

    @Test
    void registrarRevision (){
        cierreAutomaticoMantenimiento();
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";

        parque.registrarRevision(nombreZona, nombreAtraccion);

        Atraccion atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(EstadoAtraccion.ACTIVA, atraccion.getEstadoAtraccion());
    }

    @Test
    void cambiarEstadoAtraccion (){
        agregarAtraccion();
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";
        EstadoAtraccion estadoAtraccion = EstadoAtraccion.ACTIVA;

        parque.cambiarEstadoAtraccion(nombreZona, nombreAtraccion, estadoAtraccion);

        Atraccion atraccionEncontrada = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(estadoAtraccion, atraccionEncontrada.getEstadoAtraccion());
    }

    @Test
    void realizarCicloAtraccion(){
        parque.setListaVisitantes(ServicioLectura.leerDatosVisitantes());
        agregarAtraccion();
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";

        for (Visitante v : parque.getListaVisitantes()){
            parque.accesoAtraccion(v.getDocumento(), nombreZona, nombreAtraccion);
        }

        parque.realizarCicloAtraccion(nombreZona, nombreAtraccion);

        Atraccion atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(4, atraccion.getColaVirtual().size());
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
        int capacidadMaxima = 5;
        int edadMinima = 10;
        double alturaMinima = 1.50;
        double costoAdicional = 0;
        int tiempoEspera = 5;
        TipoAtraccion tipoAtraccion = TipoAtraccion.MECANICA;
        String nombreZona = "zona1";

        parque.agregarAtraccion(nombreAtraccion, capacidadMaxima, edadMinima, alturaMinima, costoAdicional, 
            tiempoEspera, tipoAtraccion, nombreZona);

        Atraccion atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertNotNull(atraccion);
    }

    @Test
    void cierreAutomaticoMantenimiento (){
        agregarAtraccion();
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";
        Atraccion atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        atraccion.setVisitantesAcumulados(500);
        
        parque.verificarMantenimiento();

        atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(EstadoAtraccion.EN_MANTENIMIENTO, atraccion.getEstadoAtraccion());
    }

    @Test
    void validarAcceso (){
        agregarAtraccion();
        comprarTicket();

        String documento = "123";
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";

        boolean esValido = parque.accesoAtraccion(documento, nombreZona, nombreAtraccion);

        assertTrue(esValido, "El visitante no es valido");  
        Atraccion atraccionEncontrada = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(documento, atraccionEncontrada.getColaVirtual().poll().getDocumento());
    }
}