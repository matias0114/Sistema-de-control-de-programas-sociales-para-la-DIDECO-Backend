package cl.municipalidadchillan.dideco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
    
}
