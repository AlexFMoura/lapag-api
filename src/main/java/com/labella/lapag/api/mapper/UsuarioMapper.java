package com.labella.lapag.api.mapper;

import com.labella.lapag.api.model.UsuarioDTO;
import com.labella.lapag.domain.model.Usuario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsuarioMapper {
    private final ModelMapper modelMapper;

    public UsuarioDTO toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
