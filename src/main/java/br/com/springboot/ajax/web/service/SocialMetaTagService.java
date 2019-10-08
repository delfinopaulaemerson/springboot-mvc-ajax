package br.com.springboot.ajax.web.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.springboot.ajax.web.domain.SocialMetaTag;

@Service
public class SocialMetaTagService {

	private static Logger LOG = LoggerFactory.getLogger(SocialMetaTagService.class); 
	/**
	 * utilizando a api Jsoup Html 5 para buscar informacoes das paginas html
	 * @param url
	 * @return SocialMetaTag
	 */
	public SocialMetaTag getOpenGraphyByUrl(String url) {
		SocialMetaTag tag = new SocialMetaTag();
		
		try {
			Document doc = Jsoup.connect(url).get();
			tag.setTitulo(doc.head().select("meta[property=og:title]").attr("content"));
			tag.setSite(doc.head().select("meta[property=og:site_name]").attr("content"));
			tag.setImage(doc.head().select("meta[property=og:image]").attr("content"));
			tag.setUrl(doc.head().select("meta[property=og:url]").attr("content"));
		} catch (IOException e) {
			LOG.error("Erro ao recuperar as metaTags do OpenGraphy "+ e.getMessage());
		}
		
		return tag;
	}
	
	public SocialMetaTag getTwitterCardByUrl(String url) {
		SocialMetaTag tag = new SocialMetaTag();
		
		try {
			Document doc = Jsoup.connect(url).get();
			tag.setTitulo(doc.head().select("meta[name=twitter:title]").attr("content"));
			tag.setSite(doc.head().select("meta[name=twitter:site]").attr("content"));
			tag.setImage(doc.head().select("meta[name=twitter:image]").attr("content"));
			tag.setUrl(doc.head().select("meta[name=twitter:url]").attr("content"));
		} catch (IOException e) {
			LOG.error("Erro ao recuperar as metaTags do Twitte Card "+ e.getMessage());
		}
		
		return tag;
	}
	
	public SocialMetaTag getSocialMetaTagByUrl(String url) {
		SocialMetaTag twitter = this.getTwitterCardByUrl(url);
		if(!this.isEmpyt(twitter)) {
			return twitter;
		}
		
		SocialMetaTag openGraphy = this.getOpenGraphyByUrl(url);
		if(!this.isEmpyt(openGraphy)) {
			return openGraphy;
		}
		return null;
	}
	
	private boolean isEmpyt(SocialMetaTag tag) {
		
		if(tag.getImage().isEmpty()) return true;
		if(tag.getSite().isEmpty()) return true;
		if(tag.getTitulo().isEmpty()) return true;
		if(tag.getUrl().isEmpty()) return true;
		
		return false;
	}
}
