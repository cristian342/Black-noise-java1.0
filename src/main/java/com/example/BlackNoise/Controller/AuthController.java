package com.example.BlackNoise.Controller;

import com.example.blacknoise.model.Usuario;
import com.example.blacknoise.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarRegistroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    
    
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        
        try {
            usuarioService.registrarUsuario(usuario);
            usuario = usuarioService.iniciarSesion(usuario.getCorreo(), usuario.getContraseña());
        if (usuario != null) {
            return "redirect:/usuario/" + usuario.getId();
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
            
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
        
    }
    
    
    @GetMapping("/catalogo")
    public String mostrarBienvenida(Model model,Usuario usuario) {
        // Asegúrate de que el usuario esté disponible en el modelo si accede directamente a esta ruta
        model.addAttribute("usuario",usuario);
        
        return "catalogo";
        
    }
    
    
    
    
    @GetMapping("/login")
    public String mostrarLoginForm() {
        return "login";
    }
    
    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String correo, 
                                @RequestParam String contrasena, 
                                Model model) {
        Usuario usuario = usuarioService.iniciarSesion(correo, contrasena);
        if (usuario != null) {
            return "redirect:/usuario/" + usuario.getId();
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }
    
    /**
     *
     */
    @Controller
    public class CarritoController {

    @GetMapping("/Carrito")
    public String carrito() {
        return "Carrito"; // El nombre del archivo HTML en la carpeta templates, sin la extensión.
    }
}

    
   
}
