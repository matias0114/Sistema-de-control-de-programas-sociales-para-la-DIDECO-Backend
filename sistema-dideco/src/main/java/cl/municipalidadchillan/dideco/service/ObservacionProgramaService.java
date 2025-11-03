package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.ObservacionPrograma;

public interface ObservacionProgramaService {
    List<ObservacionPrograma> findByIdPrograma(int idPrograma);
    ObservacionPrograma save(ObservacionPrograma observacion);
    void deleteById(int idObservacion);
    ObservacionPrograma findById(int id);

}