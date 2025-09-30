package cl.municipalidadchillan.dideco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.Programa;
import cl.municipalidadchillan.dideco.repository.ProgramaRepository;

@Service
public class ProgramaServiceImpl implements ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    @Override
    public List<Programa> findAll() {
        return programaRepository.findAll();
    }

    @Override
    public Programa findById(int id) {
        return programaRepository.findById(id).orElse(null);
    }

    @Override
    public Programa save(Programa programa) {
        return programaRepository.save(programa);
    }

    @Override
    public void deleteById(int id) {
        programaRepository.deleteById(id);
    }

    @Override
    public List<Programa> findByEstado(String estado) {
        return programaRepository.findByEstado(estado);
    }
}