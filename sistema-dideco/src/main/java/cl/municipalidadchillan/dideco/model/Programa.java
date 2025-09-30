package cl.municipalidadchillan.dideco.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "programa")
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programa")
    private int idPrograma;

    @Column(name = "nombre_programa", length = 150, nullable = false)
    private String nombrePrograma;

    @Column(name = "estado", length = 50)
    private String estado = "Activo";

    @OneToMany(mappedBy = "programa")
    @JsonIgnore
    private List<Actividad> actividades;

    @OneToMany(mappedBy = "programa")
    @JsonIgnore
    private List<Presupuesto> presupuestos;

    public Programa() {}

    public Programa(String nombrePrograma, String estado) {
        this.nombrePrograma = nombrePrograma;
        this.estado = estado;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(List<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }
}