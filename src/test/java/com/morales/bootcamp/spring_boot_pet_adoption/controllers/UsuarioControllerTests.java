package com.morales.bootcamp.spring_boot_pet_adoption.controllers;

import com.morales.bootcamp.spring_boot_pet_adoption.models.Usuario;
import com.morales.bootcamp.spring_boot_pet_adoption.services.UsuarioService;
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

public class UsuarioControllerTests {
    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    /* Method GetAll */
    @Test
    void shouldReturnAllUsuarios() {
        when(usuarioService.getUsuarios())
                .thenReturn(
                        Arrays.asList(
                                new Usuario("Lionel", "lio@gmail.com", "111010"),
                                new Usuario("Gonzalo", "gonza@gmail.com", "114444"),
                                new Usuario("Julian", "julian@gmail.com", "119999")
                        ));

        ResponseEntity<?> response = usuarioController.getUsuarios();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInAllUsuarios() {
        when(usuarioService.getUsuarios())
                .thenThrow(new RuntimeException("Error al obtener Usuarios"));

        ResponseEntity<?> response = usuarioController.getUsuarios();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method GetByID */
    @Test
    void shouldReturnGetUsuarioOk() {
        when(usuarioService.getUsuarioById(1L))
                .thenReturn(new Usuario("Lionel", "lio@gmail.com", "111010"));

        ResponseEntity<?> response = usuarioController.getUsuario(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInGetUsuarioServerError() {
        when(usuarioService.getUsuarioById(1L))
                .thenThrow(new RuntimeException("Error al obtener Usuario"));

        ResponseEntity<?> response = usuarioController.getUsuario(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void shouldReturnGetUsuarioNotFound() {
        when(usuarioService.getUsuarioById(1L))
                .thenReturn(null);

        ResponseEntity<?> response = usuarioController.getUsuario(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /* Method Create */
    @Test
    void shouldReturnCreateUsuario() {
        Usuario usuario = new Usuario("Gonzalo", "gonza@gmail.com", "114444");
        when(usuarioService.createUsuario(eq(usuario)))
                .thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.createUsuario(usuario);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void errorCreateUsuarioServerError() {
        Usuario usuario = new Usuario("Gonzalo", "gonza@gmail.com", "114444");
        when(usuarioService.createUsuario(eq(usuario)))
                .thenThrow(new RuntimeException("Error al crear Usuario"));

        ResponseEntity<?> response = usuarioController.createUsuario(usuario);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method Update */
    @Test
    void shouldReturnUpdateUsuarioOk() {
        Usuario usuario = new Usuario("Gonzalo", "gonza@gmail.com", "114444");
        Long id = 1L;
        when(usuarioService.updateUsuario(usuario, id))
                .thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.updateUsuario(usuario, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInUpdateUsuarioNotFound() {
        Usuario usuario = new Usuario("Gonzalo", "gonza@gmail.com", "114444");
        Long id = 1L;
        when(usuarioService.updateUsuario(usuario, id))
                .thenReturn(null);

        ResponseEntity<?> response = usuarioController.updateUsuario(usuario, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void errorInUpdateUsuarioServerError() {
        Usuario usuario = new Usuario("Gonzalo", "gonza@gmail.com", "114444");
        Long id = 1L;
        when(usuarioService.updateUsuario(usuario, id))
                .thenThrow(new RuntimeException("Error al actualizar Usuario"));

        ResponseEntity<?> response = usuarioController.updateUsuario(usuario, id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Method Delete */
    @Test
    void shouldReturnDeleteUsuarioOk() {
        Long id = 1L;
        doNothing().when(usuarioService).deleteUsuario(id);

        ResponseEntity<?> response = usuarioController.deleteUsuario(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void errorInDeleteUsuarioNotFound() {
        Long id = 1L;
        doThrow(new IllegalArgumentException("Usuario no encontrada"))
                .when(usuarioService).deleteUsuario(id);

        ResponseEntity<?> response = usuarioController.deleteUsuario(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void errorInDeleteUsuarioServerError() {
        Long id = 1L;
        doThrow(new RuntimeException("Usuario no encontrada"))
                .when(usuarioService).deleteUsuario(id);

        ResponseEntity<?> response = usuarioController.deleteUsuario(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
