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

import cl.municipalidadchillan.dideco.model.Actividad;
import cl.municipalidadchillan.dideco.model.Programa;
import cl.municipalidadchillan.dideco.service.ActividadService;
import cl.municipalidadchillan.dideco.service.NotificacionService;
import cl.municipalidadchillan.dideco.service.ProgramaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/actividades")
public class ActividadController {

    private final ActividadService actividadService;
    private final NotificacionService notificacionService;
    private final ProgramaService programaService;

    @Autowired
    public ActividadController(ActividadService actividadService, NotificacionService notificacionService, ProgramaService programaService) {
        this.actividadService = actividadService;
        this.notificacionService = notificacionService;
        this.programaService = programaService;
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
        
        // Obtener el programa completo desde la base de datos
        Programa programa = programaService.findById(nueva.getPrograma().getIdPrograma());
        
        String nombrePrograma = (programa != null && programa.getNombrePrograma() != null) 
            ? programa.getNombrePrograma() 
            : "Desconocido";
            
        String mensaje = String.format("Se ha creado una nueva actividad '%s' en el programa '%s'", 
            nueva.getNombreActividad(), 
            nombrePrograma);
        notificacionService.notificarVisualizadores("NUEVA_ACTIVIDAD", mensaje, nueva.getIdActividad());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Integer id) {
        actividadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actividad> actualizarActividad(@PathVariable Integer id, @RequestBody @Valid Actividad actividad) {
        Actividad existente = actividadService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizamos los campos editables
        existente.setNombreActividad(actividad.getNombreActividad());
        existente.setDescripcion(actividad.getDescripcion());
        existente.setFechaInicio(actividad.getFechaInicio());
        existente.setFechaTermino(actividad.getFechaTermino());
        existente.setMontoAsignado(actividad.getMontoAsignado());
        existente.setResponsable(actividad.getResponsable());
        existente.setMetas(actividad.getMetas());
        existente.setPrograma(actividad.getPrograma());

        Actividad actualizado = actividadService.guardar(existente);
        return ResponseEntity.ok(actualizado);
    }
}