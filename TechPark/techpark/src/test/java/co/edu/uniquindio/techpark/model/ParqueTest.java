package co.edu.uniquindio.techpark.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void inicioSesionVisitante (){
        registroVisitante();
        String documento = "123";
        String contrasenia = "1234";

        Rol rol = parque.inicioSesion(documento, contrasenia);

        assertEquals(rol, Rol.VISITANTE);
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

        parque.comprarTicket(documento, TipoTicket.GENERAL);

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
        assertEquals(nombreAtraccion, visitanteEncontrado.getListaFavoritos().get(0));
        
    }

    @Test
    void recibirNotificacion (){
        realizarCicloAtraccion();
        String documento = "11928374";

        List<Notificacion> notificaciones = parque.obtenerNotificaciones(documento);

        assertEquals("Es tu turno", notificaciones.get(0).titulo());
    }

    @Test
    void eliminarNotificacion (){
        realizarCicloAtraccion();
        String documento = "11928374";
        String idNotificacion = "notificacion_0";

        parque.eliminarNotificacion(documento, idNotificacion);

        parque.obtenerVisitante(documento).getNotificaciones().stream().map(Notificacion::titulo).forEach(System.out::println);
        
        assertTrue(parque.obtenerNotificaciones(documento).isEmpty());
    }

    @Test
    void consultarTiempoEspera (){
        realizarCicloAtraccion();
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";

        int consulta = parque.consultarTiempoEspera(nombreZona, nombreAtraccion);

        assertEquals(15, consulta);
    }

    // Pruebas de empleados
    
    @Test
    void registroOperador (){
        String nombre = "Pablo";
        String documento = "234";
        int edad = 30;
        String contrasenia = "2345";

        parque.registrarOperador(nombre, documento, edad, contrasenia);

        Operador operadorEncontrado = parque.obtenerOperador(documento);
        assertNotNull(operadorEncontrado);
    }

    @Test
    void actualizarOperador (){
        registroOperador();
        String documento = "234";
        Operador operador = parque.obtenerOperador(documento);
        String nombre = "Juan";
        int edad = operador.getEdad();
        String contrasenia = operador.getContrasenia();
        
        parque.actualizarOperador(nombre, documento, edad, contrasenia);

        operador = parque.obtenerOperador(documento);
        assertEquals(nombre, operador.getNombre());
    }

    @Test
    void eliminarOperador (){
        registroOperador();
        String documento = "234";

        parque.eliminarOperador(documento);

        Operador operador = parque.obtenerOperador(documento);
        assertNull(operador);
    }

    @Test
    void asignarOperador (){
        registroOperador();
        agregarZona();
        agregarAtraccion();
        
        String documento = "234";
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";

        parque.asignarOperador(documento, nombreZona, nombreAtraccion);

        Operador operadorEncontrado = parque.obtenerOperador(documento);
        Atraccion atraccionEncontrada = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(nombreAtraccion, operadorEncontrado.getNombreAtraccionAsignada());
        assertEquals(operadorEncontrado, atraccionEncontrada.getListaOperadoresAsignados().get(0));
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
        String motivoCierre = "cierre";
        EstadoAtraccion estadoAtraccion = EstadoAtraccion.ACTIVA;

        parque.cambiarEstadoAtraccion(nombreZona, nombreAtraccion, estadoAtraccion, motivoCierre);

        Atraccion atraccionEncontrada = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(estadoAtraccion, atraccionEncontrada.getEstadoAtraccion());
    }

    @Test
    void realizarCicloAtraccion(){
        parque.setListaVisitantes(ServicioLectura.leerDatosVisitantes());
        agregarAtraccion();
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";

        parque.cambiarEstadoAtraccion(nombreZona, nombreAtraccion, EstadoAtraccion.ACTIVA);
        for (Visitante v : parque.getListaVisitantes()){
            parque.accesoAtraccion(v.getDocumento(), nombreZona, nombreAtraccion);
        }

        parque.realizarCicloAtraccion(nombreZona, nombreAtraccion);

        Atraccion atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        assertEquals(10, atraccion.getColaVirtual().size());
    }

    @Test
    void alertaClimatica (){
        comprarTicket();
        agregarAtraccion();
        String documento = "123";
        String nombreZona = "zona1";
        String nombreAtraccion = "atraccion1";
        String motivo = "Tormenta";

        parque.activarAlertaClimatica(motivo);

        Atraccion atraccion = parque.obtenerAtraccion(nombreZona, nombreAtraccion);
        Visitante visitante = parque.obtenerVisitante(documento);
        
        assertEquals(EstadoAtraccion.CERRADA, atraccion.getEstadoAtraccion());
        assertNotNull(visitante.getNotificaciones());
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
        TipoAtraccion tipoAtraccion = TipoAtraccion.MECANICA_ALTURA;
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
        cambiarEstadoAtraccion();
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