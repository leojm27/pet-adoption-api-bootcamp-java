package com.morales.bootcamp.spring_boot_pet_adoption.services.impl;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Adopcion;
import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.AdopcionRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.MascotaRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.UsuarioRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.services.AdopcionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdopcionServiceImpl implements AdopcionService {

    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final AdopcionRepository adopcionRepository;

    @Override
    public Adopcion createAdopcion(Adopcion adopcion) {
        /* Validar existencia del usuario */
        usuarioRepository.findById(adopcion.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));

        /* Validar existencia de la mascota */
        Mascota mascota = mascotaRepository.findById(adopcion.getIdMascota())
                .orElseThrow(() -> new IllegalArgumentException("La mascota no existe"));

        /* Validar si la mascota está disponible para adopción */
        if (!mascota.getDisponible()) {
            throw new IllegalArgumentException("La mascota no está disponible para adopción");
        }

        /* actualizamos disponible a false */
        // TODO separar en un método de mascotaService
        mascota.setDisponible(false);
        mascotaRepository.save(mascota);

        return adopcionRepository.save(adopcion);
    }

    @Override
    public Adopcion updateAdopcion(Adopcion updateAdopcion, Long id) {
        Adopcion adopcion = adopcionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Adopcion " + id + " no encontrada"));

        if (updateAdopcion.getFechaAdopcion() != null) {
            adopcion.setFechaAdopcion(updateAdopcion.getFechaAdopcion());
        }
        if (updateAdopcion.getIdMascota() != null) {
            adopcion.setIdMascota(updateAdopcion.getIdMascota());
        }
        if (updateAdopcion.getIdUsuario() != null) {
            adopcion.setIdUsuario(updateAdopcion.getIdUsuario());
        }

        adopcionRepository.save(adopcion);
        return adopcion;
    }

    @Override
    public Adopcion getAdopcionById(Long id) {
        return adopcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Adopcion " + id + " no encontrada"));
    }

    @Override
    public List<Adopcion> getAdopciones() {
        return adopcionRepository.findAll();
    }

    @Override
    public void deleteAdopcion(Long id){
        if(!adopcionRepository.existsById(id)){
            throw new IllegalArgumentException("Adopcion " + id + " no encontrada");
        }

        adopcionRepository.deleteById(id);
    }

}
