package com.labella.lapag.domain.service;

import com.labella.lapag.domain.model.Rota;
import com.labella.lapag.domain.repository.RotaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RotaService {

    @Autowired
    private RotaRepository rotaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Rota findByNome(String nome) {
        return rotaRepository.findByNome(nome);
    }

    @Transactional
    public Rota salvar(Rota rota) {
        return rotaRepository.saveAndFlush(rota);
    }
}
