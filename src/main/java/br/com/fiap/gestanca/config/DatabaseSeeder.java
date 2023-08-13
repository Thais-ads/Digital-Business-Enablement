package br.com.fiap.gestanca.config;

import br.com.fiap.gestanca.models.Moeda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.gestanca.models.Usuario;
import br.com.fiap.gestanca.repositorys.MoedaRepository;
import br.com.fiap.gestanca.repositorys.UsuarioRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    MoedaRepository MoedaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

@Override
    public void run(String... args) throws Exception {

    }

}
