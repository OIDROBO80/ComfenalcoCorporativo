$(document).on('ready', function() {
	var ancho = window.innerWidth
	//Menos el ancho del Sider Principal
	var espacioDispinible = window.innerWidth - 780; 
	var espacioReal = espacioDispinible /2; 
	//se divide sobre el ancho del subeEslaider para saber cuanto se pueden incluir
	var cantidadSlider = Math.ceil(espacioReal / 255);
	$(".regular2").slick({
		dots: false,
		infinite: true,
		slidesToShow: 1,
		slidesToScroll: 1,
		touchMove: false,
		prevArrow:'<div class="prev" onClick="changeImg(\'right\')"></div>',
		nextArrow:'<div class="next" onClick="changeImg(\'left\')"></div>',
		responsive: [
		    {
		      breakpoint: 970,
		      settings: {
		        slidesToShow: 1,
		        slidesToScroll: 1,
		        infinite: true,
		        arrows: false,
		        dots: true
		      }
		    }
	    ]
	});
	$('.regular2').on('swipe', function(event, slick, direction){
			changeImg(direction);
	});
	var str = "";
	for (var i = 0; i < cantidadSlider; i++) {
		var position = i*-233;
		switch(i) {
			case 0:
				str += generarSlider({direction:"left", style: "style='left: 14px'",
						img: [10, 1, 2, 3, 4, 5, 6, 7, 8, 9]});
				str += generarSlider({direction:"right", style: "style='right: 14px'",
						img: [2, 3, 4, 5, 6, 7, 8, 9, 10, 1]});
			break;
			case 1:
				str += generarSlider({direction:"left", style: "style='left: "+position+"px'",
						img: [9, 10, 1, 2, 3, 4, 5, 6, 7, 8]});
				str += generarSlider({direction:"right", style: "style='right: "+position+"px'",
						img: [3, 4, 5, 6, 7, 8, 9, 10, 1, 2]});
			break;
			case 2:
				str += generarSlider({direction:"left", style: "style='left: "+position+"px'",
						img: [8, 9, 10, 1, 2, 3, 4, 5, 6, 7]});
				str += generarSlider({direction:"right", style: "style='right: "+position+"px'",
						img: [4, 5, 6, 7, 8, 9, 10, 1, 2, 3]});
			break;
			case 3:
				str += generarSlider({direction:"left", style: "style='left: "+position+"px'",
						img: [7, 8, 9, 10, 1, 2, 3, 4, 5, 6]});
				str += generarSlider({direction:"right", style: "style='right: "+position+"px'",
						img: [5, 6, 7, 8, 9, 10, 1, 2, 3, 4]});
			break;
			case 4:
				str += generarSlider({direction:"left", style: "style='left: "+position+"px'",
						img: [6, 7, 8, 9, 10, 1, 2, 3, 4, 5]});
				str += generarSlider({direction:"right", style: "style='right: "+position+"px'",
						img: [6, 7, 8, 9, 10, 1, 2, 3, 4, 5]});
			break;
			case 5:
				str += generarSlider({direction:"left", style: "style='left: "+position+"px'",
						img: [5, 6, 7, 8, 9, 10, 1, 2, 3, 4]});
				str += generarSlider({direction:"right", style: "style='right: "+position+"px'",
						img: [7, 8, 9, 10, 1, 2, 3, 4, 5, 6]});
			break;
		}
	}
	$(".conainerSliderCenter").append(str);

	$(".regular1").slick({
		dots: false,
		infinite: true,
		slidesToShow: 1,
		arrows: false,
		slidesToScroll: 1,
		touchMove: false
	});

});

function generarSlider(obj){
	var str = "<div class='conainerSlider "+obj.direction+" hidden-sm hidden-xs' "+obj.style+">"+
            "<div class='slider regular1'>"+
                "<div><img src='img/galeria/img_"+obj.img[0]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[1]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[2]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[3]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[4]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[5]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[6]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[7]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[8]+"_ByN.jpg'></div>"+
                "<div><img src='img/galeria/img_"+obj.img[9]+"_ByN.jpg'></div>"+
            "</div>"+
            "<div class='filter'></div>"+
        "</div>";
    return str;
}

function changeImg(direction){
	if(direction === "left"){
		$(".regular1").slick('slickNext')
	}else{
		$(".regular1").slick('slickPrev')
	}
}