package cl.municipalidadchillan.dideco.service;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.PasswordResetToken;
import cl.municipalidadchillan.dideco.model.Usuario;
import cl.municipalidadchillan.dideco.repository.PasswordResetTokenRepository;
import cl.municipalidadchillan.dideco.repository.UsuarioRepository;
@Service
public class PasswordResetService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.url.frontend}")
    private String frontendUrl;


    public String createToken(String correo) {

        Optional<Usuario> optUser = usuarioRepository.findByCorreo(correo);

        if (optUser.isEmpty()) {
            return "NO_EXISTE";
        }

        Usuario user = optUser.get();

        String token = UUID.randomUUID().toString();

        PasswordResetToken reset = new PasswordResetToken();
        reset.setToken(token);
        reset.setUsuario(user);

        // Expira en 10 minutos
        reset.setExpiration(
            Timestamp.from(Instant.now().plus(Duration.ofMinutes(10)))
        );

        tokenRepository.save(reset);

        enviarCorreo(correo, token);

        return "OK";
    }

    public boolean resetPassword(String token, String nuevaPass) {

        Optional<PasswordResetToken> opt = tokenRepository.findByToken(token);

        if (opt.isEmpty()) {
            return false; // token no existe
        }

        PasswordResetToken reset = opt.get();

        // token expirado
        if (reset.getExpiration().before(new Timestamp(System.currentTimeMillis()))) {
            return false;
        }

        Usuario user = reset.getUsuario();
        user.setContrasena(nuevaPass); // si luego quieres BCrypt, aquí se cambia

        usuarioRepository.save(user);

        // eliminar token luego de usarlo
        tokenRepository.delete(reset);

        return true;
    }

    private void enviarCorreo(String correo, String token) {

        String link = frontendUrl + "/reset-password?token=" + token;

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(correo);
        msg.setSubject("Recuperación de Contraseña - DIDECO");
        msg.setText("Hola,\n\n"
                + "Para restablecer tu contraseña haz clic en el siguiente enlace (válido por 10 minutos):\n\n"
                + link + "\n\n"
                + "Si no solicitaste este cambio, ignora este mensaje.");

        mailSender.send(msg);
    }
}