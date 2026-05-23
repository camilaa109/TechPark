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
    private int contadorNotificaciones = 0;

    //Constructor
    public Parque(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.listaZonas = new ArrayList<Zona>();
        this.listaVisitantes = new ArrayList<Visitante>();
        this.listaEmpleados = new ArrayList<Empleado>();
    }

    public Rol inicioSesion(String documento, String contrasenia) {
        Persona p;
        if (!existePersona(documento)){
            return null;
        }

        p = obtenerVisitante(documento);
        if (p == null){
            p = obtenerOperador(documento);
            return Rol.OPERADOR;
        }
        return Rol.VISITANTE;
    }

    //funciones para visitantes
    public boolean agregarVisitante (String nombre, String documento, int edad, String contrasenia, double estatura){
        if (obtenerVisitante(documento) == null){
            return false;
        }
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

    public boolean comprarTicket (String documento, TipoTicket tipoticket){
        Visitante v = obtenerVisitante(documento);
        double descuento = 0;
        if(tipoticket.equals(TipoTicket.FAMILIAR)){
            descuento = 0.10;
        }
        Ticket ticket = new Ticket(documento, descuento, tipoticket);
        if(v.getSaldoVirtual()<ticket.getPrecioTicket()){
            return false;
        }
        v.restarSaldoVirtual(ticket.getPrecioTicket());
        v.agregarTicket(ticket);
        return true;
    }

    public List<Ticket> obtenerTickets (String documento){
        Visitante v = obtenerVisitante(documento);
        return v.getListaTickets();
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

    public List<String> obtenerFavoritos (String documento){
        Visitante v = obtenerVisitante(documento);
        return v.getListaFavoritos();
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

    public List<Notificacion> obtenerNotificaciones (String documento){
        Visitante visitante = obtenerVisitante(documento);
        List<Notificacion> notificaciones = new ArrayList<>(visitante.getNotificaciones());
        //visitante.getNotificaciones().clear();
        return notificaciones;
    }

    public void eliminarNotificacion(String documento, String idNotificacion) {
        Visitante visitante = obtenerVisitante(documento);
        visitante.eliminarNotificacion(idNotificacion);
    }

    public int consultarTiempoEspera(String nombreZona, String nombreAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        return atraccion.getTiempoEsperaTotal();
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

    public List<Operador> obtenerListaOperadores (){
        List<Operador> operadores = new ArrayList<>();
        for (Empleado e : listaEmpleados){
            if (e instanceof Operador){
                operadores.add((Operador)e);
            }
        }
        return operadores;
    }

     public void actualizarOperador(String nombre, String documento, int edad, String contrasenia) {
        Operador operador = obtenerOperador(documento);
        operador.setContrasenia(contrasenia);
        operador.setDocumento(documento);
        operador.setEdad(edad);
        operador.setNombre(nombre);
    }

    public void eliminarOperador (String documento){
        Operador operador = obtenerOperador(documento);
        listaEmpleados.remove(operador);
    }

    public void asignarOperador(String documento, String nombreZona, String nombreAtraccion) {
        Zona zona = obtenerZona(nombreZona);
        Operador operador = obtenerOperador(documento);
        zona.asignarOperador(operador, nombreAtraccion);
        operador.setNombreZonaAsignada(nombreZona);
        operador.setNombreAtraccionAsignada(nombreAtraccion);
    }

    public void registrarRevision(String nombreZona, String nombreAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        atraccion.setEstadoAtraccion(EstadoAtraccion.ACTIVA);
        notificarVisitantes(obtenerVisitantesActivos(), "Una Atracción ha vuelto", 
            "Atracción " + nombreAtraccion + " se encuentra de nuevo en funcionamiento.");
    }

    public void cerraAtraccion(String nombreZona, String nombreAtraccion, String motivoCierre){
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        cambiarEstadoAtraccion(atraccion, EstadoAtraccion.CERRADA, motivoCierre);
        notificarVisitantes(obtenerVisitantesActivos(), "Cierre de atraccion", 
            "La atraccion " + nombreAtraccion + " se ha cerrado por motivos de " + motivoCierre);
    }

    public void abrirAtraccion(String nombreZona, String nombreAtraccion){
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        cambiarEstadoAtraccion(atraccion, EstadoAtraccion.ACTIVA, "");
        notificarVisitantes(obtenerVisitantesActivos(), "Apertura de atraccion", 
            "La atraccion " + nombreAtraccion + " esta en funcionamiento");
    }

    public void cambiarEstadoAtraccion(String nombreZona, String nombreAtraccion, EstadoAtraccion estadoAtraccion, String motivoCierre) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        cambiarEstadoAtraccion(atraccion, estadoAtraccion, motivoCierre);
    }
    
    public void cambiarEstadoAtraccion(String nombreZona, String nombreAtraccion, EstadoAtraccion estadoAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        atraccion.setEstadoAtraccion(estadoAtraccion);
    }

    private void cambiarEstadoAtraccion(Atraccion atraccion, EstadoAtraccion estadoAtraccion, String motivoCierre) {
        atraccion.setEstadoAtraccion(estadoAtraccion);
        atraccion.setMotivoCierre(motivoCierre);
    }


    public void realizarCicloAtraccion(String nombreZona, String nombreAtraccion) {
        Atraccion atraccion = obtenerAtraccion(nombreZona, nombreAtraccion);
        List<Visitante> proximosVisitantes = atraccion.realizarCiclo();
        if (proximosVisitantes.isEmpty()){
            return;
        }
        notificarVisitantes(proximosVisitantes, "Es tu turno", 
            "Acercate a la atracción: " + nombreAtraccion);
        verificarMantenimiento();
    }

    public void activarAlertaClimatica (String motivo){
        String atraccionesLista = "";
        for (Zona z : listaZonas){
            for (Atraccion a : z.obtenerAtraccioneTipo(TipoAtraccion.ACUATICA)){
                cambiarEstadoAtraccion(a, EstadoAtraccion.CERRADA, motivo);
                atraccionesLista += a.getNombreAtraccion() + "\n";
            }
            for (Atraccion a : z.obtenerAtraccioneTipo(TipoAtraccion.MECANICA_ALTURA)){
                cambiarEstadoAtraccion(a, EstadoAtraccion.CERRADA, motivo);
                atraccionesLista += a.getNombreAtraccion() + "\n";
            }
        }
        notificarVisitantes(obtenerVisitantesActivos(), "Cierre por Alerta Climatica", 
            "Las atracciones: \n" + atraccionesLista + " se cierran por " + motivo);
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
        Atraccion atraccion = new Atraccion(nombreAtraccion, nombreZona, capacidadMaxima, requisitosSeguridad, costoAdicional, 
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
                    notificarVisitantes(obtenerVisitantesActivos(), "Cierre de Atracción", 
                    "Atracción " + a.getNombreAtraccion() + " se encuentra en mantenimiento.");
                }
            }
        }
    }

    private void notificarVisitantes(List<Visitante> visitantes, String titulo, String mensaje) {
        for (Visitante v : visitantes){
            Notificable notificacion = new Notificacion("notificacion_"+contadorNotificaciones++, titulo, 
                mensaje, LocalDateTime.now());
            notificacion.enviar(v);
        }
    }

    private boolean existePersona (String documento){
        for (Visitante v : listaVisitantes){
            if (v.getDocumento().equals(documento)){
                return true;
            }
        }
        for (Empleado e : listaEmpleados){
            if (e.getDocumento().equals(documento)){
                return true;
            }
        }
        return false;
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

    public List<Empleado> getListaEmpleados() {
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

    public void setAtracciones(List<Atraccion> atracciones) {
        for (Zona z : listaZonas){
            for (Atraccion a : atracciones){
                if (a.getNombreZona().equals(z.getNombreZona())){
                    z.agregarAtraccion(a);
                }
            }
            for (Operador o : obtenerListaOperadores()){
                if (!o.getNombreZonaAsignada().isBlank()){
                    if (o.getNombreZonaAsignada().equals(z.getNombreZona())){
                        z.asignarOperador(o, o.getNombreAtraccionAsignada());
                    }
                }
            }
        }        
    }
}