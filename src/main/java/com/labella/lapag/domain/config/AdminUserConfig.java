package com.labella.lapag.domain.config;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.labella.lapag.domain.model.Rota;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.service.RotaService;
import com.labella.lapag.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private RotaService rotaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        var rotaAdmin = rotaService.findByNome(Rota.Values.ADMIN.name());

        var userAdmin = usuarioService.buscaPorNome("admin");
        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin já exite");
                },
                () -> {
                    var user = new Usuario();
                    user.setNome("admin");
                    user.setEmail("labellamakesoficial@gmail.com");
                    user.setSenha(bCryptPasswordEncoder.encode("labella1203"));
                    usuarioService.salvar(user);
                }
        );
    }
}
