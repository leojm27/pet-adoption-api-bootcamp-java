package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Mascota;
import com.morales.bootcamp.spring_boot_pet_adoption.services.MascotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

public class MascotaControllerTests {

    private MockMvc mockMvc;

    @Mock
    private MascotaService mascotaService;

    @InjectMocks
    private MascotaController mascotaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
    }

    /* Method GetAll */
    @Test
    void shouldReturnAllMascotas() {
        boolean disponible = true;
        when(mascotaService.getMascotas(disponible))
                .thenReturn(
                        Arrays.asList(
                                new Mascota("Jimmy", 1L, 4, true),
                                new Mascota("Morita", 1L, 1, true)
                        ));

        ResponseEntity<?> response = mascotaController.getMascotas(disponible);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInAllMascotas() {
        boolean disponible = true;
        when(mascotaService.getMascotas(disponible))
                .thenThrow(new RuntimeException("Error al obtener Mascotas"));

        ResponseEntity<?> response = mascotaController.getMascotas(disponible);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method GetById */
    @Test
    void shouldReturnGetMascotaOk() {
        when(mascotaService.getMascota(1L))
                .thenReturn(new Mascota("Morita", 1L, 1, true));

        ResponseEntity<?> response = mascotaController.getMascota(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInGetMascotaServerError() {
        when(mascotaService.getMascota(1L))
                .thenThrow(new RuntimeException("Error al obtener Mascota"));

        ResponseEntity<?> response = mascotaController.getMascota(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void shouldReturnGetMascotaNotFound() {
        when(mascotaService.getMascota(1L))
                .thenReturn(null);

        ResponseEntity<?> response = mascotaController.getMascota(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /* Method Create */
    @Test
    void shouldReturnCreateMascota() {
        Mascota mascota = new Mascota("Morita", 1L, 1, true);
        when(mascotaService.createMascota(eq(mascota)))
                .thenReturn(mascota);

        ResponseEntity<?> response = mascotaController.createMascota(mascota);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void errorCreateMascotaServerError() {
        Mascota mascota = new Mascota("Morita", 1L, 1, true);
        when(mascotaService.createMascota(eq(mascota)))
                .thenThrow(new RuntimeException("Error al crear Mascota"));

        ResponseEntity<?> response = mascotaController.createMascota(mascota);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method Update */
    @Test
    void shouldReturnUpdateMascotaOk() {
        Mascota mascota = new Mascota("Morita", 1L, 1, true);
        Long id = 1L;
        when(mascotaService.updateMascota(mascota, id))
                .thenReturn(mascota);

        ResponseEntity<?> response = mascotaController.updateMascota(mascota, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInUpdateMascotaNotFound() {
        Mascota mascota = new Mascota("Morita", 1L, 1, true);
        Long id = 1L;
        when(mascotaService.updateMascota(mascota, id))
                .thenReturn(null);

        ResponseEntity<?> response = mascotaController.updateMascota(mascota, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void errorInUpdateMascotaServerError() {
        Mascota mascota = new Mascota("Morita", 1L, 1, true);
        Long id = 1L;
        when(mascotaService.updateMascota(mascota, id))
                .thenThrow(new RuntimeException("Error al actualizar Mascota"));

        ResponseEntity<?> response = mascotaController.updateMascota(mascota, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method Delete */
    @Test
    void shouldReturnDeleteMascotaOk() {
        Long id = 1L;
        doNothing().when(mascotaService).deleteMascota(id);

        ResponseEntity<?> response = mascotaController.deleteMascota(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInDeleteMascotaNotFound() {
        Long id = 1L;
        doThrow(new IllegalArgumentException("Mascota no encontrada"))
                .when(mascotaService).deleteMascota(id);

        ResponseEntity<?> response = mascotaController.deleteMascota(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void errorInDeleteMascotaServerError() {
        Long id = 1L;
        doThrow(new RuntimeException("Mascota no encontrada"))
                .when(mascotaService).deleteMascota(id);

        ResponseEntity<?> response = mascotaController.deleteMascota(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
