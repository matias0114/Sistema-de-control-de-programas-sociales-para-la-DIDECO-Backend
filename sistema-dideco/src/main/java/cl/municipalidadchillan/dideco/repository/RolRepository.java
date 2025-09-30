package cl.municipalidadchillan.dideco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}