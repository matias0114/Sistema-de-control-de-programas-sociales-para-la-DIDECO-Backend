package cl.municipalidadchillan.dideco.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(name = "nombre_usuario", length = 100, nullable = false)
    private String nombreUsuario;

    @Column(name = "correo", length = 100, nullable = false)
    private String correo;

    @Column(name = "contraseña", length = 255, nullable = false)
    private String contrasena;

    @Column(name = "id_rol", nullable = false)
    private int idRol;

    @OneToMany(mappedBy = "usuario")
    private List<Programa> programas; 

    @OneToMany(mappedBy = "usuario")
    private List<Avance> avances;


    public Usuario() {}

    public Usuario(String nombreUsuario, String correo, String contrasena, int idRol) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idRol = idRol;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }
}