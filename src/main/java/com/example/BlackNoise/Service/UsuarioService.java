package com.example.blacknoise.service;

import com.example.blacknoise.model.Rol;
import com.example.blacknoise.model.Usuario;
import com.example.BlackNoise.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public Usuario iniciarSesion(String correo, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return usuario;
        }
        return null;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario editarUsuario(Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioActualizado.getId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        
        return usuarioRepository.save(usuarioExistente);
    }

    public void eliminarUsuario(String id) {
        usuarioRepository.deleteById(id);
    }

    public void cambiarRol(String id, Rol nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setRol(nuevoRol);
        usuarioRepository.save(usuario);
    }
    
      // Método para búsqueda multicriterio con manejo de casos especiales
    public List<Usuario> buscarUsuarios(String textoBusqueda, Rol rol) {
        // Si no hay texto ni rol, devolver todos los usuarios
        if ((textoBusqueda == null || textoBusqueda.trim().isEmpty()) && rol == null) {
            return usuarioRepository.findAll();
        }

        // Si hay texto pero no hay rol
        if (rol == null) {
            return usuarioRepository.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(textoBusqueda, textoBusqueda);
        }

        // Si hay rol pero no hay texto
        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            return usuarioRepository.findByRol(rol);
        }

        // Si hay texto y rol
        return usuarioRepository.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCaseAndRol(textoBusqueda, textoBusqueda, rol);
    }
}

