package cl.municipalidadchillan.dideco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.Rol;
import cl.municipalidadchillan.dideco.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public Rol findById(int id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void deleteById(int id) {
        rolRepository.deleteById(id);
    }
}