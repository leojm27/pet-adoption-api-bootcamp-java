package com.morales.bootcamp.spring_boot_pet_adoption.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(name = "correo_electronico")
    private String correoElectronico;
    private String telefono;

    @OneToMany(mappedBy = "idUsuario")
    private List<Adopcion> adopciones;


    public Usuario(){}

    public Usuario(String nombre, String correo_electronico, String telefono){
        this.nombre = nombre;
        this.correoElectronico = correo_electronico;
        this.telefono = telefono;
    }

}
