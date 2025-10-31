package cl.municipalidadchillan.dideco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.BeneficiarioPrograma;

public interface BeneficiarioProgramaRepository extends JpaRepository<BeneficiarioPrograma, Integer> {
    List<BeneficiarioPrograma> findByPrograma_IdPrograma(int idPrograma);
}