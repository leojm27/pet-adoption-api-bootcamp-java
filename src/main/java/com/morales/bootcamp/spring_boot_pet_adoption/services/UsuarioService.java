package com.morales.bootcamp.spring_boot_pet_adoption.services;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Usuario;
import java.util.List;

public interface UsuarioService {

    Usuario createUsuario(Usuario usuario);

    Usuario updateUsuario(Usuario usuario, Long id);

    Usuario getUsuarioById(Long id);

    List<Usuario> getUsuarios();

    void deleteUsuario(Long id);

}
