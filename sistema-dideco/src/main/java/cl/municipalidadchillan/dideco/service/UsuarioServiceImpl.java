package cl.municipalidadchillan.dideco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.Usuario;
import cl.municipalidadchillan.dideco.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(int id) {
        usuarioRepository.deleteById(id);
    }
    
    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }
    
    @Override
    public Usuario actualizarNombre(int idUsuario, String nuevoNombre) {
        Usuario usuario = findById(idUsuario);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + idUsuario);
        }
        
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        usuario.setNombreUsuario(nuevoNombre.trim());
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public boolean cambiarPassword(int idUsuario, String passwordActual, String nuevaPassword) {
        Usuario usuario = findById(idUsuario);
        if (usuario == null) {
            return false;
        }
        
        // Verificar si la contraseña actual es correcta
        if (!verificarPassword(passwordActual, usuario.getContrasena())) {
            return false;
        }
        
        // Encriptar la nueva contraseña y guardarla
        usuario.setContrasena(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
        return true;
    }
    
    @Override
    public boolean verificarPassword(String passwordPlano, String passwordEncriptada) {
        // Si la contraseña en BD no está encriptada (texto plano), comparar directamente
        // Esto permite compatibilidad con contraseñas existentes
        if (!passwordEncriptada.startsWith("$2a$") && !passwordEncriptada.startsWith("$2b$") && !passwordEncriptada.startsWith("$2y$")) {
            return passwordPlano.equals(passwordEncriptada);
        }
        // Si está encriptada, usar BCrypt
        return passwordEncoder.matches(passwordPlano, passwordEncriptada);
    }
    
    @Override
    public String encriptarPassword(String passwordPlano) {
        if (passwordPlano == null || passwordPlano.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        return passwordEncoder.encode(passwordPlano);
    }
}