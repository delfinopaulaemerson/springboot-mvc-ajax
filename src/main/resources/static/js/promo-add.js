$("#form-add-promo").submit(function(evt){
	evt.preventDefault();
	var promo = {};
	
	promo.linkPromocao = $("#linkPromocao").val();
	promo.descricao = $("#descricao").val();
	promo.preco = $("#preco").val();
	promo.titulo = $("#titulo").val();
	promo.categoria = $("#categoria").val();
	promo.linkImagem = $("#linkImagem").attr("src");
	promo.site = $("#site").text();
	
	console.log("> promo > ", promo);
	
	$.ajax({
		method : "POST",
		url : "/promocao/save",
		data: promo,
		beforeSend: function(){
			//removendo as mensagens
			$("span").closest('.error-span').remove();
			//removendo as bordas vermelhas
			$("#categoria").removeClass("is-invalid");
			$("#preco").removeClass("is-invalid");
			$("#linkPromocao").removeClass("is-invalid");
			$("#titulo").removeClass("is-invalid");
			
			//habilita o load
			$("#form-add-promo").hide();
			$("#loader-form").addClass("loader").show();
		},
		success: function(){
			$("#form-add-promo").each(function(){
				this.reset();
			});
			$("#linkImagem").attr("src", "/images/promo-dark.png");
			$("#site").text("");
			$("#alert").removeClass("alert alert-danger");
			$("#alert").addClass("alert alert-success").text("OK, promoção cadastrada com sucesso!");
		},
		//adicionando status code 422 para tratar o bean validation
		statusCode: {
			422: function(xhr) {
				console.log('status error:', xhr.status);
				var errors = $.parseJSON(xhr.responseText);
				$.each(errors, function(key, val){
					$("#" + key).addClass("is-invalid");
					$("#error-" + key)
						.addClass("invalid-feedback")
						.append("<span class='error-span'>" + val + "</span>")
				});
			}
		},
		error: function(xhr){
			console.log("> Error >",xhr.responseText);
			$("#alert").addClass("alert alert-danger").text("Não foi Possivel Salvar está Promoção!");
		},
		
		complete: function(){
			$("#loader-form").fadeOut(800, function(){
				$("#form-add-promo").fadeIn(250);
				$("#loader-form").removeClass("loader");
			});
		}
		
	})
	
});
	

$("#linkPromocao")
		.on(
				'change',
				function() {
					var url = $(this).val();

					if (url.length > 7) {
						$
								.ajax({
									method : "POST",
									url : "/meta/info?url=" + url,
									cache : false,
									//inicializando os componentes
									beforeSend: function(){
										//removendo as mensagens de erro e de sucesso
										$("#alert").removeClass("alert alert-success").text('')
										$("#alert").removeClass("alert alert-danger ").text('')
										$("#titulo").val("");
										$("#site").text("");
										$("#linkImagem").attr("src", "");
										//ativando o loader da imagem
										$("#loader-img").addClass("loader");
									},
									//adicionando valores aos componentes
									success : function(data) {
										console.log(data);
										$("#titulo").val(data.titulo);
										$("#site").text(
												data.site.replace("@", ""));
										$("#linkImagem")
												.attr("src", data.image);
									},
									//adicionando o componente a mensagem de erro 400
									statusCode : {
										404 : function() {
											$("#alert")
													.addClass("alert alert-danger")
													.text("Nenuma informação pode ser recuperada desta URL!");
											$("#linkImagem").attr("src", "/images/promo-dark.png");
										}
									},

									//adicionando o componente a mensagem de erro 500
									error : function() {
										$("#alert")
												.addClass("alert alert-danger")
												.text("ops... algo deu errado, tente mais tarde!");
										$("#linkImagem").attr("src", "/images/promo-dark.png");
									},
									//removendo o loader
									complete:function(){
										$("#loader-img").removeClass("loader");
									}
								});
					}
				});