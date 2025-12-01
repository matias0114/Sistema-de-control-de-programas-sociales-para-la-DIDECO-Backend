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
@Table(name = "presupuesto")
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presupuesto")
    private int idPresupuesto;

    @Column(name = "monto_asignado", precision = 12, scale = 2, nullable = false)
    private BigDecimal montoAsignado;

    @Column(name = "monto_ejecutado", precision = 12, scale = 2)
    private BigDecimal montoEjecutado = BigDecimal.ZERO;

    @Column(name = "fuente_presupuesto", length = 255, nullable = false)
    private String fuentePresupuesto;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private Programa programa;

    public Presupuesto() {}

    public Presupuesto(BigDecimal montoAsignado, BigDecimal montoEjecutado, LocalDate fechaRegistro, Programa programa) {
        this.montoAsignado = montoAsignado;
        this.montoEjecutado = montoEjecutado;
        this.fechaRegistro = fechaRegistro;
        this.programa = programa;
    }

    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public BigDecimal getMontoAsignado() {
        return montoAsignado;
    }

    public void setMontoAsignado(BigDecimal montoAsignado) {
        this.montoAsignado = montoAsignado;
    }

    public BigDecimal getMontoEjecutado() {
        return montoEjecutado;
    }

    public void setMontoEjecutado(BigDecimal montoEjecutado) {
        this.montoEjecutado = montoEjecutado;
    }

    public String getFuentePresupuesto() {
        return fuentePresupuesto;
    }

    public void setFuentePresupuesto(String fuentePresupuesto) {
        this.fuentePresupuesto = fuentePresupuesto;
    }


    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }
}