package cl.municipalidadchillan.dideco.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.municipalidadchillan.dideco.dto.NotificacionDTO;
import cl.municipalidadchillan.dideco.model.Notificacion;
import cl.municipalidadchillan.dideco.model.Usuario;
import cl.municipalidadchillan.dideco.repository.NotificacionRepository;
import cl.municipalidadchillan.dideco.repository.UsuarioRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void notificarVisualizadores(String tipo, String mensaje, Integer idReferencia) {
        // Obtener todos los usuarios con rol de Visualizador (id_rol = 3)
        List<Usuario> visualizadores = usuarioRepository.findByIdRol(3);
        
        for (Usuario visualizador : visualizadores) {
            Notificacion notificacion = new Notificacion(visualizador, tipo, mensaje, idReferencia);
            notificacionRepository.save(notificacion);
        }
    }

    public List<NotificacionDTO> obtenerNotificacionesPorUsuario(Integer idUsuario) {
        return notificacionRepository.findByUsuarioIdUsuarioOrderByFechaCreacionDesc(idUsuario)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<NotificacionDTO> obtenerNotificacionesNoLeidasPorUsuario(Integer idUsuario) {
        return notificacionRepository.findByUsuarioIdUsuarioAndLeidaOrderByFechaCreacionDesc(idUsuario, false)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public Long contarNotificacionesNoLeidas(Integer idUsuario) {
        return notificacionRepository.countByUsuarioIdUsuarioAndLeida(idUsuario, false);
    }

    @Transactional
    public void marcarComoLeida(Integer idNotificacion) {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion)
            .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notificacion.setLeida(true);
        notificacionRepository.save(notificacion);
    }

    @Transactional
    public void marcarTodasComoLeidas(Integer idUsuario) {
        List<Notificacion> notificaciones = notificacionRepository
            .findByUsuarioIdUsuarioAndLeidaOrderByFechaCreacionDesc(idUsuario, false);
        for (Notificacion notificacion : notificaciones) {
            notificacion.setLeida(true);
            notificacionRepository.save(notificacion);
        }
    }

    private NotificacionDTO convertirADTO(Notificacion notificacion) {
        return new NotificacionDTO(
            notificacion.getIdNotificacion(),
            notificacion.getTipo(),
            notificacion.getMensaje(),
            notificacion.getIdReferencia(),
            notificacion.getLeida(),
            notificacion.getFechaCreacion(),
            notificacion.getUsuario().getIdUsuario(),
            notificacion.getUsuario().getNombreUsuario()
        );
    }
}