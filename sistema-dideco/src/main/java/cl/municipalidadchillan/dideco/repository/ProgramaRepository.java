package cl.municipalidadchillan.dideco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.Programa;

public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
    List<Programa> findByEstado(String estado);
}