package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Adopcion;
import com.morales.bootcamp.spring_boot_pet_adoption.services.AdopcionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AdopcionController {
    private final AdopcionService adopcionService;

    /**
     * @return Todas las Adopciones.
     */
    @GetMapping("/api/adopciones")
    public ResponseEntity<?> allAdopciones() {
        try {
            return ResponseEntity.ok(adopcionService.getAdopciones());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener Adopciones");
        }
    }

    /**
     * @param id
     * @return Adopcion por Id.
     */
    @GetMapping("/api/adopciones/{id}")
    public ResponseEntity<?> getAdopcion(@PathVariable("id") Long id) {
        try {
            Adopcion adopcion = adopcionService.getAdopcionById(id);
            if(adopcion != null) {
                return ResponseEntity.ok(adopcion);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener Adopcion id " + id);
        }
    }

    /**
     * @param adopcion
     * @return Adopcion Creada.
     */
    @PostMapping("/api/adopciones")
    public ResponseEntity<?> createAdopcion(@RequestBody Adopcion adopcion) {
        try {
            Adopcion nuevaAdopcion = adopcionService.createAdopcion(adopcion);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevaAdopcion);
        } catch (IllegalArgumentException ie) {
            return ResponseEntity.badRequest().body(ie.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error al intentar crear nueva Adopcion");
        }
    }

    /**
     * @param adopcionToUpdate
     * @param id
     * @return Adopcion Actualizada.
     */
    @PutMapping("/api/adopciones/{id}")
    public ResponseEntity<?> updateAdopcion(@RequestBody Adopcion adopcionToUpdate, @PathVariable("id") Long id) {
        try {
            Adopcion adopcion = adopcionService.updateAdopcion(adopcionToUpdate, id);
            if(adopcion != null) {
                return ResponseEntity.ok(adopcion);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar Adopcion id " + id);
        }
    }


    /**
     * @param id
     * @return Adopcion Eliminada.
     */
    @DeleteMapping("/api/adopciones/{id}")
    public ResponseEntity<?> deleteAdopcion(@PathVariable("id") Long id) {
        try {
            adopcionService.deleteAdopcion(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ie.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar Adopcion id " + id);
        }
    }

}
