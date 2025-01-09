package com.morales.bootcamp.spring_boot_pet_adoption.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String tipo_mascota;
    private Integer edad;
    private Boolean disponible;

    protected Mascota(){}

    public Mascota(String nombre, String tipo_mascota, Integer edad, Boolean disponible){
        this.nombre = nombre;
        this.tipo_mascota = tipo_mascota;
        this.edad = edad;
        this.disponible = disponible;
    }
    
}
