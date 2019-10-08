package br.com.springboot.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.springboot.ajax.web.domain.SocialMetaTag;
import br.com.springboot.ajax.web.service.SocialMetaTagService;

@SpringBootApplication
public class SpringbootMvcAjxApplication  implements CommandLineRunner{

	@Autowired
	private SocialMetaTagService service;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMvcAjxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		SocialMetaTag og = this.service.getSocialMetaTagByUrl("https://www.pichau.com.br/hardware/placa-de-video");
		System.out.println(og);
		
	}

}
