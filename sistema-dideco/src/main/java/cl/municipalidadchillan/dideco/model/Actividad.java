package cl.municipalidadchillan.dideco.model;

import java.math.BigDecimal;
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
@Table(name = "actividad")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private int idActividad;

    @Column(name = "nombre_actividad", length = 150, nullable = false)
    private String nombreActividad;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_termino")
    private LocalDate fechaTermino;

    @Column(name = "monto_asignado", precision = 12, scale = 2)
    private BigDecimal montoAsignado;

    @Column(name = "responsable", length = 150)
    private String responsable;

    @Column(name = "metas", columnDefinition = "text")
    private String metas;

    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private Programa programa;

    @OneToMany(mappedBy = "actividad")
    @JsonIgnore
    private List<Avance> avances;

    public Actividad() {}

    public Actividad(List<Avance> avances, LocalDate fechaInicio, LocalDate fechaTermino, int idActividad, String nombreActividad, String descripcion, BigDecimal montoAsignado, String responsable, String metas, Programa programa) {
        this.avances = avances;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.descripcion = descripcion;
        this.montoAsignado = montoAsignado;
        this.responsable = responsable;
        this.metas = metas;
        this.programa = programa;
    }

    public int getIdActividad() { return idActividad; }
    public void setIdActividad(int idActividad) { this.idActividad = idActividad; }
    public String getNombreActividad() { return nombreActividad; }
    public void setNombreActividad(String nombreActividad) { this.nombreActividad = nombreActividad; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaTermino() { return fechaTermino; }
    public void setFechaTermino(LocalDate fechaTermino) { this.fechaTermino = fechaTermino; }
    public BigDecimal getMontoAsignado() { return montoAsignado; }
    public void setMontoAsignado(BigDecimal montoAsignado) { this.montoAsignado = montoAsignado; }
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public String getMetas() { return metas; }
    public void setMetas(String metas) { this.metas = metas; }
    public Programa getPrograma() { return programa; }
    public void setPrograma(Programa programa) { this.programa = programa; }
    public List<Avance> getAvances() { return avances; }
    public void setAvances(List<Avance> avances) { this.avances = avances; }
}