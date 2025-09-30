package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.Rol;

public interface RolService {
    List<Rol> findAll();
    Rol findById(int id);
    Rol save(Rol rol);
    void deleteById(int id);
}