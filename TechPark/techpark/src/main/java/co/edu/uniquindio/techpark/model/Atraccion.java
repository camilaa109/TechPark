package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Atraccion {
    
    private String idAtraccion;

    private String nombreAtraccion;
    private String nombreZona;
    private int capacidadMaxima;
    private RequisitosSeguridad requisitosSeguridad;
    private double costoAdicional;
    private int visitantesAcumulados;
    private int tiempoEsperaSegundos;
    private String motivoCierre;
    private List<Operador> listaOperadoresAsignados;
    private TipoAtraccion tipoAtraccion;
    private EstadoAtraccion estadoAtraccion;
    private static int contador = 0;
    private PriorityQueue<Visitante> colaVirtual;

    // Constructor

    public Atraccion (){
        this.colaVirtual = new PriorityQueue<>((v1, v2) -> {
            int p1 = v1.obtenerTicket(EstadoTicket.ACTIVO).getTipoTicket().getNivel();
            int p2 = v2.obtenerTicket(EstadoTicket.ACTIVO).getTipoTicket().getNivel();
            return Integer.compare(p1, p2); });
    }

    public Atraccion(String nombreAtraccion, String nombreZona, int capacidadMaxima, RequisitosSeguridad requisitosSeguridad,
            double costoAdicional, int tiempoEspera, TipoAtraccion tipoAtraccion) {
        this.idAtraccion = nombreAtraccion + contador++;
        this.nombreAtraccion = nombreAtraccion;
        this.nombreZona = nombreZona;
        this.capacidadMaxima = capacidadMaxima;
        this.requisitosSeguridad = requisitosSeguridad;
        this.costoAdicional = costoAdicional;
        this.tiempoEsperaSegundos = tiempoEspera;
        this.tipoAtraccion = tipoAtraccion;
        this.visitantesAcumulados = 0;
        this.motivoCierre = "";
        this.listaOperadoresAsignados = new ArrayList<>();
        this.estadoAtraccion = EstadoAtraccion.CERRADA;
        this.colaVirtual = new PriorityQueue<>((v1, v2) -> {
            int p1 = v1.obtenerTicket(EstadoTicket.ACTIVO).getTipoTicket().getNivel();
            int p2 = v2.obtenerTicket(EstadoTicket.ACTIVO).getTipoTicket().getNivel();
            return Integer.compare(p1, p2); });
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

    public void asignarOperador (Operador operador){
        if (!listaOperadoresAsignados.contains(operador)){
            listaOperadoresAsignados.add(operador);
        }
    }

    public void designarOperador (Operador operador){
        listaOperadoresAsignados.remove(operador);
    }

    //getters y setters

    public String getIdAtraccion() {
        return idAtraccion;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public void setIdAtraccion(String idAtraccion) {
        this.idAtraccion = idAtraccion;
    }

    public void setRequisitosSeguridad(RequisitosSeguridad requisitosSeguridad) {
        this.requisitosSeguridad = requisitosSeguridad;
    }

    public void setListaOperadoresAsignados(List<Operador> listaOperadoresAsignados) {
        this.listaOperadoresAsignados = listaOperadoresAsignados;
    }

    public void setColaVirtual(PriorityQueue<Visitante> colaVirtual) {
        this.colaVirtual = new PriorityQueue<>((v1, v2) -> {
            int p1 = v1.obtenerTicket(EstadoTicket.ACTIVO).getTipoTicket().getNivel();
            int p2 = v2.obtenerTicket(EstadoTicket.ACTIVO).getTipoTicket().getNivel();
            return Integer.compare(p1, p2); });
    }

    public int calcularTiempoEsperaTotal() {
        return tiempoEsperaSegundos + ((colaVirtual.size()/capacidadMaxima)*tiempoEsperaSegundos);
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

    public int getTiempoEsperaSegundos() {
        return tiempoEsperaSegundos;
    }

    public void setTiempoEsperaSegundos(int tiempoEspera) {
        this.tiempoEsperaSegundos = tiempoEspera;
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

    public List<Operador> getListaOperadoresAsignados() {
        return listaOperadoresAsignados;
    }
}