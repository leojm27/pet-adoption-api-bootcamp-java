package com.morales.bootcamp.spring_boot_pet_adoption.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correo_electronico;
    private String telefono;


    public Usuario(){}

    public Usuario(String nombre, String correo_electronico, String telefono){
        this.nombre = nombre;
        this.correo_electronico = correo_electronico;
        this.telefono = telefono;
    }

}
