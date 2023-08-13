package br.com.fiap.gestanca.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.gestanca.models.Moeda;
import br.com.fiap.gestanca.repositorys.MoedaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/moedas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "moedas", description = "Dados das moedas")
public class MoedasController {

    Logger log = LoggerFactory.getLogger(MoedasController.class);

    @Autowired // IoD - IoC
    MoedaRepository moedaRespository;



    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping("{id}")
    @Operation(
        summary = "Obter os detalhes de uma moeda",
        description = "Mostra os detalhes de uma moeda"
    )
    public EntityModel<Moeda> show(@PathVariable Long id){
        log.info("buscar moeda com id " + id);



        var moeda = moedaRespository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "moeda não encontrada")
        );

        return moeda.toModel();

    }

    @GetMapping("/ConverterMoeda/{id}")
    @Operation(
            summary = "Obter os detalhes de conversão",
            description = "Mostra os detalhes de conversão"
    )
    public ResponseEntity<Double> show(@PathVariable Long id, double qnt){

        var moeda = moedaRespository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "moeda não encontrada")
        );

        double valorDaMoeda  = (moeda.getValor());
        double retonro = valorDaMoeda * qnt;

        return ResponseEntity.ok(retonro);

    }


    @GetMapping("/retornarTodosRegistros")
    @Operation(
            summary = "Obter os detalhes de todas moedas",
            description = "Mostra os detalhes de todas as moedas registradas"
    )
    public ResponseEntity<List<Moeda>> show(){

        List<Moeda> moeda = moedaRespository.findAll();

        return ResponseEntity.ok(moeda);

    }

    @PostMapping
    @Operation(
        summary = "Cadastrar uma moeda para o usuário autenticado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Moeda cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados da Moeda são inválidos, a validação falhou")
    })
    public ResponseEntity<EntityModel<Moeda>> create(@RequestBody @Valid Moeda dadosmoeda, BindingResult result){
        log.info("cadastrar Moeda: " + dadosmoeda);
        moedaRespository.save(dadosmoeda);
        return ResponseEntity
                    .created(dadosmoeda.toModel().getRequiredLink("self").toUri())
                    .body(dadosmoeda.toModel());
    }   

    @DeleteMapping("{id}")
    public ResponseEntity<Moeda> destroy(@PathVariable Long id){
        log.info("apagar Moeda com id " + id);
        Moeda moeda = moedaRespository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao apagar. Moeda não encontrada")
        );

        moedaRespository.delete(moeda);
            
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Moeda> update(@PathVariable Long id, @RequestBody @Valid Moeda dadosmoeda, BindingResult result){
        log.info("apagar Moeda com id " + id);
        var moedaEncontrada = moedaRespository.findById(id);

        if (moedaEncontrada.isEmpty()) return ResponseEntity.notFound().build();

        var novaMoeda = moedaEncontrada.get();
        BeanUtils.copyProperties(dadosmoeda, novaMoeda, "id");

        moedaRespository.save(novaMoeda);
            
        return ResponseEntity.ok(novaMoeda);
    }
    
}
