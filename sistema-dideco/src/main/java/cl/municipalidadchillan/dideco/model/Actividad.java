package cl.municipalidadchillan.dideco.model;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_termino")
    private LocalDate fechaTermino;

    // La relación con Programa debe estar definida porque idprograma ES obligatorio (clave foránea en tu BDD)
    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private Programa programa;

    @OneToMany(mappedBy = "actividad")
    private List<Avance> avances;

    public Actividad() {}

    public Actividad(List<Avance> avances, LocalDate fechaInicio, LocalDate fechaTermino, int idActividad, String nombreActividad, Programa programa) {
        this.avances = avances;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.programa = programa;
    }

    // Getters y setters (adaptados)

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public List<Avance> getAvances() {
        return avances;
    }

    public void setAvances(List<Avance> avances) {
        this.avances = avances;
    }

}
