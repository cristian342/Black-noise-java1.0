package com.example.BlackNoise.repository;

import com.example.blacknoise.model.Rol;
import com.example.blacknoise.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    // Método para buscar usuario por correo
    Usuario findByCorreo(String correo);
    
    // Método para verificar si existe un usuario con un correo
    boolean existsByCorreo(String correo);

    // Búsqueda multicriterio
    @Query("{$or: [" +
           "  {nombre: {$regex: ?0, $options: 'i'}}, " +
           "  {correo: {$regex: ?0, $options: 'i'}}, " +
           "  {rol: ?1}" +
           "]}")
    List<Usuario> buscarMulticriterio(String textoBusqueda, Rol rol);

    // Búsqueda solo por rol
    List<Usuario> findByRol(Rol rol);
    
    List<Usuario> findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(String nombre, String correo);
    
    List<Usuario> findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCaseAndRol(String nombre, String correo, Rol rol);
}