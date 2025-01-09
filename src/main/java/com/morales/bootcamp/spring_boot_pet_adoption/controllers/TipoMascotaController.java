package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.TipoMascota;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.TipoMascotaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TipoMascotaController {
    // como queremos proveer una api-rest, uso resccontroller, en vez de regresar una vista, regresa un response
    // injection de repository
    private final TipoMascotaRepository repository;

    public TipoMascotaController(TipoMascotaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/tipo-mascota")
    public ResponseEntity<List<TipoMascota>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/api/tipo-mascota/{id}")
    public ResponseEntity<?> getTipoMascota(@PathVariable Long id) {
        Optional<TipoMascota> tipoMascota = repository.findById(id);
        if (tipoMascota.isPresent()) {
            return ResponseEntity.ok(tipoMascota.get());
        } else {
            return ResponseEntity.notFound().build();
        }

        // alternativa
        // return tipoMascota.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/tipo-mascota")
    public ResponseEntity<?> createTipoMascota(@RequestBody TipoMascota tipoMascota) {
        try {
            TipoMascota nuevoTipoMascota = repository.save(tipoMascota);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(nuevoTipoMascota);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrio un error al intentar crear nuevo Tipo de Mascota");
        }
    }

    @PutMapping("/api/tipo-mascota/{id}")
    public ResponseEntity<?> updateTipoMascota(@RequestBody TipoMascota tipoMascotaActualizado, @PathVariable Long id) {
        return repository.findById(id)
                .map(tipoMascota -> {
                    tipoMascota.setNombre(tipoMascotaActualizado.getNombre());
                    repository.save(tipoMascota);
                    // return ResponseEntity.noContent().build();
                    return ResponseEntity.ok(tipoMascota);
                }).orElse(ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("api/tipo-mascota/{id}")
    public ResponseEntity<?> deleteTipoMascota(@PathVariable Long id) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar Tipo de Mascota id " + id);
        }
    }

}
