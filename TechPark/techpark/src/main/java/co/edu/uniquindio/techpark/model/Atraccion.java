package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Atraccion {
    
    private String idAtraccion;

    private String nombreAtraccion;
    private int capacidadMaxima;
    private RequisitosSeguridad requisitosSeguridad;
    private double costoAdicional;
    private int visitantesAcumulados;
    private int tiempoEspera;
    private int tiempoEsperaTotal;
    private String motivoCierre;
    private List<String> listaDocumentoOperadoresAsignados;
    private TipoAtraccion tipoAtraccion;
    private EstadoAtraccion estadoAtraccion;
    private static int contador = 0;
    private PriorityQueue<Visitante> colaVirtual = new PriorityQueue<>((v1, v2) -> {
    int p1 = v1.obtenerTicket(EstadoTicket.ACTIVO).getTipoTicket().getNivel();
    int p2 = v2.obtenerTicket(EstadoTicket.ACTIVO).getTipoTicket().getNivel();
    return Integer.compare(p1, p2); });

    // Constructor
    public Atraccion(String nombreAtraccion, int capacidadMaxima, RequisitosSeguridad requisitosSeguridad,
            double costoAdicional, int tiempoEspera, TipoAtraccion tipoAtraccion) {
        this.idAtraccion = nombreAtraccion + contador++;
        this.nombreAtraccion = nombreAtraccion;
        this.capacidadMaxima = capacidadMaxima;
        this.requisitosSeguridad = requisitosSeguridad;
        this.costoAdicional = costoAdicional;
        this.tiempoEspera = tiempoEspera;
        this.tipoAtraccion = tipoAtraccion;
        this.visitantesAcumulados = 0;
        this.tiempoEsperaTotal = 0;
        this.motivoCierre = "";
        this.listaDocumentoOperadoresAsignados = new ArrayList<>();
        this.estadoAtraccion = EstadoAtraccion.CERRADA;
    }

    public List<Visitante> realizarCiclo() {
        List<Visitante> proximosVisitantes = new ArrayList<>();
        for (int i = 0; i < capacidadMaxima; i++){
            Visitante siguiente = colaVirtual.poll();

            if (siguiente != null){
                proximosVisitantes.add(siguiente);
                this.visitantesAcumulados++;
            }
        }
        return proximosVisitantes;
    }

    public void agregarVisitanteCola (Visitante visitante){
        colaVirtual.add(visitante);
    }
    
    public void eliminarVisitanteCola (Visitante visitante){
        colaVirtual.remove(visitante);
    }

    public void asignarOperador (String documento){
        if (listaDocumentoOperadoresAsignados.contains(documento)){
            designarOperador(documento);
        }
        listaDocumentoOperadoresAsignados.add(documento);
    }

    public void designarOperador (String documento){
        listaDocumentoOperadoresAsignados.remove(documento);
    }

    //getters y setters

    public String getIdAtraccion() {
        return idAtraccion;
    }

    public int getTiempoEsperaTotal() {
        return tiempoEsperaTotal;
    }

    public void setTiempoEsperaTotal(int tiempoEsperaTotal) {
        this.tiempoEsperaTotal = tiempoEsperaTotal;
    }

    public PriorityQueue<Visitante> getColaVirtual() {
        return colaVirtual;
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

    public RequisitosSeguridad getRequisitosSeguridad() {
        return requisitosSeguridad;
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

    public List<String> getListaDocumentoOperadoresAsignados() {
        return listaDocumentoOperadoresAsignados;
    }
}