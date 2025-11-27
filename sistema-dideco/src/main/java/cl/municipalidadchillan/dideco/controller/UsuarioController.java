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

    // ================================================================
    // GET all users
    // ================================================================
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

    // ================================================================
    // Crear usuario (con encriptación automática)
    // ================================================================
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {

        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            String passwordEncriptada = usuarioService.encriptarPassword(usuario.getContrasena());
            usuario.setContrasena(passwordEncriptada);
        }

        Usuario nuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ================================================================
    // Actualizar usuario
    // ================================================================
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable int id, @RequestBody Usuario usuario) {
        Usuario actual = usuarioService.findById(id);

        if (actual != null) {
            usuario.setIdUsuario(id);

            if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                usuario.setContrasena(actual.getContrasena());
            } else {
                String password = usuario.getContrasena();
                if (!password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                    usuario.setContrasena(usuarioService.encriptarPassword(password));
                }
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

    // ================================================================
    // Actualizar solo nombre (sin tocar contraseña)
    // ================================================================
    @PatchMapping("/{id}/nombre")
    public ResponseEntity<?> actualizarNombre(@PathVariable int id, @RequestBody Map<String, String> body) {

        String nuevoNombre = body.get("nombreUsuario");

        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "El nombre de usuario no puede estar vacío");
            return ResponseEntity.badRequest().body(error);
        }

        try {
            Usuario usuario = usuarioService.actualizarNombre(id, nuevoNombre);
            UsuarioDTO dto = UsuarioDTO.fromEntity(usuario);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // ================================================================
    // Cambiar contraseña
    // ================================================================
    @PatchMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@PathVariable int id, @RequestBody CambiarPasswordRequest request) {

        if (request.getPasswordActual() == null || request.getPasswordActual().isEmpty() ||
            request.getNuevaPassword() == null || request.getNuevaPassword().isEmpty()) {

            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Debe proporcionar la contraseña actual y la nueva contraseña");
            return ResponseEntity.badRequest().body(error);
        }

        if (request.getNuevaPassword().length() < 6) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "La nueva contraseña debe tener al menos 6 caracteres");
            return ResponseEntity.badRequest().body(error);
        }

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

    // ================================================================
    // LOGIN (Ahora devuelve TODOS los programas + el programa activo)
    // ================================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginData) {

        Usuario usuario = usuarioService.findByCorreo(loginData.getCorreo());

        if (usuario != null && usuarioService.verificarPassword(loginData.getContrasena(), usuario.getContrasena())) {

            Map<String, Object> response = new HashMap<>();

            response.put("idUsuario", usuario.getIdUsuario());
            response.put("nombreUsuario", usuario.getNombreUsuario());
            response.put("idRol", usuario.getIdRol());
            response.put("correo", usuario.getCorreo());

            // Obtener todos los programas del usuario
            List<Programa> programas = usuario.getProgramas();
            response.put("programas", programas);

            // Encontrar el programa activo
            // Obtener todos los programas activos del usuario
            List<Programa> programasActivos = programas.stream()
                .filter(p -> "Activo".equalsIgnoreCase(p.getEstado()))
                .toList();

            // Elegir programaActivo solo si hay UNO
            Programa programaActivo = programasActivos.size() == 1 
                ? programasActivos.get(0) 
                : null;

            // Respuesta
            response.put("programasActivos", programasActivos);
            response.put("programaActivo", programaActivo);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // ================================================================
    // Obtener TODOS los programas del usuario
    // ================================================================
    @GetMapping("/{id}/programas")
    public ResponseEntity<List<Programa>> getProgramasByUsuario(@PathVariable int id) {
        Usuario usuario = usuarioService.findById(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.getProgramas());
    }

}