package com.crud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.demo.models.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {


}
