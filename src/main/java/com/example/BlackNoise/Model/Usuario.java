package com.example.blacknoise.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "usuarios")

public class Usuario {

    @Id
    private String id;
    
    private String nombre;
    
    @Indexed(unique = true)
    private String correo;
    
    @Field("password")
    private String contraseña;

    private Rol rol;

    // Constructores
    
    
    
    public Usuario(String nombre, String correo, String contraseña) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = determinarRol(correo);
    }

    public Usuario() {
    }

   

    // Método para determinar el rol basado en el dominio del correo
    private Rol determinarRol(String correo) {
        if (correo.endsWith("@blacknoise.com")) {
            return Rol.ADMINISTRADOR;
        } else if (correo.endsWith("@blackmanager.com")) {
            return Rol.EMPLEADO;
        }
        return Rol.CLIENTE;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
        // Actualizar rol al cambiar el correo
        this.rol = determinarRol(correo);
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}