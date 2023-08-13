package br.com.fiap.gestanca.models;

import br.com.fiap.gestanca.controllers.MoedasController;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity

public class Registrar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @NotNull
    private String Nome;

    @NotBlank
    @NotNull
    private String Email;

    @NotBlank
    @NotNull
    private String Senha;

    public EntityModel<Registrar> toModel(){
        return EntityModel.of(
                this,
                linkTo(methodOn(MoedasController.class).show(id)).withSelfRel(),
                linkTo(methodOn(MoedasController.class).destroy(id)).withRel("delete")

        );
      }
    }
