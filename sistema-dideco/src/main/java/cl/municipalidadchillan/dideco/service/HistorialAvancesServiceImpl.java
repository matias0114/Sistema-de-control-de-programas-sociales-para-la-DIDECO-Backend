package cl.municipalidadchillan.dideco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.HistorialAvances;
import cl.municipalidadchillan.dideco.repository.HistorialAvancesRepository;

@Service
public class HistorialAvancesServiceImpl implements HistorialAvancesService {

    @Autowired
    private HistorialAvancesRepository historialAvancesRepository;

    @Override
    public List<HistorialAvances> findAll() {
        return historialAvancesRepository.findAll();
    }

    @Override
    public HistorialAvances findById(int id) {
        return historialAvancesRepository.findById(id).orElse(null);
    }

    @Override
    public HistorialAvances save(HistorialAvances historialAvances) {
        return historialAvancesRepository.save(historialAvances);
    }

    @Override
    public void deleteById(int id) {
        historialAvancesRepository.deleteById(id);
    }
}