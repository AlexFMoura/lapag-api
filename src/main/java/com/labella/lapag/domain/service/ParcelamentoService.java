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
import java.util.Optional;

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

    @Transactional
    public void excluir(Long id) {
        boolean existe = false;
        Optional<Parcelamento> parcelamento = parcelamentoRepository.findById(id);

        if (parcelamento.isEmpty()) {
            throw new NegocioException("Não existe parcelamento");
        }

        if (parcelamento.get().getParcelas().isEmpty()) {
            parcelamentoRepository.deleteById(id);
        } else {
            List<Parcelas> parcelas = parcelamento.get().getParcelas();
            for (Parcelas parcela : parcelas) {
                if (parcela.getDataPagamento() != null) {
                    existe = true;
                }
            }
        }

        if (existe) {
            throw new NegocioException("Existe parcela paga para esse parcelamento.");
        } else {
            parcelamentoRepository.deleteById(id);
        }
    }
}
