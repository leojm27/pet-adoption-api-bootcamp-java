package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.TipoMascota;
import com.morales.bootcamp.spring_boot_pet_adoption.services.TipoMascotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

public class TipoMascotaControllerTests {
    private MockMvc mockMvc;

    @Mock
    private TipoMascotaService tipoMascotaService;

    @InjectMocks
    private TipoMascotaController tipoMascotaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tipoMascotaController).build();
    }

    /* Method GetAll */
    @Test
    void shouldReturnAllTiposMascota() {
        when(tipoMascotaService.getTipoMascotas())
                .thenReturn(
                        Arrays.asList(
                                new TipoMascota("Perro"),
                                new TipoMascota("Gato")
                        ));

        ResponseEntity<?> response = tipoMascotaController.getTipoMascotas();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInAllTiposMascota() {
        when(tipoMascotaService.getTipoMascotas())
                .thenThrow(new RuntimeException("Error al obtener Tipos de mascota"));

        ResponseEntity<?> response = tipoMascotaController.getTipoMascotas();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method GetByID */
    @Test
    void shouldReturnGetTipoMascotaOk() {
        when(tipoMascotaService.getTipoMascota(1L))
                .thenReturn(new TipoMascota("Gato"));

        ResponseEntity<?> response = tipoMascotaController.getTipoMascota(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInGetTipoMascotaServerError() {
        when(tipoMascotaService.getTipoMascota(1L))
                .thenThrow(new RuntimeException("Error al obtener Tipo de mascota"));

        ResponseEntity<?> response = tipoMascotaController.getTipoMascota(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void shouldReturnGetTipoMascotaNotFound() {
        when(tipoMascotaService.getTipoMascota(1L))
                .thenReturn(null);

        ResponseEntity<?> response = tipoMascotaController.getTipoMascota(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /* Method Create */
    @Test
    void shouldReturnCreateTipoMascota() {
        TipoMascota tipoMascota = new TipoMascota("Gato");
        when(tipoMascotaService.createTipoMascota(eq(tipoMascota)))
                .thenReturn(tipoMascota);

        ResponseEntity<?> response = tipoMascotaController.createTipoMascota(tipoMascota);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void errorCreateTipoMascotaServerError() {
        TipoMascota tipoMascota = new TipoMascota("Gato");
        when(tipoMascotaService.createTipoMascota(eq(tipoMascota)))
                .thenThrow(new RuntimeException("Error al crear Tipo de mascota"));

        ResponseEntity<?> response = tipoMascotaController.createTipoMascota(tipoMascota);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method Update */
    @Test
    void shouldReturnTipoMascotaAdopcionOk() {
        TipoMascota tipoMascota = new TipoMascota("Gato");
        Long id = 1L;
        when(tipoMascotaService.updateTipoMascota(tipoMascota, id))
                .thenReturn(tipoMascota);

        ResponseEntity<?> response = tipoMascotaController.updateTipoMascota(tipoMascota, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInUpdateTipoMascotaNotFound() {
        TipoMascota tipoMascota = new TipoMascota("Gato");
        Long id = 1L;
        when(tipoMascotaService.updateTipoMascota(tipoMascota, id))
                .thenReturn(null);

        ResponseEntity<?> response = tipoMascotaController.updateTipoMascota(tipoMascota, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void errorInUpdateTipoMascotaServerError() {
        TipoMascota tipoMascota = new TipoMascota("Gato");
        Long id = 1L;
        when(tipoMascotaService.updateTipoMascota(tipoMascota, id))
                .thenThrow(new RuntimeException("Error al actualizar Tipo de mascota"));

        ResponseEntity<?> response = tipoMascotaController.updateTipoMascota(tipoMascota, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method Delete */
    @Test
    void shouldReturnDeleteTipoMascotaOk() {
        Long id = 1L;
        doNothing().when(tipoMascotaService).deleteTipoMascota(id);

        ResponseEntity<?> response = tipoMascotaController.deleteTipoMascota(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInDeleteTipoMascotaNotFound() {
        Long id = 1L;

        doThrow(new IllegalArgumentException("Tipo de mascota no encontrada"))
                .when(tipoMascotaService).deleteTipoMascota(id);

        ResponseEntity<?> response = tipoMascotaController.deleteTipoMascota(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void errorInDeleteTipoMascotaServerError() {
        Long id = 1L;
        doThrow(new RuntimeException("Tipo de mascota no encontrada"))
                .when(tipoMascotaService).deleteTipoMascota(id);

        ResponseEntity<?> response = tipoMascotaController.deleteTipoMascota(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void errorInDeleteTipoMascotaDataIntegrityViolation() {
        Long id = 1L;
        doThrow(new DataIntegrityViolationException("No se puede eliminar Tipo de Mascota 1 porque tiene mascotas asociadas"))
                .when(tipoMascotaService).deleteTipoMascota(id);

        ResponseEntity<?> response = tipoMascotaController.deleteTipoMascota(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
}
