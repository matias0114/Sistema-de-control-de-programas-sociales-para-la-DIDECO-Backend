package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.HistorialAvances;

public interface HistorialAvancesService {
    List<HistorialAvances> findAll();
    HistorialAvances findById(int id);
    HistorialAvances save(HistorialAvances historialAvances);
    void deleteById(int id);
}