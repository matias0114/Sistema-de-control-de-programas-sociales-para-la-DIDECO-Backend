package cl.municipalidadchillan.dideco.service;

import java.util.List;
import cl.municipalidadchillan.dideco.model.Actividad;

public interface ActividadService {
    List<Actividad> obtenerTodas();
    Actividad obtenerPorId(Integer id);
    Actividad guardar(Actividad actividad);
    void eliminar(Integer id);
    List<Actividad> obtenerPorPrograma(Integer idPrograma); // Nuevo método
}
