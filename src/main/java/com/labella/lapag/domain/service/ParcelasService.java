package com.labella.lapag.domain.service;

import com.labella.lapag.api.model.CriarParcelamentoDTO;
import com.labella.lapag.api.model.ParcelaVencidaDTO;
import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.model.Taxa;
import com.labella.lapag.domain.repository.ParcelasRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.labella.lapag.domain.Util.DataUtil.ajustarParaProximoDiaUtil;

@AllArgsConstructor
@Service
public class ParcelasService {

    private final ParcelasRepository parcelasRepository;
    private final PagamentoService pagamentoService;
    private final TaxaService taxaService;

    public List<Parcelas> criarParcela(CriarParcelamentoDTO parcelamentoDTO, Parcelamento parcelamento) {
        Taxa taxa = taxaService.findDataInativoIsNull().orElseThrow( () -> new NegocioException("Taxa não encontrada!"));
        List<Parcelas> parcelas = new ArrayList<Parcelas>();
        BigDecimal valorParcela = parcelamentoDTO.getValorVenda().divide(
                new BigDecimal(parcelamentoDTO.getQtdParcela().toString()), 2, RoundingMode.HALF_EVEN);
        BigDecimal diferenca = parcelamentoDTO.getValorVenda().subtract(valorParcela.multiply(BigDecimal.valueOf(parcelamentoDTO.getQtdParcela())));
        for (int i = 1; i <= parcelamentoDTO.getQtdParcela(); i++) {
            Parcelas parcela = new Parcelas();
            parcela.setValorParcela(valorParcela.add(diferenca));
            parcela.setParcela(i);
            parcela.setDataVencimento(ajustarParaProximoDiaUtil(montaDataVencimento(i, parcelamentoDTO.getPrimeiroVencimento())));
            parcela.setMulta(taxa.getMulta());
            parcela.setJuros(taxa.getJuros());
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

    public ResponseEntity<String> marcarPago(Long id, String formaPagto) {

        if (id == null) {
            throw new NegocioException("Favor informar a Parcela");
        }

        Parcelas parcela = parcelasRepository.findById(id).orElseThrow(() -> new NegocioException("Parcela não existente"));

        boolean naoPodeReceber = parcelasRepository.findParcelaVencidaNaoPaga(parcela.getDataVencimento()).isPresent();

        if (naoPodeReceber) {
            throw new NegocioException("Existe uma parcela menor em aberta, favor receber ela primeiro!");
        }

        parcela.setDataPagamento(LocalDate.now());
        parcela.setFormaPagto(formaPagto);
        parcelasRepository.save(parcela);

        pagamentoService.verificaSeQuitaContrato(parcela);

        return ResponseEntity.ok("Pagamento realizado com sucesso!");
    }

    public List<Parcelas> buscaParcelas(Long parcelamentoId) {
        return parcelasRepository.findByParcelamento_id(parcelamentoId);
    }

    public BigDecimal somarParcelasVencidas() {
        LocalDate currentDate = LocalDate.now();
        List<Parcelas> vencidas = parcelasRepository.findVencidas(currentDate);

        return vencidas.stream()
                .map(Parcelas::getValorParcela)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal buscarSomatorioParcelasVencendoEm30Dias() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(30);
        List<Parcelas> vencendo30Dias = parcelasRepository.findVencemEm30Dias(currentDate, futureDate);
        return vencendo30Dias.stream()
                .map(Parcelas::getValorParcela)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<ParcelaVencidaDTO> buscarParcelasVencendoEm30Dias() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(30);
        return parcelasRepository.findParcelasVencendoEm30Dias(currentDate,futureDate);

    }

    public List<ParcelaVencidaDTO> buscarParcelasVencidas() {
        return parcelasRepository.findParcelasVencidas();
    }
}
