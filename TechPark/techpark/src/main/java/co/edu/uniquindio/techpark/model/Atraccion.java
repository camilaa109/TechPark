package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Atraccion {
    
    private String idAtraccion;

    private String nombreAtraccion;
    private int capacidadMaxima;
    private int alturaMinima;
    private int edadMinima;
    private double costoAdicional;
    private int visitantesAcumulados;
    private int tiempoEspera;
    private String motivoCierre;
    private List<String> listaIdOperadoresAsignados;
    private TipoAtraccion tipoAtraccion;
    private EstadoAtraccion estadoAtraccion;
    private static int contador = 0;

    // Constructor
    public Atraccion(String nombreAtraccion, int capacidadMaxima, int alturaMinima, int edadMinima,
            double costoAdicional, int tiempoEspera, TipoAtraccion tipoAtraccion) {
        this.idAtraccion = nombreAtraccion + contador++;
        this.nombreAtraccion = nombreAtraccion;
        this.capacidadMaxima = capacidadMaxima;
        this.alturaMinima = alturaMinima;
        this.edadMinima = edadMinima;
        this.costoAdicional = costoAdicional;
        this.tiempoEspera = tiempoEspera;
        this.tipoAtraccion = tipoAtraccion;
        this.visitantesAcumulados = 0;
        this.motivoCierre = "";
        this.listaIdOperadoresAsignados = new ArrayList<>();
        this.estadoAtraccion = EstadoAtraccion.CERRADA;
    }

    //getters y setters
    public String getIdAtraccion() {
        return idAtraccion;
    }

    public String getNombreAtraccion() {
        return nombreAtraccion;
    }

    public void setNombreAtraccion(String nombreAtraccion) {
        this.nombreAtraccion = nombreAtraccion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getAlturaMinima() {
        return alturaMinima;
    }

    public void setAlturaMinima(int alturaMinima) {
        this.alturaMinima = alturaMinima;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public double getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(double costoAdicional) {
        this.costoAdicional = costoAdicional;
    }

    public int getVisitantesAcumulados() {
        return visitantesAcumulados;
    }

    public void setVisitantesAcumulados(int visitantesAcumulados) {
        this.visitantesAcumulados = visitantesAcumulados;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public String getMotivoCierre() {
        return motivoCierre;
    }

    public void setMotivoCierre(String motivoCierre) {
        this.motivoCierre = motivoCierre;
    }

    public TipoAtraccion getTipoAtraccion() {
        return tipoAtraccion;
    }

    public void setTipoAtraccion(TipoAtraccion tipoAtraccion) {
        this.tipoAtraccion = tipoAtraccion;
    }

    public EstadoAtraccion getEstadoAtraccion() {
        return estadoAtraccion;
    }

    public void setEstadoAtraccion(EstadoAtraccion estadoAtraccion) {
        this.estadoAtraccion = estadoAtraccion;
    }

    public List<String> getListaIdOperadoresAsignados() {
        return listaIdOperadoresAsignados;
    }
    

}
