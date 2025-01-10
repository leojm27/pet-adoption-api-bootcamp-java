package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Adopcion;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.AdopcionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdopcionController {

    private final AdopcionRepository repository;

    public AdopcionController(AdopcionRepository adopcionRepository) {
        this.repository = adopcionRepository;
    }

    /**
     * @return Todas las Adopciones.
     */
    @GetMapping("/api/adopciones")
    public ResponseEntity<List<Adopcion>> allAdoptions(Long id) {
        return ResponseEntity.ok(repository.findAll());
    }

    /**
     * @param id
     * @return Adopcion por Id.
     */
    @GetMapping("/api/adopciones/{id}")
    public ResponseEntity<?> getAdoption(@PathVariable("id") Long id) {
        Optional<Adopcion> adopcionObtained = repository.findById(id);
        if (adopcionObtained.isPresent()) {
            return ResponseEntity.ok(adopcionObtained.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param adopcion
     * @return Adopcion Creada.
     */
    @PostMapping("/api/adopciones")
    public ResponseEntity<?> createPet(@RequestBody Adopcion adopcion) {
        try {
            Adopcion nuevaAdopcion = repository.save(adopcion);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevaAdopcion);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrio un error al intentar crear nueva Adopcion");
        }
    }

    /**
     * @param adopcionActualizada
     * @param id
     * @return Adopcion Actualizada.
     */
    @PatchMapping("/api/mascotas/{id}")
    public ResponseEntity<Adopcion> updateAdopcion(@RequestBody Adopcion adopcionToUpdate, @PathVariable("id") Long id) {
        return repository.findById(id)
                .map(adopcion -> {
                    adopcion.setFecha_adopcion(adopcionToUpdate.getFecha_adopcion());
                    adopcion.setId_mascota(adopcionToUpdate.getId_mascota());
                    adopcion.setId_usuario(adopcionToUpdate.getId_usuario());

                    repository.save(adopcion);
                    return ResponseEntity.ok(adopcion);
                }).orElse(ResponseEntity.notFound().build()
                );
    }


    /**
     * @param id
     * @return Adopcion Eliminada.
     */
    @DeleteMapping("/api/adopciones/{id}")
    public ResponseEntity<?> deleteAdoption(@PathVariable("id") Long id) {
        try {
            if(!repository.existsById(id)){
                return ResponseEntity.notFound().build();
            }

            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar Adopcion id " + id);
        }
    }


    /**
     * @param userId
     * @return Adopciones de Usuario.
     */
    /*
    @GetMapping("/api/adopciones/{userId}")
    public ResponseEntity<List<Adopcion>> getAdoptionByUserId(@PathVariable("userId") Long userId) {
        return repository.findByUserId(userId);
    }
    */
}
