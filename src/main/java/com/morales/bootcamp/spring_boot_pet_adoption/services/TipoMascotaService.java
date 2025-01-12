package com.morales.bootcamp.spring_boot_pet_adoption.services;

import com.morales.bootcamp.spring_boot_pet_adoption.models.TipoMascota;
import java.util.List;

public interface TipoMascotaService {

    List<TipoMascota> getTipoMascotas();

    TipoMascota getTipoMascota(Long id);

    TipoMascota createTipoMascota(TipoMascota tipoMascota);

    TipoMascota updateTipoMascota(TipoMascota tipoMascota, Long id);

    void deleteTipoMascota(Long id);

}
