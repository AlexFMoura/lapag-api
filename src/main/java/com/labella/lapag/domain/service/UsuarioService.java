package com.labella.lapag.domain.service;

import com.labella.lapag.api.model.ClienteDTO;
import com.labella.lapag.api.model.UsuarioDTO;
import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Cliente;
import com.labella.lapag.domain.model.Rota;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RotaService rotaService;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscar(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new NegocioException("Usuário não encontrado"));
    }

    public Usuario buscarNome(String nome) {
        return usuarioRepository.findByNome(nome).orElseThrow(() -> new NegocioException("Usuário não encontrado"));
    }

    private String gerarSenhaAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        Random random = new Random();
        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < 8; i++) { // Gera uma senha de 8 caracteres
            senha.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return senha.toString();

    }

    @Transactional(rollbackFor = Exception.class)
    public Usuario salvar(UsuarioDTO usuarioDTO) {
        boolean emailEmUso = usuarioRepository.findByEmail(usuarioDTO.getEmail())
                .filter(c -> !c.equals(usuarioDTO))
                .isPresent();

        if (emailEmUso) {
            throw new NegocioException("Já existe um usuário cadastrado com este e-mail");
        }

        String senhaGerada = gerarSenhaAleatoria();

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());

        usuario.setSenha(bCryptPasswordEncoder.encode(senhaGerada));

        Rota rota;
        if (usuarioDTO.getRotaNome().equals("ADMIN")) {
            rota = rotaService.findByNome(Rota.Values.ADMIN.name());
        } else {
            rota = rotaService.findByNome(Rota.Values.BASIC.name());
        }

        usuario.setRotas(Set.of(rota));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        try {
            emailService.enviarEmail(usuario.getEmail(),
                    "Sua nova senha",
                    "Olá " + usuario.getNome() + ", sua senha de acesso é: " + senhaGerada
            );
        } catch (Exception e) {
            // Log do erro de envio de email
            logger.error("Erro ao enviar e-mail para o usuário " + usuario.getEmail(), e);
            // Lança uma exceção para garantir o rollback
            throw new RuntimeException("Falha no envio de e-mail, transação revertida.");
        }

        return usuarioSalvo;
    }

    @Transactional
    public void salvarAdmin(Usuario usuario) {
        entityManager.merge(usuario);
    }

    public Optional<Usuario> buscaPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscaPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public Optional<Usuario> buscaPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public void alterarSenha(Integer usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a senha atual fornecida é válida
        if (!bCryptPasswordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha atual inválida");
        }

        // Criptografa a nova senha
        String senhaCriptografada = bCryptPasswordEncoder.encode(novaSenha);

        // Atualiza a senha do usuário no banco de dados
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
    }

    public Page<UsuarioDTO> getUsuarioPage(Integer page, Integer size, String sort, String nome) {
        String[] sortParams = sort.split(",");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortParams[0]).ascending());

        Page<Usuario> usuarioPage;
        if (nome != null && !nome.isEmpty()) {
            usuarioPage = usuarioRepository.findByNomeContainingIgnoreCase(nome.toLowerCase(), pageable);
        } else {
            usuarioPage = usuarioRepository.findAll(pageable);
        }

        return usuarioPage.map(this::convertToDTO);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setRotaNome(usuario.getRotas().getClass().getName());
        usuarioDTO.setData_inativo(usuario.getData_inativo());
    return usuarioDTO;
    }
}
