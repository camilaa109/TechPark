package co.edu.uniquindio.techpark.model;

import java.time.LocalDateTime;

public record Notificacion(String id, String titulo, String mensaje, LocalDateTime fecha) implements Notificable {

    @Override
    public void enviar(Visitante visitante) {
        visitante.recibirNotificacion(this);
    }
    
}
