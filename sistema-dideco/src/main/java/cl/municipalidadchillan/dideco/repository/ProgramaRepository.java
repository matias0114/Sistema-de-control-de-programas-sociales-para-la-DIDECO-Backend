package cl.municipalidadchillan.dideco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.Programa;

public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
    
}