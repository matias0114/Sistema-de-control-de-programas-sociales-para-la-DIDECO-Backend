package cl.municipalidadchillan.dideco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidadchillan.dideco.model.Presupuesto;
import cl.municipalidadchillan.dideco.service.PresupuestoService;

@RestController
@RequestMapping("/presupuestos")
public class PresupuestoController {

    @Autowired
    private PresupuestoService presupuestoService;

    @GetMapping
    public List<Presupuesto> getAll() {
        return presupuestoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Presupuesto> getById(@PathVariable int id) {
        Presupuesto presupuesto = presupuestoService.findById(id);
        if (presupuesto != null) {
            return ResponseEntity.ok(presupuesto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Presupuesto> create(@RequestBody Presupuesto presupuesto) {
        Presupuesto nuevo = presupuestoService.save(presupuesto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Presupuesto> update(@PathVariable int id, @RequestBody Presupuesto presupuesto) {
        Presupuesto existente = presupuestoService.findById(id);
        if (existente != null) {
            presupuesto.setIdPresupuesto(id);
            return ResponseEntity.ok(presupuestoService.save(presupuesto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        presupuestoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/programa/{programaId}")
    public List<Presupuesto> getByProgramaId(@PathVariable int programaId) {
        return presupuestoService.findByProgramaId(programaId);
    }
}