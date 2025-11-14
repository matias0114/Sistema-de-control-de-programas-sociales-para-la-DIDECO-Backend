package cl.municipalidadchillan.dideco.dto;

import cl.municipalidadchillan.dideco.model.Usuario;

/**
 * DTO para Usuario sin exponer la contraseña
 * Se utiliza para retornar información de usuario de forma segura
 */
public class UsuarioDTO {
    private int idUsuario;
    private String nombreUsuario;
    private String correo;
    private int idRol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(int idUsuario, String nombreUsuario, String correo, int idRol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.idRol = idRol;
    }

    /**
     * Convierte una entidad Usuario a UsuarioDTO (sin contraseña)
     */
    public static UsuarioDTO fromEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setCorreo(usuario.getCorreo());
        dto.setIdRol(usuario.getIdRol());
        return dto;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
