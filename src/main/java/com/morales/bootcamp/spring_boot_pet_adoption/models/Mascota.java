package com.morales.bootcamp.spring_boot_pet_adoption.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Column(name = "id_tipo_mascota", insertable = false, updatable = false) // Solo lectura
    private Long idTipoMascota;
    private Integer edad;
    private Boolean disponible;

    /*
    @ManyToOne
    @JoinColumn(name = "id_tipo_mascota") // Relación con la tabla TipoMascota
    private TipoMascota tipoMascota;
    */

    public Mascota(){}

    public Mascota(String nombre, Long idTipoMascota, Integer edad, Boolean disponible){
        this.nombre = nombre;
        this.idTipoMascota = idTipoMascota;
        this.edad = edad;
        this.disponible = disponible;
    }
    
}
