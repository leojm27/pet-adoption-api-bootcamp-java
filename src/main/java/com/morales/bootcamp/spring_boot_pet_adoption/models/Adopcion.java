package com.morales.bootcamp.spring_boot_pet_adoption.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String id_mascota;
    private Long id_usuario;
    private Date fecha_adopcion;

    protected Adopcion(){}

    public Adopcion(String id_mascota, Long id_usuario, Date fecha_adopcion){
        this.id_mascota = id_mascota;
        this.id_usuario = id_usuario;
        this.fecha_adopcion = fecha_adopcion;
    }
    
}
