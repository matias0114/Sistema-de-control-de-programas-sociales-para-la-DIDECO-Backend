package cl.municipalidadchillan.dideco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.Actividad;
import cl.municipalidadchillan.dideco.repository.ActividadRepository;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Override
    public List<Actividad> obtenerTodas() {
        return actividadRepository.findAll();
    }

    @Override
    public Actividad obtenerPorId(Integer id) {
        Optional<Actividad> actividad = actividadRepository.findById(id);
        return actividad.orElse(null);
    }

    @Override
    public Actividad guardar(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public void eliminar(Integer id) {
        actividadRepository.deleteById(id);
    }
}
