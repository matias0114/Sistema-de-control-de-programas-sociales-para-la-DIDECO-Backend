package cl.municipalidadchillan.dideco.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidadchillan.dideco.model.ObservacionPrograma;
import cl.municipalidadchillan.dideco.model.Programa;
import cl.municipalidadchillan.dideco.model.Usuario;
import cl.municipalidadchillan.dideco.service.ObservacionProgramaService;
import cl.municipalidadchillan.dideco.service.ProgramaService;
import cl.municipalidadchillan.dideco.service.UsuarioService;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/programas/{idPrograma}/observaciones")
public class ObservacionProgramaController {

    @Autowired
    private ObservacionProgramaService observacionProgramaService;

    @Autowired
    private ProgramaService programaService;

    @Autowired
    private UsuarioService usuarioService;

    // Listar todas las observaciones de un programa
    @GetMapping
    public List<ObservacionPrograma> listarPorPrograma(@PathVariable int idPrograma) {
        return observacionProgramaService.findByIdPrograma(idPrograma);
    }

    // Crear nueva observación
    @PostMapping
    public ResponseEntity<ObservacionPrograma> agregar(
            @PathVariable int idPrograma,
            @RequestBody ObservacionPrograma observacion) {

        Programa programa = programaService.findById(idPrograma);
        if (programa == null) {
            return ResponseEntity.notFound().build();
        }

        observacion.setPrograma(programa);
        observacion.setFechaCreacion(LocalDateTime.now());

        // Como usuarioAutor es String, simplemente se usa lo que envía frontend
        if (observacion.getUsuarioAutor() == null || observacion.getUsuarioAutor().isEmpty()) {
            observacion.setUsuarioAutor("Anónimo");
        }

        ObservacionPrograma creada = observacionProgramaService.save(observacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }


    // Eliminar observación por ID
    @DeleteMapping("/{idObservacion}")
    public ResponseEntity<Void> eliminar(@PathVariable int idPrograma, @PathVariable int idObservacion) {
        // Opcional: validar que la observación pertenece al programa antes de eliminar
        observacionProgramaService.deleteById(idObservacion);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{idObservacion}/leer")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable int idPrograma, @PathVariable int idObservacion) {
        ObservacionPrograma obs = observacionProgramaService.findById(idObservacion);
        if (obs == null || obs.getPrograma().getIdPrograma() != idPrograma) {
            return ResponseEntity.notFound().build();
        }
        obs.setLeida(true);
        observacionProgramaService.save(obs);
        return ResponseEntity.noContent().build();
    }

}