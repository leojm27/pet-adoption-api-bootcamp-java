package com.morales.bootcamp.spring_boot_pet_adoption.repository;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
