package com.morales.bootcamp.spring_boot_pet_adoption.repository;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {

    // ResponseEntity<List<Adopcion>> findByUserId(Long userId);

}
