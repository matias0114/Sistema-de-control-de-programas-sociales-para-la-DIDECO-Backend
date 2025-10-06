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

import cl.municipalidadchillan.dideco.model.HistorialAvances;
import cl.municipalidadchillan.dideco.service.HistorialAvancesService;

@RestController
@RequestMapping("/historial-avances")
public class HistorialAvancesController {

    @Autowired
    private HistorialAvancesService historialAvancesService;

    @GetMapping
    public List<HistorialAvances> getAll() {
        return historialAvancesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialAvances> getById(@PathVariable int id) {
        HistorialAvances historial = historialAvancesService.findById(id);
        if (historial != null) {
            return ResponseEntity.ok(historial);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<HistorialAvances> create(@RequestBody HistorialAvances historialAvances) {
        HistorialAvances nuevo = historialAvancesService.save(historialAvances);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialAvances> update(@PathVariable int id, @RequestBody HistorialAvances historialAvances) {
        HistorialAvances actual = historialAvancesService.findById(id);
        if (actual != null) {
            historialAvances.setIdHistorial(id);
            return ResponseEntity.ok(historialAvancesService.save(historialAvances));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        historialAvancesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}