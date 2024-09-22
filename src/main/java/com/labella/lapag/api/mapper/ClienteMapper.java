package com.labella.lapag.api.mapper;

import com.labella.lapag.api.model.ClienteDTO;
import com.labella.lapag.domain.model.Cliente;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ClienteMapper {

    private final ModelMapper modelMapper;

    public ClienteDTO toModel(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public List<ClienteDTO> toCollectionModel(List<Cliente> clientes) {
        return clientes.stream().map(this::toModel).toList();
    }
}
