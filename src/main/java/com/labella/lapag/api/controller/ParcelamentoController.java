package com.labella.lapag.api.controller;

import com.labella.lapag.api.mapper.ParcelamentoMapper;
import com.labella.lapag.api.model.CriarParcelamentoDTO;
import com.labella.lapag.api.model.ParcelamentoDTO;
import com.labella.lapag.api.model.ParcelamentoPageDTO;
import com.labella.lapag.api.model.ParcelasDTO;
import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.repository.ParcelamentoRepository;
import com.labella.lapag.domain.service.ParcelamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/parcelamento")
public class ParcelamentoController {

    private final ParcelamentoRepository parcelamentoRepository;
    private final ParcelamentoService parcelamentoService;
    private final ParcelamentoMapper parcelamentoMapper;

    @GetMapping
    public List<ParcelamentoDTO> listar() {
        return parcelamentoService.listar();
    }

    @GetMapping("/{parcelamentoId}")
    public ResponseEntity<ParcelamentoDTO> buscar(@PathVariable Long parcelamentoId) {
        return parcelamentoRepository.findById(parcelamentoId)
                .map(parcelamentoMapper::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public List<ParcelamentoDTO> buscarPorIdCliente(@PathVariable Long clienteId) {
        return parcelamentoService.buscarParcelamentoPorClienteId(clienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelamentoDTO salvar(@Valid @RequestBody CriarParcelamentoDTO parcelamento) {
        Parcelamento parcelamentoSalvo = parcelamentoService.salvar(parcelamento);
        return parcelamentoMapper.toModel(parcelamentoSalvo);
    }

    @DeleteMapping("/{parcelamentoId}")
    public ResponseEntity<String> excluir(@PathVariable Long parcelamentoId) {
        parcelamentoService.excluir(parcelamentoId);
        return ResponseEntity.ok("Excluido com sucesso!");
    }

    @GetMapping("/venda/{vendaId}")
    public ResponseEntity<ParcelamentoDTO> buscarVendaId(@PathVariable Integer vendaId) {
        return parcelamentoRepository.findByVendaId(vendaId)
                .map(parcelamentoMapper::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("page/")
    public Page<ParcelamentoPageDTO> getParcelamentoPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "cliente", required = false) String cliente,
            @RequestParam(value = "codigoVenda", required = false) String codigoVenda) {

        PageRequest pageRequest = PageRequest.of(page, size);
        return parcelamentoService.getParcelamentoPage(cliente, codigoVenda, pageRequest);

    }
}
