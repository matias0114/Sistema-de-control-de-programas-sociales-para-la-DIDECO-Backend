package cl.municipalidadchillan.dideco.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "avance")
public class Avance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avance")
    private int idAvance;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha_avance", nullable = false)
    private LocalDate fechaAvance;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "objetivos_alcanzados", columnDefinition = "text")
    private String objetivosAlcanzados;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad actividad;

    @OneToMany(mappedBy = "avance")
    @JsonIgnore
    private List<DocumentoRespaldo> documentos;

    public List<DocumentoRespaldo> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoRespaldo> documentos) {
        this.documentos = documentos;
    }

    public Avance() {}

    public Avance(Actividad actividad, Usuario usuario, String descripcion, LocalDate fechaAvance, int idAvance, String estado, String objetivosAlcanzados) {
        this.actividad = actividad;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.fechaAvance = fechaAvance;
        this.idAvance = idAvance;
        this.estado = estado;
        this.objetivosAlcanzados = objetivosAlcanzados;
    }

    public int getIdAvance() { return idAvance; }
    public void setIdAvance(int idAvance) { this.idAvance = idAvance; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaAvance() { return fechaAvance; }
    public void setFechaAvance(LocalDate fechaAvance) { this.fechaAvance = fechaAvance; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObjetivosAlcanzados() { return objetivosAlcanzados; }
    public void setObjetivosAlcanzados(String objetivosAlcanzados) { this.objetivosAlcanzados = objetivosAlcanzados; }

    public Actividad getActividad() { return actividad; }
    public void setActividad(Actividad actividad) { this.actividad = actividad; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
