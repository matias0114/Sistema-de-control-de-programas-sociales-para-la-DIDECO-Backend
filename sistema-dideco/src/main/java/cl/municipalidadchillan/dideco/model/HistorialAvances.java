package cl.municipalidadchillan.dideco.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_avances")
public class HistorialAvances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private int idHistorial;

    @ManyToOne
    @JoinColumn(name = "id_avance")
    private Avance avance;

    @Column(name = "campo_modificado", length = 100, nullable = false)
    private String campoModificado;

    @Column(name = "valor_anterior")
    private String valorAnterior;

    @Column(name = "valor_nuevo")
    private String valorNuevo;

    @Column(name = "fecha_modificacion")
    private Timestamp fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }

    public Avance getAvance() { return avance; }
    public void setAvance(Avance avance) { this.avance = avance; }

    public String getCampoModificado() { return campoModificado; }
    public void setCampoModificado(String campoModificado) { this.campoModificado = campoModificado; }

    public String getValorAnterior() { return valorAnterior; }
    public void setValorAnterior(String valorAnterior) { this.valorAnterior = valorAnterior; }

    public String getValorNuevo() { return valorNuevo; }
    public void setValorNuevo(String valorNuevo) { this.valorNuevo = valorNuevo; }

    public Timestamp getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(Timestamp fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
