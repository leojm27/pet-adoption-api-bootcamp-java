package com.morales.bootcamp.spring_boot_pet_adoption.actuator;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.MascotaRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HealthChecker implements HealthIndicator {

    private final MascotaRepository mascotaRepository;

    public HealthChecker(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public Health health() {
        Long mascotas = mascotaRepository.count();

        if (mascotas == 0) {
            return Health.down().withDetail("No hay mascotas disponibles ", 0).build();
        } else {
            return Health.up().withDetail("Cantidad de mascotas disponibles ", mascotas).build();
        }
    }
}
