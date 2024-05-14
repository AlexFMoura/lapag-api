package com.labella.lapag.domain.service;

import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Cliente;
import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.repository.ParcelamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ParcelamentoService {

    private final ParcelamentoRepository parcelamentoRepository;
    private final ClienteService clienteService;
    private final ParcelasService parcelasService;

    @Transactional
    public Parcelamento salvar(Parcelamento parcelamento) {
        if (parcelamento.getId() != null) {
            throw new NegocioException("Parcelamento já existente");
        }

        List<Parcelas> parcelas = parcelasService.criarParcela(parcelamento);

        Cliente cliente = clienteService.buscar(parcelamento.getCliente().getId());

        parcelamento.setCliente(cliente);
        parcelamento.setParcelas(parcelas);
        return parcelamentoRepository.save(parcelamento);
    }
}
