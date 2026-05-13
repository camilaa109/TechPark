package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Visitante extends Persona {

    private double estatura;
    private double saldoVirtual;
    private List<Ticket> listaTickets;
    private List<String> listaFavoritos;
    
    public Visitante(String nombre, String documento, int edad, String contrasenia, double estatura) {
        super(nombre, documento, edad, contrasenia);
        this.estatura = estatura;
        this.saldoVirtual = 0;
        this.listaTickets = new ArrayList<>();
        this.listaFavoritos = new ArrayList<>();
    }

    public void restarSaldoVirtual (double cantidad){
        saldoVirtual -= cantidad;
    }

    public void agregarTicket (Ticket t){
        listaTickets.add(t);
    }

    public void agregarFavorito (String nombreAtraccion){
        listaFavoritos.add(nombreAtraccion);
    }

    //getters y setters
    public List<Ticket> getListaTickets() {
        return listaTickets;
    }

    public List<String> getListaNombreFavoritos() {
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

    
    
}
