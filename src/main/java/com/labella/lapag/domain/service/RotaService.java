package com.labella.lapag.domain.service;

import com.labella.lapag.domain.model.Rota;
import com.labella.lapag.domain.repository.RotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RotaService {

    @Autowired
    private RotaRepository rotaRepository;

    public Rota findByNome(String nome) {
        return rotaRepository.findByNome(nome);
    }
}
