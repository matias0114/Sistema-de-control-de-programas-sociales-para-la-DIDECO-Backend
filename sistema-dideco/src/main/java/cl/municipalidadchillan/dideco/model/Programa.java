package cl.municipalidadchillan.dideco.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tipo_programa", length = 100)
    private String tipoPrograma;

    @Column(name = "oficina_responsable", length = 150)
    private String oficinaResponsable;

    @Column(name = "contacto_encargado", length = 150)
    private String contactoEncargado;

    @Column(name = "requisitos_ingreso", columnDefinition = "TEXT")
    private String requisitosIngreso;

    @Column(name = "beneficios", columnDefinition = "TEXT")
    private String beneficios;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "cupos")
    private Integer cupos;

    @Column(name = "metas", columnDefinition = "TEXT")
    private String metas;

    @OneToMany(mappedBy = "programa")
    @JsonIgnore
    private List<Actividad> actividades;

    @OneToMany(mappedBy = "programa")
    @JsonIgnore
    private List<Presupuesto> presupuestos;

    @OneToMany(mappedBy = "programa") 
    @JsonIgnore
    private List<BeneficiarioPrograma> beneficiarios;


    @OneToMany(mappedBy = "programa", cascade = CascadeType.ALL)
    @JsonIgnore 
    private List<ObservacionPrograma> observaciones;



    public Programa() {}

    public Programa(String nombrePrograma, String estado) {
        this.nombrePrograma = nombrePrograma;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdPrograma() { return idPrograma; }
    public void setIdPrograma(int idPrograma) { this.idPrograma = idPrograma; }
    public String getNombrePrograma() { return nombrePrograma; }
    public void setNombrePrograma(String nombrePrograma) { this.nombrePrograma = nombrePrograma; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getTipoPrograma() { return tipoPrograma; }
    public void setTipoPrograma(String tipoPrograma) { this.tipoPrograma = tipoPrograma; }
    public String getOficinaResponsable() { return oficinaResponsable; }
    public void setOficinaResponsable(String oficinaResponsable) { this.oficinaResponsable = oficinaResponsable; }
    public String getContactoEncargado() { return contactoEncargado; }
    public void setContactoEncargado(String contactoEncargado) { this.contactoEncargado = contactoEncargado; }
    public String getRequisitosIngreso() { return requisitosIngreso; }
    public void setRequisitosIngreso(String requisitosIngreso) { this.requisitosIngreso = requisitosIngreso; }
    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Integer getCupos() { return cupos; }
    public void setCupos(Integer cupos) { this.cupos = cupos; }
    public String getMetas() { return metas; }
    public void setMetas(String metas) { this.metas = metas; }
    public List<Actividad> getActividades() { return actividades; }
    public void setActividades(List<Actividad> actividades) { this.actividades = actividades; }
    public List<Presupuesto> getPresupuestos() { return presupuestos; }
    public void setPresupuestos(List<Presupuesto> presupuestos) { this.presupuestos = presupuestos; }
    public List<BeneficiarioPrograma> getBeneficiarios() {
        return beneficiarios;
    }
    public void setBeneficiarios(List<BeneficiarioPrograma> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }
    public List<ObservacionPrograma> getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(List<ObservacionPrograma> observaciones) {
        this.observaciones = observaciones;
    }

}