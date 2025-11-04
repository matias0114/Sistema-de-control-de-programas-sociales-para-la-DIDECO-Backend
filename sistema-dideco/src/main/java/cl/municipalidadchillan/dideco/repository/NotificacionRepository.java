package cl.municipalidadchillan.dideco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.municipalidadchillan.dideco.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    
    List<Notificacion> findByUsuarioIdUsuarioOrderByFechaCreacionDesc(Integer idUsuario);
    
    List<Notificacion> findByUsuarioIdUsuarioAndLeidaOrderByFechaCreacionDesc(Integer idUsuario, Boolean leida);
    
    Long countByUsuarioIdUsuarioAndLeida(Integer idUsuario, Boolean leida);
}