package com.morales.bootcamp.spring_boot_pet_adoption.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_mascota")
    private Long idMascota;
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "fecha_adopcion")
    private Date fechaAdopcion; // yyyy-MM-dd

    protected Adopcion(){}

    public Adopcion(Long idMascota, Long idUsuario, Date fechaAdopcion){
        this.idMascota = idMascota;
        this.idUsuario = idUsuario;
        this.fechaAdopcion = fechaAdopcion;
    }
    
}
