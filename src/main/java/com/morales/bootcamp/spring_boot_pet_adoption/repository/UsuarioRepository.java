package com.morales.bootcamp.spring_boot_pet_adoption.repository;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
