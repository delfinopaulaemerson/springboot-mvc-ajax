package br.com.springboot.ajax.web.controller;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import br.com.springboot.ajax.web.domain.Categoria;

public class PromocaoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long id;
	
	@NotBlank(message = "Um titulo é requerido")
	private String titulo;
	
	private String descricao;
	
	@NotNull(message = "o preco e requerido")
	@NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
	private BigDecimal preco;
	
	@NotNull(message = "uma categoria é requerida.")
	private Categoria categoria;
	
	@NotBlank(message = "uma imagem é requerida")
	private String linkImagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getLinkImagem() {
		return linkImagem;
	}

	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
	}
	
}
