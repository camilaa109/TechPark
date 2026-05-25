package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Visitante extends Persona {

    private double estatura;
    private double saldoVirtual;
    private List<Ticket> listaTickets;
    private List<String> listaFavoritos;
    private List<Notificacion> notificaciones;
    
    public Visitante (){
        this.saldoVirtual = 0;
        this.listaTickets = new ArrayList<>();
        this.listaFavoritos = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

    public Visitante(String nombre, String documento, int edad, String contrasenia, double estatura) {
        super(nombre, documento, edad, contrasenia);
        this.estatura = estatura;
        this.saldoVirtual = 0;
        this.listaTickets = new ArrayList<>();
        this.listaFavoritos = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

    public void eliminarNotificacion (String idNotificacion){
        for (int i = 0; i < notificaciones.size(); i++){
            if (notificaciones.get(i).id().equals(idNotificacion)){
                notificaciones.remove(i);
            }
        }
    }

    public void recibirNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }   

    public Ticket obtenerTicket (EstadoTicket estadoTicket){
        for (Ticket t : listaTickets){
            if (t.getEstadoTicket().equals(estadoTicket)){
                return t;
            }
        }
        return null;
    }

    public void restarSaldoVirtual (double cantidad){
        saldoVirtual -= cantidad;
    }

    public void agregarTicket (Ticket t){
        listaTickets.add(t);
    }

    public void agregarFavorito (String nombreAtraccion){
        if (!listaFavoritos.contains(nombreAtraccion)) {
            listaFavoritos.add(nombreAtraccion);
        }
    }

    public void eliminarFavorito (String nombreAtraccion){
        listaFavoritos.remove(nombreAtraccion);
    }

    //getters y setters
    public List<Ticket> getListaTickets() {
        return listaTickets;
    }

    public List<String> getListaFavoritos() {
        return listaFavoritos;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getSaldoVirtual() {
        return saldoVirtual;
    }

    public void setSaldoVirtual(double saldoVirtual) {
        this.saldoVirtual = saldoVirtual;
    }

    public void setListaTickets(List<Ticket> listaTickets) {
        this.listaTickets = listaTickets;
    }

    public void setListaFavoritos(List<String> listaFavoritos) {
        this.listaFavoritos = listaFavoritos;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }
}
