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

import cl.municipalidadchillan.dideco.model.Avance;
import cl.municipalidadchillan.dideco.service.AvanceService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avances")
public class AvanceController {

    private final AvanceService avanceService;

    @Autowired
    public AvanceController(AvanceService avanceService) {
        this.avanceService = avanceService;
    }

    @GetMapping
    public List<Avance> obtenerTodos() {
        return avanceService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Avance obtenerPorId(@PathVariable Integer id) {
        return avanceService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<Avance> crearAvance(@RequestBody @Valid Avance avance) {
        Avance nuevo = avanceService.guardar(avance);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAvance(@PathVariable Integer id) {
        avanceService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avance> actualizarAvance(@PathVariable Integer id, @RequestBody @Valid Avance avance) {
        Avance existente = avanceService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setDescripcion(avance.getDescripcion());
        existente.setEstado(avance.getEstado());
        existente.setFechaAvance(avance.getFechaAvance());
        existente.setObjetivosAlcanzados(avance.getObjetivosAlcanzados());
        existente.setActividad(avance.getActividad());
        existente.setUsuario(avance.getUsuario());

        Avance actualizado = avanceService.guardar(existente);
        return ResponseEntity.ok(actualizado);
    }

}