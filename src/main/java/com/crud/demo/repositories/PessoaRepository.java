package com.crud.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.demo.models.Pessoa;


@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

Optional<Pessoa> findByCpf(String cpf);
}
