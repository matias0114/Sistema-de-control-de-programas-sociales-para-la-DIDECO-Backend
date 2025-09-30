package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.Programa;

public interface ProgramaService {
    
    List<Programa> findAll();
    
    Programa findById(int id);
    
    Programa save(Programa programa);
    
    void deleteById(int id);
    
    List<Programa> findByEstado(String estado);
    
}