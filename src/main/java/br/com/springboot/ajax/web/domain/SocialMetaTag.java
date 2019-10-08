package br.com.springboot.ajax.web.domain;

import java.io.Serializable;

public class SocialMetaTag implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String site;
	
	private String titulo;
	
	private String url;
	
	private String image;
	
	

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "SocialMetaTag [site=" + site + ", titulo=" + titulo + ", url=" + url + ", image=" + image + "]";
	}
	

}
