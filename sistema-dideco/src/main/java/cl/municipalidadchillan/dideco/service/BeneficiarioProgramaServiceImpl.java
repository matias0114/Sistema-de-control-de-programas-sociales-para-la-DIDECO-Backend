package cl.municipalidadchillan.dideco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.BeneficiarioPrograma;
import cl.municipalidadchillan.dideco.repository.BeneficiarioProgramaRepository;

@Service
public class BeneficiarioProgramaServiceImpl implements BeneficiarioProgramaService {

    @Autowired
    private BeneficiarioProgramaRepository beneficiarioProgramaRepository;

    @Override
    public List<BeneficiarioPrograma> findAll() {
        return beneficiarioProgramaRepository.findAll();
    }

    @Override
    public BeneficiarioPrograma findById(int id) {
        return beneficiarioProgramaRepository.findById(id).orElse(null);
    }

    @Override
    public BeneficiarioPrograma save(BeneficiarioPrograma beneficiario) {
        return beneficiarioProgramaRepository.save(beneficiario);
    }

    @Override
    public void deleteById(int id) {
        beneficiarioProgramaRepository.deleteById(id);
    }

    @Override
    public List<BeneficiarioPrograma> findByProgramaId(int idPrograma) {
        return beneficiarioProgramaRepository.findByPrograma_IdPrograma(idPrograma);
    }
}