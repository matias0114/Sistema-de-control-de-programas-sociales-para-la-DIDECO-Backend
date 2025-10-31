package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.BeneficiarioPrograma;

public interface BeneficiarioProgramaService {
    List<BeneficiarioPrograma> findAll();
    BeneficiarioPrograma findById(int id);
    BeneficiarioPrograma save(BeneficiarioPrograma beneficiario);
    void deleteById(int id);
    List<BeneficiarioPrograma> findByProgramaId(int idPrograma);
}