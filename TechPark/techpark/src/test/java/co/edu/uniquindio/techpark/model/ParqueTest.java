package co.edu.uniquindio.techpark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ParqueTest {

    private Parque parque = new Parque("Parque", 100);

    @Test
    void registroVisitante (){
        Visitante visitante = new Visitante("Andres", "123", 19, "1234", 1.81);
        boolean resultado = parque.agregarVisitante(visitante);
        assertTrue(resultado, "Estudiante registrado");
    }

    @Test
    void obtenerListaEmpleados (){
        registroVisitante();
        List<Visitante> visitantesObtenidos = parque.obtenerListaVisitantes();
        assertFalse(visitantesObtenidos.isEmpty(), "Lista de visitantes vacia");
    }

    @Test
    void obtenerEmpleado (){
        registroVisitante();
        String documento = "123";
        Visitante visitanteObtenido = parque.obtenerVisitante(documento);
        assertEquals(documento, visitanteObtenido.getDocumento());
    }

   
}
