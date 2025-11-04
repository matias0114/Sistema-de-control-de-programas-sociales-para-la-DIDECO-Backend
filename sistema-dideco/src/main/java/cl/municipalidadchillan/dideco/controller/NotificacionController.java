package cl.municipalidadchillan.dideco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidadchillan.dideco.dto.NotificacionDTO;
import cl.municipalidadchillan.dideco.service.NotificacionService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/usuario/{idUsuario}")
    public List<NotificacionDTO> obtenerNotificaciones(@PathVariable Integer idUsuario) {
        return notificacionService.obtenerNotificacionesPorUsuario(idUsuario);
    }

    @GetMapping("/usuario/{idUsuario}/no-leidas")
    public List<NotificacionDTO> obtenerNotificacionesNoLeidas(@PathVariable Integer idUsuario) {
        return notificacionService.obtenerNotificacionesNoLeidasPorUsuario(idUsuario);
    }

    @GetMapping("/usuario/{idUsuario}/contador")
    public ResponseEntity<Long> contarNoLeidas(@PathVariable Integer idUsuario) {
        Long count = notificacionService.contarNotificacionesNoLeidas(idUsuario);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{idNotificacion}/leer")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Integer idNotificacion) {
        notificacionService.marcarComoLeida(idNotificacion);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/usuario/{idUsuario}/leer-todas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Integer idUsuario) {
        notificacionService.marcarTodasComoLeidas(idUsuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idNotificacion}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Integer idNotificacion) {
        try {
            notificacionService.eliminar(idNotificacion);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}