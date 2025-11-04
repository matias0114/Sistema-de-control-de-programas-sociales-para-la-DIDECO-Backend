package cl.municipalidadchillan.dideco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);
    
    // Cambiar a findByIdRol porque idRol es un campo directo en Usuario
    List<Usuario> findByIdRol(Integer idRol);

}
