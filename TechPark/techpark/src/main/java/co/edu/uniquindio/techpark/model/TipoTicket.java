package co.edu.uniquindio.techpark.model;

public enum TipoTicket {
    GENERAL(3, 100000),
    FAMILIAR(2, 100000),
    FAST_PASS(1, 150000);

    private int nivel;
    private double precio;
    TipoTicket(int nivel, double precio){this.nivel = nivel; this.precio = precio;}
    public int getNivel(){return nivel;}
    public double getPrecio(){return precio;}
}
