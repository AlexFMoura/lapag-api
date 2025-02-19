package com.labella.lapag.api.controller;

import com.labella.lapag.api.model.ParcelaVencidaDTO;
import com.labella.lapag.api.model.ParcelasDTO;
import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.service.ParcelasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parcelas")
public class ParcelaController {

    private final ParcelasService parcelasService;

    public ParcelaController(ParcelasService parcelasService) {
        this.parcelasService = parcelasService;
    }

    @PutMapping("/pagamento/parcela/{id}")
    public ResponseEntity<String> pagamento(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String formaPagamento = request.get("formaPagamento");
        return parcelasService.marcarPago(id,formaPagamento);
    }

    @GetMapping("/vencidas/soma")
    public BigDecimal somarParcelasVencidas() {
        return parcelasService.somarParcelasVencidas();
    }

    @GetMapping("/vencidas")
    public ResponseEntity<List<ParcelaVencidaDTO>> buscarParcelasVencidas() {
        List<ParcelaVencidaDTO> parcelasVencidas = parcelasService.buscarParcelasVencidas();
        return ResponseEntity.ok(parcelasVencidas);
    }

//    @GetMapping("/vencendo-em-30-dias")
//    public BigDecimal buscarParcelasVencendoEm30Dias() {
//        return parcelasService.buscarParcelasVencendoEm30Dias();
//    }

    @GetMapping("/vencendo-em-30-dias")
    public ResponseEntity<List<ParcelaVencidaDTO>> buscarParcelasVencendoEm30Dias() {
        List<ParcelaVencidaDTO> parcelasVencidas = parcelasService.buscarParcelasVencendoEm30Dias();
        return ResponseEntity.ok(parcelasVencidas);
    }
}
