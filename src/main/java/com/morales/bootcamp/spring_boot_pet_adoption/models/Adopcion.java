package com.morales.bootcamp.spring_boot_pet_adoption.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "adopciones")
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_mascota")
    private Long idMascota;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "fecha_adopcion")
    private Date fechaAdopcion;

    /*
    @Column(name="creado_en")
    private Date creadoEn;

    @Column(name="actualizado_en")
    private Date actualizadoEn;

    @Column(name="eliminado_en")
    private Date eliminadoEn;
    */


    /*
    @OneToOne
    @JoinColumn(name = "id_mascota", insertable = false, updatable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;
    */


    protected Adopcion() {
    }

    public Adopcion(Long idMascota, Long idUsuario) {
        this.idMascota = idMascota;
        this.idUsuario = idUsuario;
        this.fechaAdopcion = new Date();
        // this.creadoEn = new Date();
    }

}
