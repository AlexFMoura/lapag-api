package com.labella.lapag.api.controller;

import com.labella.lapag.domain.model.Cliente;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listar();
    }

    @GetMapping("/{usuarioId}")
    public Usuario buscar(@PathVariable Integer usuarioId) {
        return usuarioService.buscar(usuarioId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario salvar(@Valid @RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }
}
