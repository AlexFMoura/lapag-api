package com.labella.lapag.api.controller;

import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.service.ParcelasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parcelas")
public class ParcelaController {

    private final ParcelasService parcelasService;

    public ParcelaController(ParcelasService parcelasService) {
        this.parcelasService = parcelasService;
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Parcelas> buscarPorIdCliente(@PathVariable Integer clienteId) {
        return parcelasService.buscarParcelasPorClienteId(clienteId);
    }

}
