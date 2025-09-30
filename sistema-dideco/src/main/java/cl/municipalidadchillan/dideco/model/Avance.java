package cl.municipalidadchillan.dideco.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "porcentaje_avance", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentajeAvance;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad actividad;

    public Avance() {}

    public Avance(Actividad actividad, Usuario usuario, String descripcion, LocalDate fechaAvance, int idAvance, BigDecimal porcentajeAvance) {
        this.actividad = actividad;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.fechaAvance = fechaAvance;
        this.idAvance = idAvance;
        this.porcentajeAvance = porcentajeAvance;
    }

    public int getIdAvance() { return idAvance; }
    public void setIdAvance(int idAvance) { this.idAvance = idAvance; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaAvance() { return fechaAvance; }
    public void setFechaAvance(LocalDate fechaAvance) { this.fechaAvance = fechaAvance; }
    public BigDecimal getPorcentajeAvance() { return porcentajeAvance; }
    public void setPorcentajeAvance(BigDecimal porcentajeAvance) { this.porcentajeAvance = porcentajeAvance; }
    public Actividad getActividad() { return actividad; }
    public void setActividad(Actividad actividad) { this.actividad = actividad; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

}
