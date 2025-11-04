package cl.municipalidadchillan.dideco.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.municipalidadchillan.dideco.model.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
    List<Actividad> findByProgramaIdPrograma(Integer idPrograma);
}
