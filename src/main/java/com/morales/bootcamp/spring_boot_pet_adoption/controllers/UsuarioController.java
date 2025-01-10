package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Usuario;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {
    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    /**
     * @return Todos los Usuarios.
     */
    @GetMapping("/api/usuarios")
    public ResponseEntity<List<Usuario>> allUsers() {
        return ResponseEntity.ok(repository.findAll());
    }

    /**
     * @param id
     * @return Usuario por Id.
     */
    @GetMapping("/api/usuarios/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioObtained = repository.findById(id);
        if (usuarioObtained.isPresent()) {
            return ResponseEntity.ok(usuarioObtained);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario " + id + " no encontrado");
        }
    }

    /**
     * @param usuarioToCreate
     * @return usuario creado
     */
    @PostMapping("/api/usuarios")
    public ResponseEntity<?> createUser(@RequestBody Usuario usuarioToCreate) {
        try {
            Usuario nuevoUsuario = repository.save(usuarioToCreate);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevoUsuario);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrio un error al intentar crear nuevo Usuario");
        }
    }

    /**
     * @param usuarioToUpdate
     * @param id
     * @return usuario actualizado
     */
    @PutMapping("/api/usuarios/{id}")
    public ResponseEntity<Usuario> updateUser(@RequestBody Usuario usuarioToUpdate, @PathVariable("id") Long id) {
        return repository.findById(id)
                .map(usuario -> {
                    if(usuarioToUpdate.getNombre() != null) {
                        usuario.setNombre(usuarioToUpdate.getNombre());
                    }
                    if(usuarioToUpdate.getTelefono() != null) {
                        usuario.setTelefono(usuarioToUpdate.getTelefono());
                    }
                    if(usuarioToUpdate.getCorreo_electronico() != null) {
                        usuario.setCorreo_electronico(usuarioToUpdate.getCorreo_electronico());
                    }

                    repository.save(usuario);
                    return ResponseEntity.ok(usuario);
                }).orElse(ResponseEntity.notFound().build()
                );
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/api/usuarios/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            repository.deleteById(id);
            return ResponseEntity.ok().body("El Usuario con id " + id + " ha sido eliminado.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar Usuario id " + id);
        }
    }
}
