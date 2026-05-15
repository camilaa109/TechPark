package co.edu.uniquindio.techpark.model;

public enum TipoTicket {
    GENERAL(3),
    FAMILIAR(2),
    FAST_PASS(1);

    private int nivel;
    TipoTicket(int nivel){this.nivel = nivel;}
    public int getNivel(){return nivel;}
}
