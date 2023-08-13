package br.com.fiap.gestanca.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.gestanca.models.Moeda;

import java.util.List;

public interface MoedaRepository extends JpaRepository<Moeda, Long> {

    @Override
    List<Moeda> findAll();

    
}
