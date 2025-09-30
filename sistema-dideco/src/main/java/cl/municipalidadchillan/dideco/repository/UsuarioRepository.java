package cl.municipalidadchillan.dideco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
