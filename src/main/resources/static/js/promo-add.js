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
										$("#alert").removeClass("alert alert-danger").text('')
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