package com.labella.lapag.domain.service;

import com.labella.lapag.domain.model.Taxa;
import com.labella.lapag.domain.repository.TaxaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TaxaService {

    @Autowired
    private TaxaRepository taxaRepository;


    public Optional<Taxa> findDataInativoIsNull() {
        return taxaRepository.findByDataInativoIsNull();
    }
}
