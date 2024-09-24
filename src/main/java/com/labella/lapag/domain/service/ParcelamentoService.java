package com.labella.lapag.domain.service;

import com.labella.lapag.api.mapper.ParcelamentoMapper;
import com.labella.lapag.api.model.CriarParcelamentoDTO;
import com.labella.lapag.api.model.ParcelamentoDTO;
import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Cliente;
import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.model.Usuario;
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
    private final UsuarioService usuarioService;
    private final ParcelamentoMapper parcelamentoMapper;

    @Transactional
    public Parcelamento salvar(CriarParcelamentoDTO parcelamentoDTO) {
//        if (parcelamento.getId() != null) {
//            throw new NegocioException("Parcelamento já existente");
//        }

        List<Parcelas> parcelas = parcelasService.criarParcela(parcelamentoDTO);

        Cliente cliente = clienteService.buscar(parcelamentoDTO.getClienteId());

        Usuario usuario = usuarioService.buscar(parcelamentoDTO.getUsuarioId());

        Parcelamento parcelamento = new Parcelamento();
        parcelamento.setCliente(cliente);
        parcelamento.setParcelas(parcelas);
        parcelamento.setStatus("Ativo");
        parcelamento.setUsuario(usuario);
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
//            List<Parcelas> parcelas = parcelamento.get().getParcelas();
//            for (Parcelas parcela : parcelas) {
//                if (parcela.getDataPagamento() != null) {
//                    existe = true;
//                    break;
//                }
//            }
//        }

            existe = parcelamento.get().getParcelas().stream().anyMatch(p -> p.getDataPagamento() != null);
        }

        if (existe) {
            throw new NegocioException("Existe parcela paga para esse parcelamento.");
        } else {
            parcelamentoRepository.deleteById(id);
        }
    }

    public List<ParcelamentoDTO> listar() {
        List<Parcelamento> parcelamentos = parcelamentoRepository.findAll();

        if (parcelamentos.isEmpty()) {
            throw new NegocioException("Não existe parcelamento.");
        }

        return parcelamentoMapper.toCollectionModel(parcelamentos);
    }
}
