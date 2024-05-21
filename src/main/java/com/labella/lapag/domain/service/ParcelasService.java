package com.labella.lapag.domain.service;

import com.labella.lapag.api.mapper.ParcelamentoMapper;
import com.labella.lapag.api.mapper.ParcelasMapper;
import com.labella.lapag.api.model.ParcelasDTO;
import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.repository.ParcelasRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ParcelasService {

    private final ParcelasRepository parcelasRepository;
    private final ParcelamentoMapper parcelamentoMapper;
    private final ParcelasMapper parcelasMapper;

    public List<Parcelas> criarParcela(Parcelamento parcelamento) {
        List<Parcelas> parcelas = new ArrayList<Parcelas>();
        BigDecimal valorParcela = parcelamento.getValorTotal().divide(
                new BigDecimal(parcelamento.getQtdParcelas().toString()), 2, RoundingMode.HALF_EVEN);
        BigDecimal diferenca = parcelamento.getValorTotal().subtract(valorParcela.multiply(BigDecimal.valueOf(parcelamento.getQtdParcelas())));
        for (int i = 1; i <= parcelamento.getQtdParcelas(); i++) {
            Parcelas parcela = new Parcelas();
            parcela.setValorParcela(valorParcela.add(diferenca));
            parcela.setParcela(i);
            parcela.setDataVencimento(montaDataVencimento(i, parcelamento.getPrimeiroVencimento()));
            diferenca = BigDecimal.ZERO;
            parcela.setParcelamento(parcelamento);
            parcelas.add(parcela);
        }

        return parcelas;
    }

    public LocalDate montaDataVencimento(Integer parcela, LocalDate dataVencimento) {
        if (parcela == 1) {
            return dataVencimento;
        } else {
            return dataVencimento.plusMonths(parcela);
        }
    }

    public ResponseEntity<String> marcarPago(Long id) {

        if (id == null) {
            throw new NegocioException("Favor informar a Parcela");
        }

        Parcelas parcela = parcelasRepository.findById(id).orElseThrow(() -> new NegocioException("Parcela não existente"));

        parcela.setDataPagamento(LocalDate.now());
        parcelasRepository.save(parcela);

        return ResponseEntity.ok("Pago realizado com sucesso!");
    }
}
