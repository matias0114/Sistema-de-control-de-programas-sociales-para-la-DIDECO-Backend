package cl.municipalidadchillan.dideco.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.municipalidadchillan.dideco.service.PasswordResetService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    // ================================================================
    // 1. Solicitar recuperación de contraseña (envía correo)
    // ================================================================
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {

        String correo = body.get("correo");

        String result = passwordResetService.createToken(correo);

        if (result.equals("NO_EXISTE")) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "No existe un usuario registrado con ese correo");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Se ha enviado un enlace a su correo para restablecer la contraseña");
        return ResponseEntity.ok(response);
    }

    // ================================================================
    // 2. Restablecer contraseña usando el token recibido por correo
    // ================================================================
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {

        String token = body.get("token");
        String nuevaPassword = body.get("nuevaPassword");

        boolean ok = passwordResetService.resetPassword(token, nuevaPassword);

        if (!ok) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "El enlace es inválido o ha expirado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "La contraseña ha sido restablecida exitosamente");
        return ResponseEntity.ok(response);
    }
}