package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.Presupuesto;

public interface PresupuestoService {
    
    List<Presupuesto> findAll();
    
    Presupuesto findById(int id);
    
    Presupuesto save(Presupuesto presupuesto);
    
    void deleteById(int id);
    
    List<Presupuesto> findByProgramaId(int programaId);
    
}