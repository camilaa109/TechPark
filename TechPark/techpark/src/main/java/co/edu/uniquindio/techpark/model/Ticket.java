package co.edu.uniquindio.techpark.model;

import java.time.LocalDateTime;

public class Ticket {
    
    private String idTicket;
    private String idVisitante;
    private double precioTicket;
    private LocalDateTime fechaTicket;
    private double descuento;
    private TipoTicket tipoTicket;
    private EstadoTicket estadoTicket;
    private static int contador = 0;

    public Ticket(){}

    public Ticket(String idVisitante, double descuento, TipoTicket tipoTicket) {
        this.idTicket = idVisitante + tipoTicket.toString() + contador++;
        this.idVisitante = idVisitante;
        this.descuento = descuento;
        this.tipoTicket = tipoTicket;
        this.precioTicket = definirPrecioTicket(tipoTicket, descuento);
        this.estadoTicket = EstadoTicket.ACTIVO;
    }

    private double definirPrecioTicket(TipoTicket tipoTicket, double descuento){

       return tipoTicket.getPrecio() * (1-descuento);
    }

    //getters y setters
    public String getIdTicket() {
        return idTicket;
    }

    public String getIdVisitante() {
        return idVisitante;
    }

    public void setIdVisitante(String idVisitante) {
        this.idVisitante = idVisitante;
    }

    public TipoTicket getTipoTicket() {
        return tipoTicket;
    }

    public double getPrecioTicket() {
        return precioTicket;
    }

    public void setPrecioTicket(double precioTicket) {
        this.precioTicket = precioTicket;
    }

    public LocalDateTime getFechaTicket() {
        return fechaTicket;
    }

    public void setFechaTicket(LocalDateTime fechaTicket) {
        this.fechaTicket = fechaTicket;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public EstadoTicket getEstadoTicket() {
        return estadoTicket;
    }

    public void setEstadoTicket(EstadoTicket estadoTicket) {
        this.estadoTicket = estadoTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public void setTipoTicket(TipoTicket tipoTicket) {
        this.tipoTicket = tipoTicket;
    }
    
}
