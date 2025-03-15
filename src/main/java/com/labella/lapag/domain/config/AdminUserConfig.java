package com.labella.lapag.domain.config;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.labella.lapag.domain.model.Rota;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.service.RotaService;
import com.labella.lapag.domain.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private RotaService rotaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${admin.user.email}")
    private String adminEmail;

    @Value("${admin.user.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        Rota rotaAdmin = rotaService.findByNome(Rota.Values.ADMIN.name());

        if (rotaAdmin == null) {
            throw new IllegalStateException("Rota ADMIN não encontrada no banco de dados");
        }

        usuarioService.buscaPorNome("admin").ifPresentOrElse(
                user -> {
                    System.out.println("admin já exite");
                },
                () -> {
                    Usuario user = new Usuario();
                    user.setNome("admin");
                    user.setEmail(adminEmail);
                    user.setSenha(bCryptPasswordEncoder.encode(adminPassword));
                    user.setRotas(Set.of(rotaAdmin));
                    usuarioService.salvarAdmin(user);
                }
        );

    }
}
