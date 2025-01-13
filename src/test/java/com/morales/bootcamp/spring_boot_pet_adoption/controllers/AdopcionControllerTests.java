package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Adopcion;
import com.morales.bootcamp.spring_boot_pet_adoption.services.AdopcionService;
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

public class AdopcionControllerTests {

    private MockMvc mockMvc;

    @Mock
    private AdopcionService adopcionService;

    @InjectMocks
    private AdopcionController adopcionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adopcionController).build();
    }

    /* Method GetAll */
    @Test
    void shouldReturnAllAdopciones() {
        when(adopcionService.getAdopciones())
                .thenReturn(
                        Arrays.asList(
                                new Adopcion(1L, 9L),
                                new Adopcion(2L, 3L)
                        ));

        ResponseEntity<?> response = adopcionController.allAdopciones();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInAllAdopciones() {
        when(adopcionService.getAdopciones())
                .thenThrow(new RuntimeException("Error al obtener Adopciones"));

        ResponseEntity<?> response = adopcionController.allAdopciones();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method GetByID */
    @Test
    void shouldReturnGetAdopcionOk() {
        when(adopcionService.getAdopcionById(1L))
                .thenReturn(new Adopcion(1L, 9L));

        ResponseEntity<?> response = adopcionController.getAdopcion(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInGetAdopcionServerError() {
        when(adopcionService.getAdopcionById(1L))
                .thenThrow(new RuntimeException("Error al obtener Adopcion"));

        ResponseEntity<?> response = adopcionController.getAdopcion(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void shouldReturnGetAdopcionNotFound() {
        when(adopcionService.getAdopcionById(1L))
                .thenReturn(null);

        ResponseEntity<?> response = adopcionController.getAdopcion(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /* Method Create */
    @Test
    void shouldReturnCreateAdopcion() {
        Adopcion adopcion = new Adopcion(1L, 9L);
        when(adopcionService.createAdopcion(eq(adopcion)))
                .thenReturn(adopcion);

        ResponseEntity<?> response = adopcionController.createAdopcion(adopcion);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void errorCreateAdopcionServerError() {
        Adopcion adopcion = new Adopcion(1L, 9L);
        when(adopcionService.createAdopcion(eq(adopcion)))
                .thenThrow(new RuntimeException("Error al crear Adopcion"));

        ResponseEntity<?> response = adopcionController.createAdopcion(adopcion);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void errorCreateAdopcionWhenUsuarioOrMascotaNotFound() {
        Adopcion adopcion = new Adopcion(1L, 9L);
        when(adopcionService.createAdopcion(eq(adopcion)))
                .thenThrow(new IllegalArgumentException("El usuario 9 no existe"));

        ResponseEntity<?> response = adopcionController.createAdopcion(adopcion);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /* Method Update */
    @Test
    void shouldReturnUpdateAdopcionOk() {
        Adopcion adopcion = new Adopcion(1L, 9L);
        Long id = 1L;
        when(adopcionService.updateAdopcion(adopcion, id))
                .thenReturn(adopcion);

        ResponseEntity<?> response = adopcionController.updateAdopcion(adopcion, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInUpdateAdopcionNotFound() {
        Adopcion adopcion = new Adopcion(1L, 9L);
        Long id = 1L;
        when(adopcionService.updateAdopcion(adopcion, id))
                .thenReturn(null);

        ResponseEntity<?> response = adopcionController.updateAdopcion(adopcion, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void errorInUpdateAdopcionServerError() {
        Adopcion adopcion = new Adopcion(1L, 9L);
        Long id = 1L;
        when(adopcionService.updateAdopcion(adopcion, id))
                .thenThrow(new RuntimeException("Error al actualizar Adopcion"));

        ResponseEntity<?> response = adopcionController.updateAdopcion(adopcion, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method Delete */
    @Test
    void shouldReturnDeleteAdopcionOk() {
        Long id = 1L;
        doNothing().when(adopcionService).deleteAdopcion(id);

        ResponseEntity<?> response = adopcionController.deleteAdopcion(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInDeleteAdopcionNotFound() {
        Long id = 1L;

        doThrow(new IllegalArgumentException("Adopción no encontrada"))
                .when(adopcionService).deleteAdopcion(id);

        ResponseEntity<?> response = adopcionController.deleteAdopcion(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void errorInDeleteAdopcionServerError() {
        Long id = 1L;

        doThrow(new RuntimeException("Adopción no encontrada"))
                .when(adopcionService).deleteAdopcion(id);

        ResponseEntity<?> response = adopcionController.deleteAdopcion(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

