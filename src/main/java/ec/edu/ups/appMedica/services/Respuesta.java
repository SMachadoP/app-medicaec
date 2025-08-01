package ec.edu.ups.appMedica.services;

public class Respuesta {
    public String status;
    public String mensaje; // Cambiar de "message" a "mensaje" para consistencia
    
    // Constructor vacío requerido
    public Respuesta() {
    }
    
    public Respuesta(String status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    // Mantener compatibilidad con "message"
    public String getMessage() {
        return mensaje;
    }
    
    public void setMessage(String message) {
        this.mensaje = message;
    }
    
    @Override
    public String toString() {
        return "Respuesta{status='" + status + "', mensaje='" + mensaje + "'}";
    }
}
