package cl.municipalidadchillan.dideco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.Avance;
import cl.municipalidadchillan.dideco.repository.AvanceRepository;

@Service
public class AvanceServiceImpl implements AvanceService {

    @Autowired
    private AvanceRepository avanceRepository;

    @Override
    public List<Avance> obtenerTodos() {
        return avanceRepository.findAll();
    }

    @Override
    public Avance obtenerPorId(Integer id) {
        Optional<Avance> avance = avanceRepository.findById(id);
        return avance.orElse(null);
    }

    @Override
    public Avance guardar(Avance avance) {
        return avanceRepository.save(avance);
    }

    @Override
    public void eliminar(Integer id) {
        avanceRepository.deleteById(id);
    }
}