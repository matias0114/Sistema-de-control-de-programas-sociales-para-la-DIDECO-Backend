package cl.municipalidadchillan.dideco.service;

import java.util.List;
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
        return actividadRepository.findById(id).orElse(null);
    }
    
    @Override
    public Actividad guardar(Actividad actividad) {
        return actividadRepository.save(actividad);
    }
    
    @Override
    public void eliminar(Integer id) {
        actividadRepository.deleteById(id);
    }

    @Override
    public List<Actividad> obtenerPorPrograma(Integer idPrograma) {
        return actividadRepository.findByProgramaIdPrograma(idPrograma);
    }
}
