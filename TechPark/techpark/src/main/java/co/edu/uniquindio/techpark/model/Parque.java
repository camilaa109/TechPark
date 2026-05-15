package co.edu.uniquindio.techpark.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Parque{

    private String nombre;
    private int capacidadMaxima;
    private List<Zona> listaZonas; 
    private List<Visitante> listaVisitantes; 
    private List<Empleado> listaEmpleados;

    //Constructor
    public Parque(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.listaZonas = new ArrayList<Zona>();
        this.listaVisitantes = new ArrayList<Visitante>();
        this.listaEmpleados = new ArrayList<Empleado>();
    }

    //funciones para visitantes
    public boolean agregarVisitante (String nombre, String documento, int edad, String contrasenia, double estatura){
        Visitante visitante = new Visitante(nombre, documento, edad, contrasenia, estatura);
        listaVisitantes.add(visitante);
        return true;
    }
    
    public Visitante obtenerVisitante (String documento){
        for (Visitante v: listaVisitantes){
            if (documento.equals(v.getDocumento())){
                return v;
            }
        }
        return null;
    }

    public void actualizarVisitante (String nombre, String documento, int edad, double estatura){
        Visitante visitante = obtenerVisitante(documento);
        visitante.setNombre(nombre);
        visitante.setEdad(edad);
        visitante.setEstatura(estatura);
    }

    public void eliminarVisitante (String documento){
        Visitante v = obtenerVisitante(documento);
        listaVisitantes.remove(v);
    }

    public void comprarTicket (String documento, double descuento, TipoTicket tipoticket){
        Visitante v = obtenerVisitante(documento);
        Ticket ticket = new Ticket(descuento, tipoticket);
        v.restarSaldoVirtual(ticket.getPrecioTicket());
        v.agregarTicket(ticket);
    }

    public boolean accesoAtraccion(String documento, String nombreZona, String nombreAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        Visitante visitante = obtenerVisitante(documento);
        Ticket ticket = visitante.obtenerTicket(EstadoTicket.ACTIVO);
        boolean esValido = atraccion.getRequisitosSeguridad().esApto(visitante.getEdad(), visitante.getEstatura());
        if (!esValido 
            || ticket == null 
            || atraccion.getEstadoAtraccion() == EstadoAtraccion.CERRADA
            || atraccion.getEstadoAtraccion() == EstadoAtraccion.EN_MANTENIMIENTO){
            return false;
        }
        if (atraccion.getCostoAdicional() > 0){
            if (ticket.getTipoTicket() == TipoTicket.GENERAL){
                boolean pagoAprobado = realizarPago(visitante, atraccion.getCostoAdicional());
                if (!pagoAprobado){
                    return false;
                }
            }
        }
        atraccion.agregarVisitanteCola(visitante);
        return true;
    }

    public boolean realizarPago (Visitante visitante, double cantidad){
        if (visitante.getSaldoVirtual() < cantidad){
            return false;
        }
        visitante.restarSaldoVirtual(cantidad);
        return true;
    }

    public void agregarFavorito (String documento, String nombreAtraccion){
        Visitante v = obtenerVisitante(documento);
        v.agregarFavorito(nombreAtraccion);
    }

    public List<Visitante> obtenerVisitantesActivos (){
        List<Visitante> visitantesActivos = new ArrayList<>();
        for (Visitante v : listaVisitantes){
            if (v.obtenerTicket(EstadoTicket.ACTIVO) != null){
                visitantesActivos.add(v);
            }
        }
        return visitantesActivos;
    }

    //Funciones para empleados
    public void registrarOperador(String nombre, String documento, int edad, String contrasenia) {
        Operador operador = new Operador(nombre, documento, edad, contrasenia);
        listaEmpleados.add(operador);
    }

    public Operador obtenerOperador(String documento) {
        for (Empleado e : listaEmpleados){
            if (e instanceof Operador && e.getDocumento().equals(documento)){
                return (Operador) e;
            }
        }
        return null;
    }

    public void asignarOperador(String documento, String nombreZona, String nombreAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        atraccion.asignarOperador(documento);
        Operador operador = obtenerOperador(documento);
        operador.setNombreZonaAsignada(nombreZona);
        operador.setNombreAtraccionAsignada(nombreAtraccion);
    }

    public void registrarRevision(String nombreZona, String nombreAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        atraccion.setEstadoAtraccion(EstadoAtraccion.ACTIVA);
        notificarVisitantes(obtenerVisitantesActivos(), nombreAtraccion, "Una Atracción ha vuelto", 
            "Atracción " + nombreAtraccion + " se encuentra de nuevo en funcionamiento.");
    }

    public void cambiarEstadoAtraccion(String nombreZona, String nombreAtraccion, EstadoAtraccion estadoAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        atraccion.setEstadoAtraccion(estadoAtraccion);
    }

    public void realizarCicloAtraccion(String nombreZona, String nombreAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        List<Visitante> proximosVisitantes = atraccion.realizarCiclo();
        if (proximosVisitantes.isEmpty()){
            return;
        }
        notificarVisitantes(proximosVisitantes, nombreAtraccion, "Es tu turno", 
            "Acercate a la atracción: " + nombreAtraccion);
        verificarMantenimiento();
    }

    private void notificarVisitantes(List<Visitante> visitantes, String nombreAtraccion, String titulo, String mensaje) {
        for (Visitante v : visitantes){
            Notificable notificacion = new Notificacion(titulo, mensaje, LocalDateTime.now());
            notificacion.enviar(v);
        }
    }

    //Funciones para atracciones y zonas
    public void agregarZona (String nombre){
        Zona zona = new Zona(nombre);
        listaZonas.add(zona);
    }

    public Zona obtenerZona (String nombre){
        for (Zona z : listaZonas){
            if (z.getNombreZona().equals(nombre)){
                return z;
            }
        }
        return null;
    }

    public Atraccion obtenerAtraccion (String nombreZona, String nombreAtraccion){
        Zona zona = obtenerZona(nombreZona);
        return zona.obtenerAtraccion(nombreAtraccion);
    }

    public void agregarAtraccion (String nombreAtraccion, int capacidadMaxima, int edadMinima, double alturaMinima, 
        double costoAdicional, int tiempoEspera, TipoAtraccion tipoAtraccion, String nombreZona){
        Zona zona = obtenerZona(nombreZona);
        RequisitosSeguridad requisitosSeguridad = new RequisitosSeguridad(edadMinima, alturaMinima);
        Atraccion atraccion = new Atraccion(nombreAtraccion, capacidadMaxima, requisitosSeguridad, costoAdicional, 
            tiempoEspera, tipoAtraccion);
        zona.agregarAtraccion(atraccion);
    }

    public void agregarEmpleado (Empleado empleado){
        listaEmpleados.add(empleado);
    }

        //verifica todas las atracciones y asigna el estado EN_MANTENIMIENTO a las que tiene 500 visitantes acumulados
        // o mas
    public void verificarMantenimiento (){
        for (Zona z : listaZonas){
            for (Atraccion a : z.getListaAtracciones()){
                if (a.getVisitantesAcumulados() >= 500){
                    a.setEstadoAtraccion(EstadoAtraccion.EN_MANTENIMIENTO);
                    notificarVisitantes(obtenerVisitantesActivos(), a.getNombreAtraccion(), "Cierre de Atracción", 
                    "Atracción " + a.getNombreAtraccion() + " se encuentra en mantenimiento.");
                }
            }
        }
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public List<Zona> getListaZonas() {
        return listaZonas;
    }

    public List<Visitante> getListaVisitantes() {
        return listaVisitantes;
    }

    public List<Empleado> getListaIdEmpleados() {
        return listaEmpleados;
    }

    //Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setListaZonas(List<Zona> listaZonas) {
        this.listaZonas = listaZonas;
    }

    public void setListaVisitantes(List<Visitante> listaVisitantes) {
        this.listaVisitantes = listaVisitantes;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
}