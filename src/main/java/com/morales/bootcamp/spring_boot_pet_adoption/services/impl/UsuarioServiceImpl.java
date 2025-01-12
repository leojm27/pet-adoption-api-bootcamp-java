package com.morales.bootcamp.spring_boot_pet_adoption.services.impl;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Adopcion;
import com.morales.bootcamp.spring_boot_pet_adoption.models.Usuario;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.UsuarioRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.services.AdopcionService;
import com.morales.bootcamp.spring_boot_pet_adoption.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final AdopcionService adopcionService;

    @Override
    public List<Usuario> getUsuarios() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Usuario::getId))
                .toList();
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuarioToUpdate, Long id) {
        return repository.findById(id)
                .map(usuario -> {
                    if (usuarioToUpdate.getNombre() != null) {
                        usuario.setNombre(usuarioToUpdate.getNombre());
                    }
                    if (usuarioToUpdate.getTelefono() != null) {
                        usuario.setTelefono(usuarioToUpdate.getTelefono());
                    }
                    if (usuarioToUpdate.getCorreoElectronico() != null) {
                        usuario.setCorreoElectronico(usuarioToUpdate.getCorreoElectronico());
                    }

                    repository.save(usuario);
                    return usuario;
                }).orElse(null);
    }

    @Override
    public void deleteUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Usuario " + id + " no encontrado");
        }

        /* VALIDAR ADOPCIONES ASOCIADAS
        List<Adopcion> adopciones = adopcionService.getAdopcionesByUsuario(id);
        if(!adopciones.isEmpty()){
            throw new DataIntegrityViolationException("No se puede eliminar Usuario " + id + " porque tiene adopciones asociadas.");
        }
        */

        repository.deleteById(id);
    }
}
