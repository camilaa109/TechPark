package co.edu.uniquindio.techpark.model;

import java.time.LocalDateTime;

public class Ticket {
    
    private String idTicket;
    private double precioTicket;
    private LocalDateTime fechaTicket;
    private double descuento;
    private TipoTicket tipoTicket;
    private EstadoTicket estadoTicket;
    private static int contador = 0;

    public Ticket(double descuento, TipoTicket tipoTicket) {
        this.idTicket = tipoTicket.toString() + contador++;
        this.descuento = descuento;
        this.tipoTicket = tipoTicket;
        this.precioTicket = definirPrecioTicket(tipoTicket, descuento);
        this.estadoTicket = EstadoTicket.RESERVADO;
    }

    private double definirPrecioTicket(TipoTicket tipoTicket, double descuento){

        switch (tipoTicket){
            case GENERAL:
                return 100 * (1-descuento);
            case FAST_PASS:
                return 200 * (1-descuento);
            case FAMILIAR:
                return 300 * (1-descuento);
            default:
                return 0;
        }
    }

    //getters y setters
    public String getIdTicket() {
        return idTicket;
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

    

    
}
