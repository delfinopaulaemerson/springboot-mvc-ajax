package br.com.springboot.ajax.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.springboot.ajax.web.domain.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long>{

}
