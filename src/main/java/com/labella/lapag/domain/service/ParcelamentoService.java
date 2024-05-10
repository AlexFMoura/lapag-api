package com.labella.lapag.domain.service;

import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Cliente;
import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.repository.ParcelamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ParcelamentoService {

    private final ParcelamentoRepository parcelamentoRepository;
    private final ClienteService clienteService;

    @Transactional
    public Parcelamento salvar(Parcelamento parcelamento) {
        if (parcelamento.getId() != null) {
            throw new NegocioException("Parcelamento já existente");
        }

        Cliente cliente = clienteService.buscar(parcelamento.getCliente().getId());

        parcelamento.setCliente(cliente);
        return parcelamentoRepository.save(parcelamento);
    }
}
