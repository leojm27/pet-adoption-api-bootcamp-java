package com.morales.bootcamp.spring_boot_pet_adoption.services.impl;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.MascotaRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.services.MascotaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository repository;

    @Override
    public List<Mascota> getMascotas(boolean disponible) {
        return repository.findAll()
                .stream()
                .filter(mascota -> disponible ? mascota.getDisponible() : true)
                .sorted(Comparator.comparing(Mascota::getId))
                .toList();
    }

    @Override
    public Mascota getMascota(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Mascota createMascota(Mascota mascota) {
        return repository.save(mascota);
    }

    @Override
    public Mascota updateMascota(Mascota mascotaToUpdate, Long id) {
        return repository.findById(id)
                .map(mascota -> {
                    if (mascotaToUpdate.getNombre() != null) {
                        mascota.setNombre(mascotaToUpdate.getNombre());
                    }
                    if (mascotaToUpdate.getIdTipoMascota() != null) {
                        mascota.setIdTipoMascota(mascotaToUpdate.getIdTipoMascota());
                    }
                    if (mascotaToUpdate.getEdad() != null) {
                        mascota.setEdad(mascotaToUpdate.getEdad());
                    }
                    if (mascotaToUpdate.getDisponible() != null) {
                        mascota.setDisponible(mascotaToUpdate.getDisponible());
                    }

                    repository.save(mascota);
                    return mascota;
                }).orElse(null);
    }

    @Override
    public void deleteMascota(Long id) {
        Mascota mascota = this.getMascota(id);
        if(mascota == null) {
            throw new IllegalArgumentException("Mascota " + id + " no encontrada.");
        }

        if(!mascota.getDisponible()) {
            throw new IllegalArgumentException("Mascota " + id + " no puede ser eliminada porque se encuentra Adoptada.");
        }

        repository.deleteById(id);
    }

    @Override
    public Mascota actualizarDisponibilidad(Long id, boolean disponible) {
        return repository.findById(id)
                .map(mascota -> {
                    mascota.setDisponible(disponible);
                    repository.save(mascota);
                    return mascota;
                }).orElse(null);
    }
}
