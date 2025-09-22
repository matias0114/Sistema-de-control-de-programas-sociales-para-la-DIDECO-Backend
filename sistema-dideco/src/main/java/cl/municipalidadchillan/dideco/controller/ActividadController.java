package cl.municipalidadchillan.dideco.controller;

import cl.municipalidadchillan.dideco.model.Actividad;
import cl.municipalidadchillan.dideco.service.ActividadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/actividades")
public class ActividadController {

    private final ActividadService actividadService;

    @Autowired
    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @GetMapping
    public List<Actividad> obtenerTodas() {
        return actividadService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Actividad obtenerPorId(@PathVariable Integer id) {
        return actividadService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<Actividad> crearActividad(@RequestBody @Valid Actividad actividad) {
        Actividad nueva = actividadService.guardar(actividad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Integer id) {
        actividadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}