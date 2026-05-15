package co.edu.uniquindio.techpark.model;

public record RequisitosSeguridad(int edadMinima, double alturaMinima) {
    public boolean esApto (int edadVisitante, double alturaVisitante){
        return edadVisitante >= edadMinima && alturaVisitante >= alturaMinima;
    }     
}
