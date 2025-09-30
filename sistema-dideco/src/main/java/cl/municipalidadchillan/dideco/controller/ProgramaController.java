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

import cl.municipalidadchillan.dideco.model.Programa;
import cl.municipalidadchillan.dideco.service.ProgramaService;

@RestController
@RequestMapping("/programas")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;

    @GetMapping
    public List<Programa> getAll() {
        return programaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Programa> getById(@PathVariable int id) {
        Programa programa = programaService.findById(id);
        if (programa != null) {
            return ResponseEntity.ok(programa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Programa> create(@RequestBody Programa programa) {
        Programa nuevo = programaService.save(programa);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Programa> update(@PathVariable int id, @RequestBody Programa programa) {
        Programa existente = programaService.findById(id);
        if (existente != null) {
            programa.setIdPrograma(id);
            return ResponseEntity.ok(programaService.save(programa));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        programaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public List<Programa> findByEstado(@PathVariable String estado) {
        return programaService.findByEstado(estado);
    }
}