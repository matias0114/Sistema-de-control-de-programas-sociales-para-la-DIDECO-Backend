package cl.municipalidadchillan.dideco.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.municipalidadchillan.dideco.model.Actividad;
import cl.municipalidadchillan.dideco.model.Programa;
import cl.municipalidadchillan.dideco.service.ActividadService;
import cl.municipalidadchillan.dideco.service.NotificacionService;
import cl.municipalidadchillan.dideco.service.PDFExportService;
import cl.municipalidadchillan.dideco.service.ProgramaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/actividades")
public class ActividadController {

    private final ActividadService actividadService;
    private final NotificacionService notificacionService;
    private final ProgramaService programaService;
    private final PDFExportService pdfExportService;
    private static final DateTimeFormatter FECHA_ARCHIVO_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    @Autowired
    public ActividadController(
            ActividadService actividadService, 
            NotificacionService notificacionService, 
            ProgramaService programaService,
            PDFExportService pdfExportService) {
        this.actividadService = actividadService;
        this.notificacionService = notificacionService;
        this.programaService = programaService;
        this.pdfExportService = pdfExportService;
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

    @GetMapping("/exportar-pdf")
    public ResponseEntity<byte[]> exportarPDF(@RequestParam(required = false) Integer idPrograma) {
        List<Actividad> actividades;
        String nombreArchivo;
        String nombrePrograma;
        String fechaHora = LocalDateTime.now().format(FECHA_ARCHIVO_FORMATTER);
        
        if (idPrograma != null) {
            // Si se proporciona un idPrograma, filtrar las actividades
            Programa programa = programaService.findById(idPrograma);
            if (programa == null) {
                return ResponseEntity.notFound().build();
            }
            actividades = actividadService.obtenerPorPrograma(idPrograma);
            nombrePrograma = programa.getNombrePrograma();
            // Limpiar el nombre del programa para usarlo en el archivo (sin espacios ni caracteres especiales)
            String nombreProgramaLimpio = nombrePrograma
                .replaceAll("[^a-zA-Z0-9]", "_")
                .replaceAll("_+", "_");
            nombreArchivo = "actividades_" + nombreProgramaLimpio + "_" + fechaHora + ".pdf";
        } else {
            // Si no se proporciona idPrograma, obtener todas las actividades
            actividades = actividadService.obtenerTodas();
            nombrePrograma = "Todos los Programas";
            nombreArchivo = "actividades_todas_" + fechaHora + ".pdf";
        }
        
        byte[] pdf = pdfExportService.generarPDFActividades(actividades, nombrePrograma);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(nombreArchivo).build());
        
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/exportar-pdf")
    public ResponseEntity<byte[]> exportarPDFPorId(@PathVariable Integer id) {
        Actividad actividad = actividadService.obtenerPorId(id);
        if (actividad == null) {
            return ResponseEntity.notFound().build();
        }
        
        String nombrePrograma = actividad.getPrograma().getNombrePrograma();
        String fechaHora = LocalDateTime.now().format(FECHA_ARCHIVO_FORMATTER);
        
        // Limpiar el nombre de la actividad para usarlo en el archivo
        String nombreActividadLimpio = actividad.getNombreActividad()
            .replaceAll("[^a-zA-Z0-9]", "_")
            .replaceAll("_+", "_");
        
        byte[] pdf = pdfExportService.generarPDFActividades(List.of(actividad), nombrePrograma);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(
            "actividad_" + nombreActividadLimpio + "_" + fechaHora + ".pdf").build());
        
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}