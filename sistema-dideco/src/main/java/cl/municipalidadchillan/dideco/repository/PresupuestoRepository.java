package cl.municipalidadchillan.dideco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.Presupuesto;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Integer> {

    public List<Presupuesto> findByProgramaIdPrograma(int programaId);
    
}