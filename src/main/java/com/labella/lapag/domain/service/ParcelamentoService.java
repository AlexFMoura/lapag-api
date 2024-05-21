package com.labella.lapag.domain.service;

import com.labella.lapag.api.mapper.ParcelamentoMapper;
import com.labella.lapag.api.model.ParcelamentoDTO;
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
    private final ParcelamentoMapper parcelamentoMapper;

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


    public List<ParcelamentoDTO> buscarParcelamentoPorClienteId(Long clienteId) {
        List<Parcelamento> listaParcelamento = parcelamentoRepository.findByCliente_Id(clienteId);

        if (listaParcelamento.isEmpty()) {
            throw new NegocioException("Não existe parcelas para esse Cliente");
        }
        return listaParcelamento
                .stream()
                .map(parcelamentoMapper::toModel)
                .toList();
    }
}
