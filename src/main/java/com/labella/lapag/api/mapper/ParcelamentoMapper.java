package com.labella.lapag.api.mapper;

import com.labella.lapag.api.model.ParcelamentoDTO;
import com.labella.lapag.domain.model.Parcelamento;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ParcelamentoMapper {

    private final ModelMapper modelMapper;

    public ParcelamentoDTO toModel(Parcelamento parcelamento) {
        return modelMapper.map(parcelamento, ParcelamentoDTO.class);
    }

    public List<ParcelamentoDTO> toCollectionModel(List<Parcelamento> parcelamentos) {
        return parcelamentos.stream()
                .map(this::toModel)
                .toList();
    }

}
