package br.com.springboot.ajax.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.springboot.ajax.web.domain.Categoria;
import br.com.springboot.ajax.web.domain.Promocao;
import br.com.springboot.ajax.web.repository.CategoriaRepository;
import br.com.springboot.ajax.web.repository.PromocaoRepository;
import br.com.springboot.ajax.web.service.PromocaoDataTablesService;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {


	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private PromocaoRepository promocaoRepository;

	@GetMapping("/add")
	public String abrirCadastro() {

		return "promo-add";
	}

	@ModelAttribute("categorias")
	public List<Categoria> getcategorias() {

		return this.categoriaRepository.findAll();
	}
	
	@GetMapping("/tabela")
	public String showTabela() {
		
		return"promo-datatables";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> datatables(HttpServletRequest request){
		Map<String, Object> data = new PromocaoDataTablesService().execute(this.promocaoRepository, request);
		return ResponseEntity.ok(data);
	}

	@PostMapping("/save")
	public ResponseEntity<?> salvarPromocao(@Valid Promocao promocao, BindingResult result) {

		if (result.hasErrors()) {

			Map<String, String> erros = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				erros.put(error.getField(), error.getDefaultMessage());
			}

			return ResponseEntity.unprocessableEntity().body(erros);
		}

		promocao.setDtCadastro(LocalDateTime.now());
		this.promocaoRepository.save(promocao);

		return ResponseEntity.ok().build();

	}

	@GetMapping("/list")
	public String listaPromocoes(ModelMap map) {
		Sort sort = new Sort(Sort.Direction.DESC, "dtCadastro");
		PageRequest pageRequest = PageRequest.of(0, 8, sort);
		map.addAttribute("promocoes", this.promocaoRepository.findAll(pageRequest));

		return "promo-list";
	}
	
	@GetMapping("/list/ajax")
	public String listaCards(@RequestParam(name = "page", defaultValue = "1") int page,
							 @RequestParam(name = "site", defaultValue = "") String site, ModelMap model) {
		
		Sort sort = new Sort(Sort.Direction.DESC, "dtCadastro");
		PageRequest pageRequest = PageRequest.of(page, 8, sort);
		if(site.isEmpty()) {
			model.addAttribute("promocoes", this.promocaoRepository.findAll(pageRequest));
		}else {
			model.addAttribute("promocoes", this.promocaoRepository.findBySite(site, pageRequest));
		}

		return "promo-card";
	}
	
	@PostMapping("/like/{id}")
	public ResponseEntity<?> adicionarLikes(@PathVariable("id") Long id) {
		int likes = 0;
		this.promocaoRepository.updateSomarLikes(id);
		likes = this.promocaoRepository.findLikesById(id);
				
		return ResponseEntity.ok(likes);
	}
	
	
	@GetMapping("/site")
	public ResponseEntity<?> findSiteByTermo(@RequestParam("termo") String termo){
		List<String> sites = this.promocaoRepository.findSiteByTermo(termo);
		
		return ResponseEntity.ok(sites);
	}
	
	@GetMapping("/site/list")
	public String findBySite(@RequestParam("site") String site, ModelMap model){
		Sort sort = new Sort(Sort.Direction.DESC, "dtCadastro");
		PageRequest pageRequest = PageRequest.of(0, 8, sort);
		model.addAttribute("promocoes", this.promocaoRepository.findBySite(site,pageRequest));
		
		return "promo-card";
	}
	
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> excluirPromocao(@PathVariable("id") Long id) {
		this.promocaoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/edit/{id}")
	public ResponseEntity<?> preEditarPromocao(@PathVariable("id") Long id) {
		Promocao p = this.promocaoRepository.findById(id).get();
		return ResponseEntity.ok(p);
	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> EditarPromocao(@Valid PromocaoDTO dto, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> erros = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				erros.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.unprocessableEntity().body(erros);
		}
		
		Promocao p = this.promocaoRepository.findById(dto.getId()).get();
		p.setCategoria(dto.getCategoria());
		p.setPreco(dto.getPreco());
		p.setTitulo(dto.getTitulo());
		p.setLinkImagem(dto.getLinkImagem());
		
		if(dto.getDescricao() != null)
			p.setDescricao(dto.getDescricao());
		
		this.promocaoRepository.save(p);
		
		return ResponseEntity.ok().build();
	}
}
