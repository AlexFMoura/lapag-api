package com.labella.lapag.api.mapper;

import com.labella.lapag.api.model.ParcelasDTO;
import com.labella.lapag.domain.model.Parcelas;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ParcelasMapper {

    private final ModelMapper modelMapper;

    public ParcelasDTO toModel(Parcelas parcelas) {
        return modelMapper.map(parcelas, ParcelasDTO.class);
    }
}
