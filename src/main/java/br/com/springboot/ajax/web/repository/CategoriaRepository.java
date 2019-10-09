package br.com.springboot.ajax.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.springboot.ajax.web.domain.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
