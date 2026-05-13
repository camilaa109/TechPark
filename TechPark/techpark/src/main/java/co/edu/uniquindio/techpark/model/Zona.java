package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    
    private String idZona;
    private String nombreZona;
    private List<Atraccion> listaAtracciones;
    private List<String> listaIdOperadores;
    private static int contador = 0;

    //Constructor
    public Zona(String nombreZona) {
        this.idZona = nombreZona + contador++;
        this.nombreZona = nombreZona;
        this.listaAtracciones = new ArrayList<>();
        this.listaIdOperadores = new ArrayList<>();
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

    public List<String> getListaIdOperadores() {
        return listaIdOperadores;
    }
    

}
