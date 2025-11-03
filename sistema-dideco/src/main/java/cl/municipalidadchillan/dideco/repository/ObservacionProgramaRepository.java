package cl.municipalidadchillan.dideco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.ObservacionPrograma;
import cl.municipalidadchillan.dideco.model.Programa;

public interface ObservacionProgramaRepository extends JpaRepository<ObservacionPrograma, Integer> {
    // Buscar todas las observaciones por programa (ordenadas opcionalmente por fecha)
    List<ObservacionPrograma> findByProgramaOrderByFechaCreacionDesc(Programa programa);

    // Alternativamente, por idPrograma:
    List<ObservacionPrograma> findByPrograma_IdProgramaOrderByFechaCreacionDesc(int idPrograma);
}