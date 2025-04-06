package com.labella.lapag.domain.Util;

import com.labella.lapag.domain.model.Parcelas;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Calculos {

    public static void calcularMultaEJuros(Parcelas parcela) {
        if (parcela.getDataPagamento() == null &&
                LocalDate.now().isAfter(parcela.getDataVencimento())) {

            long diasAtraso = ChronoUnit.DAYS.between(parcela.getDataVencimento(), LocalDate.now());

            BigDecimal valorParcela = parcela.getValorParcela();
            BigDecimal multa = BigDecimal.valueOf(parcela.getMulta() != null ? parcela.getMulta() : 0.0);
            BigDecimal juros = BigDecimal.valueOf(parcela.getJuros() != null ? parcela.getJuros() : 0.0);

            BigDecimal valorMulta = valorParcela.multiply(multa).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal valorJuros = valorParcela.multiply(juros)
                    .multiply(BigDecimal.valueOf(diasAtraso))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            parcela.setValorMulta(valorMulta);
            parcela.setValorJuros(valorJuros);
            parcela.setTotalPagar(valorParcela.add(valorMulta).add(valorJuros));
        } else {
            // Parcela ainda dentro do prazo ou já paga
            parcela.setValorMulta(BigDecimal.ZERO);
            parcela.setValorJuros(BigDecimal.ZERO);
            parcela.setTotalPagar(parcela.getValorParcela());
        }
    }
}
