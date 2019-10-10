package br.com.springboot.ajax.web.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.springboot.ajax.web.domain.Promocao;
import br.com.springboot.ajax.web.repository.PromocaoRepository;

@Service
public class PromocaoDataTablesService {

	private String[] cols =  {
			"id","titulo","site","linkPromocao","descricao","linkImagem","preco","likes","dtCadastro","categoria"
	};
	
	public Map<String, Object> execute(PromocaoRepository repository, HttpServletRequest request){
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));
		
		int current = this.currentPage(start,length);
		
		String column = this.columnName(request);
		
		Sort.Direction direction = this.orderBy(request);
		
		String search =  this.searchBy(request);
		
		Pageable pageable =  PageRequest.of(current, length,direction,column);
		
		Page<Promocao> page = this.queryBy(search,repository,pageable);
		
		Map<String, Object> json = new LinkedHashMap<>();
		json.put("draw", draw);
		json.put("recordsTotal", page.getTotalElements());
		json.put("recordsFilered", page.getTotalElements());
		json.put("data", page.getContent());
		
		return json;
	}
	

	private String searchBy(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return request.getParameter("search[value]").isEmpty() ? "" : request.getParameter("search[value]");
	}


	private Page<Promocao> queryBy(String search,PromocaoRepository repository, Pageable pageable) {
		
		if(search.isEmpty()) {
			return repository.findAll(pageable);
		}
		
		if(search.matches("^[0-9]+([.,][0-9]{2})?$")) {
			search = search.replace(",", ".");
			return repository.findByPreco(new BigDecimal(search), pageable);
		}
		
		return repository.findByTituloOrSiteOrCategoria(search, pageable);
	}


	private Direction orderBy(HttpServletRequest request) {
		String order = request.getParameter("order[0][dir]");
		Sort.Direction sort = Sort.Direction.ASC;
		
		if(order.equalsIgnoreCase("desc")) {
			sort = Sort.Direction.DESC;
		}
		return sort;
	}


	private String columnName(HttpServletRequest request) {
		int icol = Integer.parseInt(request.getParameter("order[0][column]"));
		
		return this.cols[icol];
	}

	private int currentPage(int start, int length) {
		
		return start / length;
	}
}
