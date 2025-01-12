package com.morales.bootcamp.spring_boot_pet_adoption.services.impl;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import com.morales.bootcamp.spring_boot_pet_adoption.models.TipoMascota;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.TipoMascotaRepository;
import com.morales.bootcamp.spring_boot_pet_adoption.services.MascotaService;
import com.morales.bootcamp.spring_boot_pet_adoption.services.TipoMascotaService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class TipoMascotaServiceImpl implements TipoMascotaService {

    private final TipoMascotaRepository repository;
    private final MascotaService mascotaService;

    @Override
    public List<TipoMascota> getTipoMascotas() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(TipoMascota::getId))
                .toList();
    }

    @Override
    public TipoMascota getTipoMascota(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TipoMascota createTipoMascota(TipoMascota tipoMascota) {
        return repository.save(tipoMascota);
    }

    @Override
    public TipoMascota updateTipoMascota(TipoMascota tipoMascota, Long id) {
        return repository.findById(id)
                .map(tipoMascotaToUpdate -> {
                    if (tipoMascota.getNombre() != null) {
                        tipoMascotaToUpdate.setNombre(tipoMascota.getNombre());
                    }
                    repository.save(tipoMascotaToUpdate);
                    return tipoMascotaToUpdate;
                }).orElse(null);
    }

    @Override
    public void deleteTipoMascota(Long id) {
        TipoMascota tipoMascota = this.getTipoMascota(id);
        if(tipoMascota == null){
            throw new IllegalArgumentException("Tipo de Mascota " + id + " no encontrada");
        }

        List<Mascota> mascotas = mascotaService.getMascotas(true);
        if(!mascotas.isEmpty()){
            throw new DataIntegrityViolationException("No se puede eliminar Tipo de Mascota " + id + " porque tiene mascotas asociadas");
        }

        repository.deleteById(id);
    }
}
