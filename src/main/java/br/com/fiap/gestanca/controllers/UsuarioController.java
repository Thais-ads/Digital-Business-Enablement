package br.com.fiap.gestanca.controllers;

import br.com.fiap.gestanca.models.Registrar;
import br.com.fiap.gestanca.models.Usuario;
import br.com.fiap.gestanca.repositorys.RegistrarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.gestanca.models.Credencial;
import br.com.fiap.gestanca.repositorys.UsuarioRepository;
import br.com.fiap.gestanca.service.TokenService;
import jakarta.validation.Valid;

@RestController
public class UsuarioController {
    Logger log = LoggerFactory.getLogger(UsuarioController.class);


    @Autowired
    RegistrarRepository registrarRepository;
    @Autowired
    UsuarioRepository repository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

        @PostMapping("/api/registrar")
        public ResponseEntity<Registrar> registrar(@RequestBody @Valid Registrar registrar){
            repository.save(Usuario
                    .builder()
                    .nome(registrar.getNome())
                    .email(registrar.getEmail())
                    .senha(encoder.encode(registrar.getSenha()))
                    .build());


            return ResponseEntity.ok(registrar);
        }

    @PostMapping("/api/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial){ 
        manager.authenticate(credencial.toAuthentication());
        var token = tokenService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

}
