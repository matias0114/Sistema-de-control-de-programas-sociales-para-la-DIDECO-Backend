package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findById(int id);
    Usuario save(Usuario usuario);
    void deleteById(int id);

    public Usuario findByCorreo(String correo);
}