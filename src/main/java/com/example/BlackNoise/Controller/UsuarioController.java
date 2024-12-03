package com.example.blacknoise.controller;

import com.example.blacknoise.model.Usuario;
import com.example.blacknoise.model.Rol;
import com.example.blacknoise.service.UsuarioService;
import com.example.blacknoise.service.ExportService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ExportService exportService;

    // Métodos existentes...

    @GetMapping("/{id}")
    public String obtenerUsuarioPorId(@PathVariable String id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            
            // Obtener todos los usuarios por defecto
            List<Usuario> todosUsuarios = usuarioService.obtenerTodosLosUsuarios();
            model.addAttribute("usuarios", todosUsuarios);
            
            switch(usuario.getRol()) {
                case ADMINISTRADOR:
                    return "admin_dashboard";
                case EMPLEADO:
                    return "empleado_dashboard";
                default:
                    return "catalogo";
            }
        } else {
            return "error";
        }
    }

    @GetMapping("/{idAdmin}/editar/{idEditado}")
    public String editarFormulario(@PathVariable String idAdmin, @PathVariable String idEditado, Model model) {
        try {
            // Obtén el usuario que está siendo editado
            Usuario usuarioEditar = usuarioService.obtenerUsuarioPorId(idEditado);
            
            if (usuarioEditar != null) {
                model.addAttribute("usuario", usuarioEditar);
                model.addAttribute("idEditado", idEditado); // Agrega el ID del usuario al modelo
                return "editar_usuario";  // Página donde se muestra el formulario de edición
            } else {
                model.addAttribute("error", "Usuario no encontrado");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al obtener el usuario: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{idAdmin}/editar/{idEditado}")
    public String editarUsuario(@PathVariable String idAdmin, @PathVariable String idEditado, 
                                @ModelAttribute Usuario usuarioActualizado, Model model) {
        try {
            // Actualizar los valores del usuario con los nuevos datos del formulario
            Usuario usuarioEditado = usuarioService.editarUsuario(usuarioActualizado);
            
            // Redirigir a la vista del usuario editado
            return "redirect:/usuario/" + idAdmin;  // Redirigir al dashboard del administrador o a la vista que desees
        } catch (Exception e) {
            model.addAttribute("error", "Error al editar el usuario: " + e.getMessage());
            return "error";  // En caso de error, redirige a una página de error
        }
    }

    @PostMapping("/{idAdmin}/eliminar/{idEditado}")
    public String eliminarUsuario(@PathVariable String idAdmin, @PathVariable String idEditado, Model model) {
        try {
            // Eliminar el usuario con el ID recibido
            usuarioService.eliminarUsuario(idEditado);
            
            // Redirigir al dashboard del administrador después de eliminar el usuario
            return "redirect:/usuario/" + idAdmin;  // Redirige al panel del administrador (o la página que desees)
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";  // Si ocurre un error, redirige a una página de error
        }
    }

    @GetMapping("/{id}/buscar")
    public String buscarUsuarios(
        @PathVariable String id, 
        @RequestParam(required = false) String textoBusqueda,
        @RequestParam(required = false) Rol rol,
        Model model
    ) {
        Usuario usuarioActual = usuarioService.obtenerUsuarioPorId(id);
        
        if (usuarioActual == null) {
            return "error";
        }

        // Obtener usuarios filtrados
        List<Usuario> usuariosFiltrados = usuarioService.buscarUsuarios(textoBusqueda, rol);
        
        model.addAttribute("usuario", usuarioActual);
        model.addAttribute("usuarios", usuariosFiltrados);
        
        // Redirige al dashboard correspondiente según el rol del usuario actual
        return usuarioActual.getRol() == Rol.ADMINISTRADOR ? 
               "admin_dashboard" : "empleado_dashboard";
    }

    // Nuevo método para exportar usuarios a PDF
    @GetMapping("/{id}/exportar")
    public ResponseEntity<byte[]> exportarUsuarios(
        @PathVariable String id, 
        @RequestParam(required = false) String textoBusqueda,
        @RequestParam(required = false) Rol rol,
        HttpServletResponse response
    ) throws DocumentException {
        Usuario usuarioActual = usuarioService.obtenerUsuarioPorId(id);
        
        if (usuarioActual == null || 
            (usuarioActual.getRol() != Rol.ADMINISTRADOR && 
             usuarioActual.getRol() != Rol.EMPLEADO)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Obtener usuarios filtrados
        List<Usuario> usuariosFiltrados = usuarioService.buscarUsuarios(textoBusqueda, rol);
        
        // Generar título descriptivo
        String titulo = "Reporte de Usuarios - " + 
            (textoBusqueda != null ? "Búsqueda: " + textoBusqueda : "Todos los Usuarios") +
            (rol != null ? " (Rol: " + rol + ")" : "");

        ByteArrayOutputStream pdfStream = exportService.generarPdfUsuarios(usuariosFiltrados, titulo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("reporte_usuarios.pdf").build());

        return ResponseEntity
            .ok()
            .headers(headers)
            .body(pdfStream.toByteArray());
    }
}