package cl.municipalidadchillan.dideco.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "beneficiario_programa")
public class BeneficiarioPrograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_beneficiario")
    private int idBeneficiario;

    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private Programa programa;

    @Column(name = "nombre_completo", length = 150, nullable = true)
    private String nombreCompleto;

    @Column(name = "rut", length = 15, nullable = true)
    private String rut;

    @Column(name = "telefono", length = 30, nullable = true)
    private String telefono;

    @Column(name = "direccion", length = 255, nullable = true)
    private String direccion;

    public BeneficiarioPrograma() {}

    // Getters y setters
    public int getIdBeneficiario() { return idBeneficiario; }
    public void setIdBeneficiario(int idBeneficiario) { this.idBeneficiario = idBeneficiario; }

    public Programa getPrograma() { return programa; }
    public void setPrograma(Programa programa) { this.programa = programa; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
