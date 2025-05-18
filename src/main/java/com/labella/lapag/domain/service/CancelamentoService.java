package com.labella.lapag.domain.service;

import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.repository.ParcelamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CancelamentoService {

    private final ParcelamentoRepository parcelamentoRepository;

    public void verificaSeEstaQuitadoEVolta(Parcelas parcela) {
        Parcelamento parcelamento = parcela.getParcelamento();
        if (parcelamento == null) {
            throw new NegocioException("Parcelamento não encontrado!");
        }

        if ("Pago".equals(parcelamento.getStatus())) {
            parcelamento.setStatus("Ativo");
            parcelamentoRepository.saveAndFlush(parcelamento);
        }
    }
}
