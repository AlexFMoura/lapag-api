package com.labella.lapag.api.controller;

import com.labella.lapag.api.mapper.UsuarioMapper;
import com.labella.lapag.api.model.ClienteDTO;
import com.labella.lapag.api.model.ParcelamentoPageDTO;
import com.labella.lapag.api.model.UsuarioDTO;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

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
    public UsuarioDTO salvar(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.salvar(usuario);
        return usuarioMapper.toModel(usuarioSalvo);
    }

    @PutMapping("/{usuarioId}/alterar-senha")
    public ResponseEntity<Void> alterarSenha(
            @PathVariable Integer usuarioId,
            @RequestParam String senhaAtual,
            @RequestParam String novaSenha) {

        try {
            usuarioService.alterarSenha(usuarioId, senhaAtual, novaSenha);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 se algo der errado
        }
    }

    @GetMapping("/nome/{nome}")
    public Usuario buscarNome(@PathVariable String nome) {
        return usuarioService.buscarNome(nome);
    }

    @GetMapping("/page/")
    public Page<UsuarioDTO> getUsuarioPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String sort,
            @RequestParam(value = "nome", required = false) String nome) {

        return usuarioService.getUsuarioPage(page, size, sort, nome);
    }

//    @GetMapping("page/")
//    public Page<ParcelamentoPageDTO> getParcelamentoPaginado(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(value = "cliente", required = false) String cliente,
//            @RequestParam(value = "codigoVenda", required = false) String codigoVenda) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return parcelamentoService.getParcelamentoPage(cliente, codigoVenda, pageRequest);

//    }
}
