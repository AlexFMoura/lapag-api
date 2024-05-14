package com.labella.lapag.domain.service;

import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.model.Parcelas;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ParcelasService {

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
}
