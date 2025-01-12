package com.morales.bootcamp.spring_boot_pet_adoption.services;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import java.util.List;

public interface MascotaService {

    List<Mascota> getMascotas(boolean disponible);

    Mascota getMascota(Long id);

    Mascota createMascota(Mascota mascota);

    Mascota updateMascota(Mascota mascota, Long id);

    void deleteMascota(Long id);

    Mascota actualizarDisponibilidad(Long id, boolean disponible);

}
