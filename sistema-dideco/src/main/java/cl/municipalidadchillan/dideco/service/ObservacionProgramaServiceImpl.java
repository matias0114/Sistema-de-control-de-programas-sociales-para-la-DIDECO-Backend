package cl.municipalidadchillan.dideco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.ObservacionPrograma;
import cl.municipalidadchillan.dideco.repository.ObservacionProgramaRepository;

@Service
public class ObservacionProgramaServiceImpl implements ObservacionProgramaService {

    @Autowired
    private ObservacionProgramaRepository observacionProgramaRepository;

    @Override
    public List<ObservacionPrograma> findByIdPrograma(int idPrograma) {
        return observacionProgramaRepository.findByPrograma_IdProgramaOrderByFechaCreacionDesc(idPrograma);
    }

    @Override
    public ObservacionPrograma save(ObservacionPrograma observacion) {
        return observacionProgramaRepository.save(observacion);
    }

    @Override
    public void deleteById(int idObservacion) {
        observacionProgramaRepository.deleteById(idObservacion);
    }

    @Override
    public ObservacionPrograma findById(int id) {
        return observacionProgramaRepository.findById(id).orElse(null);
    }

}