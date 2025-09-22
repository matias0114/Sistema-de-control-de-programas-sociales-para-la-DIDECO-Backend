package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.Avance;

public interface AvanceService {
    List<Avance> obtenerTodos();
    Avance obtenerPorId(Integer id);
    Avance guardar(Avance avance);
    void eliminar(Integer id);
}