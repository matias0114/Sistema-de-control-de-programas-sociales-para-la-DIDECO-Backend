package cl.municipalidadchillan.dideco.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidadchillan.dideco.dto.CambiarPasswordRequest;
import cl.municipalidadchillan.dideco.dto.UsuarioDTO;
import cl.municipalidadchillan.dideco.model.Programa;
import cl.municipalidadchillan.dideco.model.Usuario;
import cl.municipalidadchillan.dideco.repository.ProgramaRepository;
import cl.municipalidadchillan.dideco.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProgramaRepository programaRepository;

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable int id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Crear nuevo usuario - Encripta la contraseña automáticamente
     */
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        // Encriptar contraseña antes de guardar
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            String passwordEncriptada = usuarioService.encriptarPassword(usuario.getContrasena());
            usuario.setContrasena(passwordEncriptada);
        }
        Usuario nuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    /**
     * Actualizar usuario completo - Encripta contraseña si se proporciona una nueva
     * Si la contraseña viene vacía, mantiene la existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable int id, @RequestBody Usuario usuario) {
        Usuario actual = usuarioService.findById(id);
        if (actual != null) {
            usuario.setIdUsuario(id);
            
            // Si la contraseña está vacía o es null, mantener la existente
            if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                usuario.setContrasena(actual.getContrasena());
            } else {
                // Si viene una nueva contraseña, verificar si ya está encriptada
                String password = usuario.getContrasena();
                if (!password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                    // No está encriptada, encriptarla
                    usuario.setContrasena(usuarioService.encriptarPassword(password));
                }
                // Si ya está encriptada, mantenerla como está
            }
            
            return ResponseEntity.ok(usuarioService.save(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint seguro para actualizar solo el nombre del usuario (sin tocar contraseña)
     * Retorna UsuarioDTO sin exponer la contraseña
     */
    @PatchMapping("/{id}/nombre")
    public ResponseEntity<?> actualizarNombre(
            @PathVariable int id, 
            @RequestBody Map<String, String> body) {
        
        String nuevoNombre = body.get("nombreUsuario");
        
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "El nombre de usuario no puede estar vacío");
            return ResponseEntity.badRequest().body(error);
        }
        
        try {
            Usuario usuario = usuarioService.actualizarNombre(id, nuevoNombre);
            // Retornar DTO sin contraseña para mayor seguridad
            UsuarioDTO dto = UsuarioDTO.fromEntity(usuario);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Endpoint para cambiar la contraseña de un usuario
     * Requiere la contraseña actual para validar la identidad
     */
    @PatchMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@PathVariable int id, @RequestBody CambiarPasswordRequest request) {
        // Validar que se proporcionen ambas contraseñas
        if (request.getPasswordActual() == null || request.getPasswordActual().isEmpty() ||
            request.getNuevaPassword() == null || request.getNuevaPassword().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Debe proporcionar la contraseña actual y la nueva contraseña");
            return ResponseEntity.badRequest().body(error);
        }

        // Validar longitud mínima de la nueva contraseña
        if (request.getNuevaPassword().length() < 6) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "La nueva contraseña debe tener al menos 6 caracteres");
            return ResponseEntity.badRequest().body(error);
        }

        // Intentar cambiar la contraseña
        boolean exito = usuarioService.cambiarPassword(id, request.getPasswordActual(), request.getNuevaPassword());
        
        if (exito) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Contraseña cambiada exitosamente");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "La contraseña actual es incorrecta");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginData) {
        Usuario usuario = usuarioService.findByCorreo(loginData.getCorreo());
        if (usuario != null && usuarioService.verificarPassword(loginData.getContrasena(), usuario.getContrasena())) {
            // Buscar el programa asignado (solo 1 por encargado)
            Programa programa = programaRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
            // Prepara la respuesta extensible
            Map<String, Object> response = new HashMap<>();
            response.put("idUsuario", usuario.getIdUsuario());
            response.put("nombreUsuario", usuario.getNombreUsuario());
            response.put("idRol", usuario.getIdRol());
            response.put("correo", usuario.getCorreo());
            if (programa != null) {
                Map<String, Object> programaInfo = new HashMap<>();
                programaInfo.put("idPrograma", programa.getIdPrograma());
                programaInfo.put("nombrePrograma", programa.getNombrePrograma());
                response.put("programa", programaInfo);
            }
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}