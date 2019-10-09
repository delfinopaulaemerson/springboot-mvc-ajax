package br.com.springboot.ajax.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.springboot.ajax.web.domain.Categoria;
import br.com.springboot.ajax.web.domain.Promocao;
import br.com.springboot.ajax.web.repository.CategoriaRepository;
import br.com.springboot.ajax.web.repository.PromocaoRepository;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {
	
	private static Logger LOG = LoggerFactory.getLogger(PromocaoController.class);
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private PromocaoRepository PromocaoRepository; 
	
	@GetMapping("/add")
	public String abrirCadastro() {
		
		return "promo-add";
	}
	
	@ModelAttribute("categorias")
	public List<Categoria> getcategorias(){
		
		return this.categoriaRepository.findAll();
	}
	
	@PostMapping("/save")
	public ResponseEntity<?>salvarPromocao(@Valid Promocao promocao, BindingResult result) {
		
		if(result.hasErrors()) {
			
			Map<String, String> erros = new HashMap<>();
			for(FieldError error: result.getFieldErrors()) {
				erros.put(error.getField(), error.getDefaultMessage());
			}
			
			return ResponseEntity.unprocessableEntity().body(erros);
		}
		
		promocao.setDtCadastro(LocalDateTime.now());
		this.PromocaoRepository.save(promocao);
		
		return ResponseEntity.ok().build(); 
		
	}

}
