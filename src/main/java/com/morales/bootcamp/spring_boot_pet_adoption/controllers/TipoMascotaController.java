package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.TipoMascota;
import com.morales.bootcamp.spring_boot_pet_adoption.services.TipoMascotaService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TipoMascotaController {
    private final TipoMascotaService service;

    /**
     * @return Devuelve todos los Tipos de Mascotas
     */
    @GetMapping("/api/tipo-mascota")
    public ResponseEntity<?> getTipoMascotas() {
        try {
            return ResponseEntity.ok(service.getTipoMascotas());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener Tipos de mascotas");
        }
    }

    /**
     * @param id
     * @return Devuelve Tipo de Mascota por id
     */
    @GetMapping("/api/tipo-mascota/{id}")
    public ResponseEntity<?> getTipoMascota(@PathVariable("id") Long id) {
        try {
            TipoMascota tipoMascota = service.getTipoMascota(id);
            if (tipoMascota != null) {
                return ResponseEntity.ok(tipoMascota);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Tipo de Mascota " + id + " no encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener Tipo de Mascota id " + id);
        }
    }

    /**
     * @param tipoMascota
     * @return Crea Tipo de Mascota
     */
    @PostMapping("/api/tipo-mascota")
    public ResponseEntity<?> createTipoMascota(@RequestBody TipoMascota tipoMascota) {
        try {
            TipoMascota nuevoTipoMascota = service.createTipoMascota(tipoMascota);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevoTipoMascota);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrio un error al intentar crear nuevo Tipo de Mascota");
        }
    }

    /**
     * @param tipoMascotaActualizado
     * @param id
     * @return Actualiza Tipo de Mascota
     */
    @PutMapping("/api/tipo-mascota/{id}")
    public ResponseEntity<?> updateTipoMascota(@RequestBody TipoMascota tipoMascotaActualizado, @PathVariable("id") Long id) {
        try {
            TipoMascota tipoMascota = service.updateTipoMascota(tipoMascotaActualizado, id);
            if (tipoMascota != null) {
                return ResponseEntity.ok(tipoMascota);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar Tipo de Mascota id " + id);
        }
    }

    /**
     * @param id
     * @return Elimina Tipo de Mascota
     */
    @DeleteMapping("api/tipo-mascota/{id}")
    public ResponseEntity<?> deleteTipoMascota(@PathVariable("id") Long id) {
        try {
            service.deleteTipoMascota(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ie) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ie.getMessage());
        } catch (DataIntegrityViolationException datae) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(datae.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar Tipo de Mascota id " + id);
        }
    }

}
