package cl.municipalidadchillan.dideco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.Presupuesto;
import cl.municipalidadchillan.dideco.repository.PresupuestoRepository;

@Service
public class PresupuestoServiceImpl implements PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Override
    public List<Presupuesto> findAll() {
        return presupuestoRepository.findAll();
    }

    @Override
    public Presupuesto findById(int id) {
        return presupuestoRepository.findById(id).orElse(null);
    }

    @Override
    public Presupuesto save(Presupuesto presupuesto) {
        return presupuestoRepository.save(presupuesto);
    }

    @Override
    public void deleteById(int id) {
        presupuestoRepository.deleteById(id);
    }

    @Override
    public List<Presupuesto> findByProgramaId(int programaId) {
        // Necesitarás agregar este método al repository
        return presupuestoRepository.findByProgramaIdPrograma(programaId);
    }
}