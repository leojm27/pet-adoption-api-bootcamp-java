package com.morales.bootcamp.spring_boot_pet_adoption.services.impl;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Adopcion;
import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import com.morales.bootcamp.spring_boot_pet_adoption.models.Usuario;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.AdopcionRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.MascotaRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.UsuarioRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.services.AdopcionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AdopcionServiceImpl implements AdopcionService {

    private final AdopcionRepository adopcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;

    @Override
    public Adopcion getAdopcionById(Long id) {
        return adopcionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Adopcion> getAdopciones() {
        return adopcionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Adopcion::getId))
                .toList();
    }

    @Override
    public Adopcion createAdopcion(Adopcion adopcion) {
        /* Validar existencia del usuario */
        Usuario usuario = usuarioRepository.findById(adopcion.getIdUsuario()).orElse(null);

        if (usuario == null) {
            throw new IllegalArgumentException("El usuario " + adopcion.getIdUsuario() + " no existe");
        }

        /* Validar existencia de la mascota */
        Mascota mascota = mascotaRepository.findById(adopcion.getIdMascota()).orElse(null);
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota " + adopcion.getIdMascota() + " no existe");
        }

        /* Validar si la mascota está disponible para adopción */
        if (!mascota.getDisponible()) {
            throw new IllegalArgumentException("Mascota " + mascota.getId() + ", de nombre '" + mascota.getNombre() + "' no está disponible para adopción");
        }

        /* actualizamos disponible a false */
        mascota.setDisponible(false);
        mascotaRepository.save(mascota);

        try {
            adopcion.setFechaAdopcion(new Date());
            return adopcionRepository.save(adopcion);
        } catch (Exception e) {
            /* actualizamos disponible a true */
            mascota.setDisponible(true);
            mascotaRepository.save(mascota);
            throw e;
        }
    }

    @Override
    public Adopcion updateAdopcion(Adopcion updateAdopcion, Long id) {
        return adopcionRepository.findById(id)
                .map(adopcion -> {
                    if (updateAdopcion.getIdMascota() != null) {
                        adopcion.setIdMascota(updateAdopcion.getIdMascota());
                    }
                    if (updateAdopcion.getIdUsuario() != null) {
                        adopcion.setIdUsuario(updateAdopcion.getIdUsuario());
                    }

                    adopcionRepository.save(adopcion);
                    return adopcion;
                }).orElse(null);
    }

    @Override
    public void deleteAdopcion(Long id) {
        Adopcion adopcion = this.getAdopcionById(id);
        if (adopcion == null) {
            throw new IllegalArgumentException("Adopcion " + id + " no encontrada");
        }

        /* se disponibiliza Mascota */
        Mascota mascota = mascotaRepository.findById(adopcion.getIdMascota()).orElse(null);
        if (mascota != null) {
            mascota.setDisponible(true);
            mascotaRepository.save(mascota);
        }

        adopcionRepository.deleteById(id);
    }

}
