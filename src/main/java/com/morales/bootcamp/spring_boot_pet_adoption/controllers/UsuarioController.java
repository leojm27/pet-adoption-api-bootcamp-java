package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Usuario;
import com.morales.bootcamp.spring_boot_pet_adoption.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    /**
     * @return Todos los Usuarios.
     */
    @GetMapping("/api/usuarios")
    public ResponseEntity<?> allUsers() {
        try {
            return ResponseEntity.ok(service.getUsuarios());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener Usuarios");
        }
    }

    /**
     * @param id
     * @return Usuario por Id.
     */
    @GetMapping("/api/usuarios/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        try {
            Usuario usuario = service.getUsuarioById(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + id + " no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener Usuario id " + id);
        }
    }

    /**
     * @param usuarioToCreate
     * @return usuario creado
     */
    @PostMapping("/api/usuarios")
    public ResponseEntity<?> createUser(@RequestBody Usuario usuarioToCreate) {
        try {
            Usuario nuevoUsuario = service.createUsuario(usuarioToCreate);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error al intentar crear nuevo Usuario");
        }
    }

    /**
     * @param usuarioToUpdate
     * @param id
     * @return usuario actualizado
     */
    @PutMapping("/api/usuarios/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Usuario usuarioToUpdate, @PathVariable("id") Long id) {
        try {
            Usuario usuario = service.updateUsuario(usuarioToUpdate, id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar Usuario id " + id);
        }
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/api/usuarios/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            service.deleteUsuario(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ie.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar Usuario id " + id);
        }
    }
}
