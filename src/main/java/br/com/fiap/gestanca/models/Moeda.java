package br.com.fiap.gestanca.models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;

import br.com.fiap.gestanca.controllers.MoedasController;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moeda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank @NotNull
    private String NomeMoeda;

    @NotNull
    private Double Valor;
    


    public EntityModel<Moeda> toModel(){
        return EntityModel.of(
            this,
            linkTo(methodOn(MoedasController.class).show(id)).withSelfRel(),
             linkTo(methodOn(MoedasController.class).destroy(id)).withRel("delete")

        );
    }

}
