$(function(){
	//acao botao de alto contraste
	$('a.toggle-contraste').click(function(){
		if(!$('body.layout').hasClass('contraste'))
		{
			$('body.layout').addClass('contraste');	
			layout_classes = $.cookie('layout_classes');
			if( layout_classes != 'undefined' )
				layout_classes = layout_classes + ' contraste';
			else
				layout_classes = 'contraste';
				$.cookie('layout_classes', layout_classes );
		}
		else
		{
			$('body.layout').removeClass('contraste');
			layout_classes = $.cookie('layout_classes');
			layout_classes = layout_classes.replace('contraste', '');			
			$.cookie('layout_classes', layout_classes );
		}
	});
	
	//Aumentar, dimunuir e resetar fonte
	//Font size
	var defaultsize = $("body").css('font-size');
	var size = defaultsize;
	$(".toggle-aumentar").click(function () {
		var size = $("body").css('font-size');

		size = size.replace('px', '');
		size = parseInt(size) + 3;
		
		if(size <= 20){
			$("body").animate({'font-size' : size + 'px'});
		}
	});

	//diminuindo a fonte
	$(".toggle-diminuir").click(function () {
		var size = $("body").css('font-size');

		size = size.replace('px', '');
		size = parseInt(size) - 3;
		
		if(size >= 8){
			$("body").animate({'font-size' : size + 'px'});
		}
	});	
	
	$(".toggle-resetar").click(function () {
		$("body").animate({'font-size' : defaultsize});
	});	
});