package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.MascotaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MascotaController {

    private final MascotaRepository repository;

    public MascotaController(MascotaRepository mascotaRepository) {
        this.repository = mascotaRepository;
    }

    /**
     * @return Todas las Mascotas.
     */
    @GetMapping("/api/mascotas")
    public ResponseEntity<List<Mascota>> allMascotas() {
        return ResponseEntity.ok(repository.findAll());
    }

    /**
     * @param id
     * @return Mascota por Id.
     */
    @GetMapping("/api/mascotas/{id}")
    public ResponseEntity<?> getMascota(@PathVariable("id") Long id) {
        Optional<Mascota> mascotaObtained = repository.findById(id);
        if (mascotaObtained.isPresent()) {
            return ResponseEntity.ok(mascotaObtained);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mascota " + id + " no encontrada");
        }
    }

    /**
     * @param mascota
     * @return Mascota Creada.
     */
    @PostMapping("/api/mascotas")
    public ResponseEntity<?> createMascota(@RequestBody Mascota mascotaToCreate) {
        try {
            Mascota nuevaMascota = repository.save(mascotaToCreate);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevaMascota);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrio un error al intentar crear nueva Mascota");
        }
    }

    /**
     * @param mascotaToUpdate
     * @param id
     * @return Mascota Actualizada.
     */
    @PutMapping("/api/mascotas/{id}")
    public ResponseEntity<Mascota> updateMascota(@RequestBody Mascota mascotaToUpdate, @PathVariable("id") Long id) {
        return repository.findById(id)
                .map(mascota -> {
                    if (mascotaToUpdate.getNombre() != null) {
                        mascota.setNombre(mascotaToUpdate.getNombre());
                    }
                    if(mascotaToUpdate.getIdTipoMascota() != null) {
                        mascota.setIdTipoMascota(mascotaToUpdate.getIdTipoMascota());
                    }
                    if(mascotaToUpdate.getEdad() != null) {
                        mascota.setEdad(mascotaToUpdate.getEdad());
                    }
                    if (mascotaToUpdate.getDisponible() != null){
                        mascota.setDisponible(mascotaToUpdate.getDisponible());
                    }

                    repository.save(mascota);
                    return ResponseEntity.ok(mascota);
                }).orElse(ResponseEntity.notFound().build()
                );
    }

    /**
     * @param id
     * @return Mascota Eliminada.
     */
    @DeleteMapping("/api/mascotas/{id}")
    public ResponseEntity<?> deleteMascota(@PathVariable("id") Long id) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar Mascota id " + id);
        }
    }
}
