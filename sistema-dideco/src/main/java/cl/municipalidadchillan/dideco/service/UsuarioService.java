package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findById(int id);
    Usuario save(Usuario usuario);
    void deleteById(int id);

    public Usuario findByCorreo(String correo);
    
    /**
     * Actualiza solo el nombre de usuario sin tocar la contraseña
     * @param idUsuario ID del usuario
     * @param nuevoNombre Nuevo nombre para el usuario
     * @return Usuario actualizado
     */
    Usuario actualizarNombre(int idUsuario, String nuevoNombre);
    
    /**
     * Cambia la contraseña de un usuario verificando primero la contraseña actual
     * @param idUsuario ID del usuario
     * @param passwordActual Contraseña actual del usuario
     * @param nuevaPassword Nueva contraseña
     * @return true si el cambio fue exitoso, false si la contraseña actual es incorrecta
     */
    boolean cambiarPassword(int idUsuario, String passwordActual, String nuevaPassword);
    
    /**
     * Verifica si una contraseña coincide con la contraseña encriptada del usuario
     * @param passwordPlano Contraseña en texto plano
     * @param passwordEncriptada Contraseña encriptada en la base de datos
     * @return true si coinciden, false en caso contrario
     */
    boolean verificarPassword(String passwordPlano, String passwordEncriptada);
    
    /**
     * Encripta una contraseña usando BCrypt
     * @param passwordPlano Contraseña en texto plano
     * @return Contraseña encriptada
     */
    String encriptarPassword(String passwordPlano);
}