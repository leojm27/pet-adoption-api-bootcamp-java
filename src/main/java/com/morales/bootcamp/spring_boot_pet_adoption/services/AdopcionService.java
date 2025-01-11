package com.morales.bootcamp.spring_boot_pet_adoption.services;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Adopcion;

import java.util.List;

public interface AdopcionService {

    Adopcion createAdopcion(Adopcion adopcion);

    Adopcion updateAdopcion(Adopcion adopcion, Long id);

    Adopcion getAdopcionById(Long id);

    List<Adopcion> getAdopciones();

    void deleteAdopcion(Long id);

}
