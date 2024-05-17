package com.labella.lapag.api.controller;

import com.labella.lapag.api.model.ParcelasDTO;
import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.service.ParcelasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parcelas")
public class ParcelaController {

    private final ParcelasService parcelasService;

    public ParcelaController(ParcelasService parcelasService) {
        this.parcelasService = parcelasService;
    }

    @GetMapping("/cliente/{clienteId}")
    public List<ParcelasDTO> buscarPorIdCliente(@PathVariable Integer clienteId) {
        return parcelasService.buscarParcelasPorClienteId(clienteId);
    }

    @PutMapping("/pagamento/parcela/{id}")
    public ResponseEntity<String> pagamento(@PathVariable Long id) {
        return parcelasService.marcarPago(id);
    }
}
