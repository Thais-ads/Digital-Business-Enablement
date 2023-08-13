package br.com.fiap.gestanca.repositorys;

import br.com.fiap.gestanca.models.Registrar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrarRepository extends JpaRepository<Registrar, Long> {

    @Override
    List<Registrar> findAll();



}
