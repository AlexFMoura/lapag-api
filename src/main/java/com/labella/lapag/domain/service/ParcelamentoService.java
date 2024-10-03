package com.labella.lapag.domain.service;

import com.labella.lapag.api.mapper.ParcelamentoMapper;
import com.labella.lapag.api.model.CriarParcelamentoDTO;
import com.labella.lapag.api.model.ParcelamentoDTO;
import com.labella.lapag.api.model.ParcelamentoPageDTO;
import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Cliente;
import com.labella.lapag.domain.model.Parcelamento;
import com.labella.lapag.domain.model.Parcelas;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.repository.ParcelamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ParcelamentoService {

    private final ParcelamentoRepository parcelamentoRepository;
    private final ClienteService clienteService;
    private final ParcelasService parcelasService;
    private final UsuarioService usuarioService;
    private final PagamentoService pagamentoService;
    private final ParcelamentoMapper parcelamentoMapper;

    @Transactional
    public Parcelamento salvar(CriarParcelamentoDTO parcelamentoDTO) {

        Parcelamento parcelamentoBD = parcelamentoRepository.findByVendaId(parcelamentoDTO.getCodigoVenda()).orElse(null);
        if (parcelamentoBD != null) {
            throw new NegocioException("Já existe um parcelamento com esse código da venda cadastrado!");
        }

        Parcelamento parcelamento = new Parcelamento();
        Cliente cliente = clienteService.buscar(parcelamentoDTO.getClienteId());
        Usuario usuario = usuarioService.buscar(parcelamentoDTO.getUsuarioId());
        parcelamento.setCliente(cliente);
        parcelamento.setStatus("Ativo");
        parcelamento.setUsuario(usuario);

        List<Parcelas> parcelas = parcelasService.criarParcela(parcelamentoDTO, parcelamento);
        parcelamento.setParcelas(parcelas);

        parcelamento.setVendaId(parcelamentoDTO.getCodigoVenda());
        parcelamento.setQtdParcelas(parcelamentoDTO.getQtdParcela());
        parcelamento.setValorTotal(parcelamentoDTO.getValorVenda());
        parcelamento.setPrimeiroVencimento(parcelamentoDTO.getPrimeiroVencimento());

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

    public Page<ParcelamentoPageDTO> getParcelamentoPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Parcelamento> parcelamentoPage = parcelamentoRepository.findAll(pageable);

        // Converter para DTO
        return parcelamentoPage.map(this::toParcelamentoDTO);
    }

    public ParcelamentoPageDTO toParcelamentoDTO(Parcelamento parcelamento){
        ParcelamentoPageDTO dto = new ParcelamentoPageDTO();
        dto.setId(parcelamento.getId());
        dto.setNomeCliente(parcelamento.getCliente().getNome());
        dto.setCodigoVenda(parcelamento.getVendaId());
        dto.setStatus(parcelamento.getStatus());
        dto.setValorVenda(parcelamento.getValorTotal()); // Corrigido para 'valorTotal'
        dto.setQtdParcela(parcelamento.getQtdParcelas()); // Corrigido para 'qtdParcelas'
        dto.setPrimeiroVencimento(parcelamento.getPrimeiroVencimento());

//        dto.setParcelas(parcelamento
//                .getParcelas()
//                .stream()
//                .sorted(Comparator.comparingLong(Parcelas::getId))
//                .collect(Collectors.toList()));

        dto.setParcelas(Optional.ofNullable(parcelamento.getParcelas())
                .map(parcelas -> parcelas
                        .stream()
                        .sorted(Comparator.comparingLong(Parcelas::getId))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
        return dto;
    }

    public void verificaSeQuitaContrato(Parcelas parcela) {
        pagamentoService.verificaSeQuitaContrato(parcela);
    }
}
