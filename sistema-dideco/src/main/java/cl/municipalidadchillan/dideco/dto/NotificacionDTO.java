package cl.municipalidadchillan.dideco.dto;

import java.time.LocalDateTime;

public class NotificacionDTO {
    private Integer idNotificacion;
    private String tipo;
    private String mensaje;
    private Integer idReferencia;
    private Boolean leida;
    private LocalDateTime fechaCreacion;
    private Integer idUsuario;
    private String nombreUsuario;

    // Constructores
    public NotificacionDTO() {}

    public NotificacionDTO(Integer idNotificacion, String tipo, String mensaje, Integer idReferencia, 
                          Boolean leida, LocalDateTime fechaCreacion, Integer idUsuario, String nombreUsuario) {
        this.idNotificacion = idNotificacion;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.idReferencia = idReferencia;
        this.leida = leida;
        this.fechaCreacion = fechaCreacion;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }

    // Getters y Setters
    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}