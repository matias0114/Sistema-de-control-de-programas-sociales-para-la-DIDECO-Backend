package cl.municipalidadchillan.dideco.controller;

import cl.municipalidadchillan.dideco.model.Avance;
import cl.municipalidadchillan.dideco.service.AvanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
}