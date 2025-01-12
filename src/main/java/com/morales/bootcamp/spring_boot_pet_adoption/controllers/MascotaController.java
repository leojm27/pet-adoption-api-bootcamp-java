package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import com.morales.bootcamp.spring_boot_pet_adoption.services.MascotaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class MascotaController {

    private final MascotaService service;

    /**
     * @return Todas las Mascotas.
     */
    @GetMapping("/api/mascotas")
    public ResponseEntity<?> getMascotas(@RequestParam(required = false) boolean disponible) {
        try {
            return ResponseEntity.ok(service.getMascotas(disponible));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener Mascotas");
        }
    }

    /**
     * @param id
     * @return Mascota por Id.
     */
    @GetMapping("/api/mascotas/{id}")
    public ResponseEntity<?> getMascota(@PathVariable("id") Long id) {
        try {
            Mascota mascota = service.getMascota(id);
            if (mascota != null) {
                return ResponseEntity.ok(mascota);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Mascota " + id + " no encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener Mascota id " + id);
        }
    }

    /**
     * @param mascota
     * @return Mascota Creada.
     */
    @PostMapping("/api/mascotas")
    public ResponseEntity<?> createMascota(@RequestBody Mascota mascotaToCreate) {
        try {
            Mascota nuevaMascota = service.createMascota(mascotaToCreate);
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
    public ResponseEntity<?> updateMascota(@RequestBody Mascota mascotaToUpdate, @PathVariable("id") Long id) {
        try {
            Mascota mascota = service.updateMascota(mascotaToUpdate, id);
            if (mascota != null) {
                return ResponseEntity.ok(mascota);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar Mascota id " + id);
        }
    }

    /**
     * @param id
     * @return Mascota Eliminada.
     */
    @DeleteMapping("/api/mascotas/{id}")
    public ResponseEntity<?> deleteMascota(@PathVariable("id") Long id) {
        try {
            service.deleteMascota(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ie.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar Mascota id " + id);
        }
    }
}
