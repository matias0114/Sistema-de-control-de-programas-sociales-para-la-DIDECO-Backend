package cl.municipalidadchillan.dideco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.DocumentoRespaldo;

public interface DocumentoRespaldoRepository extends JpaRepository<DocumentoRespaldo, Integer> {
    // Método para listar documentos por id de avance
    List<DocumentoRespaldo> findByAvance_IdAvance(int idAvance);
}