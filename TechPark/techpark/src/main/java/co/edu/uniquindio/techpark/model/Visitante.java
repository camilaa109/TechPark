package co.edu.uniquindio.techpark.model;

import java.util.ArrayList;
import java.util.List;

public class Visitante extends Persona {

    private double estatura;
    private double saldoVirtual;
    private List<Ticket> listaTickets;
    private List<String> listaIdFavoritos;
    
    public Visitante(String nombre, String documento, int edad, int estatura) {
        super(nombre, documento, edad);
        this.estatura = estatura;
        this.saldoVirtual = 0;
        this.listaTickets = new ArrayList<>();
        this.listaIdFavoritos = new ArrayList<>();
    }

    //getters y setters
    public List<Ticket> getListaTickets() {
        return listaTickets;
    }

    public List<String> getListaIdFavoritos() {
        return listaIdFavoritos;
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
