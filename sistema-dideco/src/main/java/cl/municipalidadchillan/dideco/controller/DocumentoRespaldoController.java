package cl.municipalidadchillan.dideco.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.municipalidadchillan.dideco.model.Avance;
import cl.municipalidadchillan.dideco.model.DocumentoRespaldo;
import cl.municipalidadchillan.dideco.service.AvanceService;
import cl.municipalidadchillan.dideco.service.DocumentoRespaldoService;


@RestController
@RequestMapping("/documentos-respaldo")
public class DocumentoRespaldoController {

    private final DocumentoRespaldoService documentoRespaldoService;
    private final AvanceService avanceService;

    @Autowired
    public DocumentoRespaldoController(DocumentoRespaldoService documentoRespaldoService, AvanceService avanceService) {
        this.documentoRespaldoService = documentoRespaldoService;
        this.avanceService = avanceService;
    }

    // Listar todos los documentos
    @GetMapping
    public List<DocumentoRespaldo> obtenerTodos() {
        return documentoRespaldoService.obtenerTodos();
    }

    // Listar documentos por id de avance
    @GetMapping("/avance/{idAvance}")
    public List<DocumentoRespaldo> obtenerPorAvance(@PathVariable int idAvance) {
        return documentoRespaldoService.obtenerPorIdAvance(idAvance);
    }

    // Obtener documento por id
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoRespaldo> obtenerPorId(@PathVariable Integer id) {
        DocumentoRespaldo documento = documentoRespaldoService.obtenerPorId(id);
        if (documento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(documento);
    }

    // Crear nuevo documento (sin archivo - solo metadata)
    @PostMapping
    public ResponseEntity<DocumentoRespaldo> crearDocumento(@RequestBody DocumentoRespaldo documento) {
        DocumentoRespaldo nuevo = documentoRespaldoService.guardar(documento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PostMapping("/avance/{idAvance}/upload")
    public ResponseEntity<?> subirArchivo(@PathVariable int idAvance, @RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Archivo no proporcionado");
            }

            Avance avance = avanceService.obtenerPorId(idAvance);
            if (avance == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avance no encontrado");
            }

            // Aquí la lógica para guardar archivo en disco o almacenamiento
            // Luego crear el objeto DocumentoRespaldo y guardarlo en la base
            
            String carpetaUploads = "uploads/"; // ruta relativa o absoluta
            String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path rutaArchivo = Paths.get(carpetaUploads + nombreArchivo);
            
            // Asegura que exista el directorio
            Files.createDirectories(rutaArchivo.getParent());
            
            // Copia el archivo hacia la carpeta destino
            Files.copy(file.getInputStream(), rutaArchivo);

            DocumentoRespaldo doc = new DocumentoRespaldo();
            doc.setNombreArchivo(file.getOriginalFilename());
            doc.setTipoContenido(file.getContentType());
            doc.setUrl(carpetaUploads + nombreArchivo);
            doc.setFechaSubida(LocalDateTime.now());
            doc.setAvance(avance);

            DocumentoRespaldo guardado = documentoRespaldoService.guardar(doc);

            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir archivo: " + e.getMessage());
        }
    }


    // Actualizar documento metadata
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoRespaldo> actualizarDocumento(@PathVariable Integer id, @RequestBody DocumentoRespaldo doc) {
        DocumentoRespaldo existente = documentoRespaldoService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombreArchivo(doc.getNombreArchivo());
        existente.setTipoContenido(doc.getTipoContenido());
        existente.setUrl(doc.getUrl());
        existente.setFechaSubida(doc.getFechaSubida());
        existente.setAvance(doc.getAvance());

        DocumentoRespaldo actualizado = documentoRespaldoService.guardar(existente);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar documento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDocumento(@PathVariable Integer id) {
        documentoRespaldoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
