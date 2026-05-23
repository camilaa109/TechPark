package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    
    private String idZona;
    private String nombreZona;
    private List<Atraccion> listaAtracciones;
    private List<Operador> listaOperadores;
    private static int contador = 0;

    //Constructor
    public Zona (){}

    public Zona(String nombreZona) {
        this.idZona = nombreZona + contador++;
        this.nombreZona = nombreZona;
        this.listaAtracciones = new ArrayList<>();
        this.listaOperadores = new ArrayList<>();
    }

    public void asignarOperador(Operador operador, String nombreAtraccion) {
        if (!listaOperadores.contains(operador)){
            listaOperadores.add(operador);
        }
        Atraccion atraccion = obtenerAtraccion(nombreAtraccion);
        atraccion.asignarOperador(operador);
    }

    public void designarOperador (Operador operador){
        listaOperadores.remove(operador);
    }

    public List<Atraccion> obtenerAtraccioneTipo (TipoAtraccion tipoAtraccion){
        List<Atraccion> atracciones = new ArrayList<>();
        for (Atraccion a : listaAtracciones){
            if (a.getTipoAtraccion().equals(tipoAtraccion)){
                atracciones.add(a);
            }
        }
        return atracciones;
    }

    public void agregarAtraccion (Atraccion atraccion){
        listaAtracciones.add(atraccion);
    }

    public Atraccion obtenerAtraccion (String nombre){
        for (Atraccion a : listaAtracciones){
            if (a.getNombreAtraccion().equals(nombre)){
                return a;
            }
        }
        return null;
    }

    //getters y setters
    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getIdZona() {
        return idZona;
    }

    public List<Atraccion> getListaAtracciones() {
        return listaAtracciones;
    }

    public List<Operador> getListaOperadores() {
        return listaOperadores;
    }

}
