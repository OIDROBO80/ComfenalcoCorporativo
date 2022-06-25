$(document).ready(function () {
	console.log(document.querySelector("body").scrollLeft);
	document.querySelector("body").scrollLeft = 0;
	console.log(document.querySelector("body").scrollLeft);
	events();
	$(window).resize(size);
});

var validation = {
	capcha: false,
	email: false,
	form: false
};
var dataService = {
	url: ""
}

function size() {
	document.querySelector("body").scrollLeft = 0;
}

function events() {
	$(".validarDatos").on("click", confirmData);

	$('.cerrar').on("click", function () {
		$(this).parents(".lightbox").removeClass("lightboxOpened");
	});

	$('#btnMenuHamburguer').on('click', function () {
		if ($('#menuHamburguer').hasClass('showMenu')) {
			$(".mainMenu").removeAttr("style");
		} else {
			$(".mainMenu").width(window.innerWidth + "px")
		}
		$('#menuHamburguer').toggleClass('showMenu');
		$('#opacityMenu').toggleClass('opacityMenu');
		$(this).toggleClass('active');
	});

	$(".sliderHeader").slick({
		infinite: true,
		slidesToShow: 1,
		slidesToScroll: 1,
		dots: true,
		responsive: [{
			breakpoint: 440,
			settings: { arrows: false }
		}]
	});

	$(".sliderPreguntas, .sliderTabla").slick({
		infinite: true,
		slidesToShow: 1,
		slidesToScroll: 1,
		dots: false
	});

	var ancho = window.innerWidth
	//Menos el ancho del Sider Principal
	var espacioDispinible = window.innerWidth - 780;
	var espacioReal = espacioDispinible / 2;

	$(".galeria").slick({
		dots: false,
		infinite: true,
		slidesToShow: 1,
		slidesToScroll: 1,
		touchMove: false,
		prevArrow: '<div class="prev" onClick="changeImg(\'right\')"></div>',
		nextArrow: '<div class="next" onClick="changeImg(\'left\')"></div>',
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

	$('.galeria').on('swipe', function (event, slick, direction) {
		changeImg(direction);
	});
	espacioReal = espacioReal < 255 ? 255 : espacioReal;
	var str = "";
	str += generarSlider({
		direction: "left", style: "style='left:-" + espacioReal + "px; width:" + espacioReal + "px'",
		img: [10, 1, 2, 3, 4, 5, 6, 7, 8, 9]
	});
	str += generarSlider({
		direction: "right", style: "style='right:-" + espacioReal + "px; width:" + espacioReal + "px'",
		img: [2, 3, 4, 5, 6, 7, 8, 9, 10, 1]
	});

	$(".conainerSliderCenter").append(str);

	$(".regular1").slick({
		dots: false,
		infinite: true,
		slidesToShow: 1,
		arrows: false,
		slidesToScroll: 1,
		touchMove: false
	});

	var myInput = document.getElementById('ConfirmarEmail');
	if (myInput) {
		myInput.onpaste = function (e) {
			e.preventDefault();
		}
	}
	myInput = document.getElementById('numeroDocumento');
	if (myInput) {
		myInput.oninput = function () { if (this.value.length > 12) { this.value = this.value.slice(0, 12); } }
	}
}

function generarSlider(obj) {
	var str = "<div class='conainerSlider " + obj.direction + " hidden-sm hidden-xs' " + obj.style + ">" +
		"<div class='slider regular1'>" +
		"<div><img src='img/galeria/img_" + obj.img[0] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[1] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[2] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[3] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[4] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[5] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[6] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[7] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[8] + "_ByN.jpg'></div>" +
		"<div><img src='img/galeria/img_" + obj.img[9] + "_ByN.jpg'></div>" +
		"</div>" +
		"<div class='filter'></div>" +
		"</div>";
	return str;
}

function changeImg(direction) {
	if (direction === "left") {
		$(".regular1").slick('slickNext')
	} else {
		$(".regular1").slick('slickPrev')
	}
}

function confirmData() {
	$("input:valid, select:valid").each(function () {
		if (this.id === "checkTerminos") {
			$(".labelTerminos").removeClass("invalid")
		}
		$(this).removeClass("invalid");
		validation.form = true;
	})

	$("input:invalid, select:invalid").each(function () {
		if (this.id === "checkTerminos") {
			$(".labelTerminos").addClass("invalid")
		}
		$(this).addClass("invalid");
		validation.form = false;
	})

	var email = $("#email"), ConfirmarEmail = $("#ConfirmarEmail");
	if (email.val() !== "" && email.val() === ConfirmarEmail.val()) {
		email.removeClass("invalid");
		ConfirmarEmail.removeClass("invalid");
		validation.email = true;
	} else {
		email.addClass("invalid");
		ConfirmarEmail.addClass("invalid");
		validation.email = false;
	}

	if (validation.capcha && validation.form && validation.email) {
		//if(validation.form && validation.email){
		$(".validarDatos").off("click").text("Validando...");
		getData();
		clearFields();
	} else {
		$(".completarDatos").removeClass("hidden")
	}
}

function YourOnSubmitFn() {
	validation.capcha = true;
}

function getData() {
	var info = {
		tipoDocumento: $("#tipoDocumento").val(),
		nDocumento: $("#numeroDocumento").val(),
		idSede: $("#sede").val(),
		eMail: $("#email").val()
	}
	console.log(JSON.stringify(info));
	$.ajax({
		data: JSON.stringify(info),
		method: "POST",
		contentType: "application/json",
		crossDomain: true,
		//	  url: "http://10.125.65.78:8080/WebServices/convenio/afiliados",
		//	  url: "http://10.125.64.78:8080/WebServices/convenio/afiliados",
		//	  url: "http://172.0.0.67:8080/WebServices/convenio/afiliados",
		//	  url: "http://192.168.1.154:8080/convenio/afiliados",
		 	  url: "http://localhost:8080/WebServices/convenio/afiliados",
		//	  url: "https://www.comfamasmartfit.com:8081/convenio/afiliados",
		//	  url: "https://www.comfenalcosantandersf.com:8081/convenio/afiliados",
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.done(function (data) {
				console.log(data);
				Succes(data);
		})
		.fail(function (data) {
			console.log(data);
			error(data.status);
		})
		.always(always);

/*		data = {
			availableCodes: true,
			eMail: "estebangarcia1994@gmail.com",
			estadoAfiliado: true,
			idSede: 9,
			isAntiguo: false,
			nombreCompleto: "DANIEL ESTEBAN GARCIA GAVIRIA",
			numeroDoc: null,
			tarifa: null,
			tipoDocumentoIdentidad: 0,
			urlConvenio: "https://www.smartfit.com.co/people/new?idExterno=394&plano=smart&code=GVDQMFH4RV",
			valoresAfiliado: {
				mensualidad: 49000,
				inscripcion: 0,
				mantenimiento: 0
			}
		}
	
		Succes(data);*/
}


function Succes(data) {
	console.log("onSucces!")
	console.log(data.statusCode)
	if (!data.isAntiguo) {
		var mensualidad = data.valoresAfiliado.mensualidad.toString().split("");
		mensualidad = mensualidad[0] + "" + mensualidad[1] + "," + mensualidad[2] + "" + mensualidad[3] + "" + mensualidad[4]

		var mantenimiento = data.valoresAfiliado.mantenimiento.toString().split("");
		mantenimiento = mantenimiento.length > 1 ? mantenimiento[0] + "" + mantenimiento[1] + "," + mantenimiento[2] + "" + mantenimiento[3] + "" + mantenimiento[4] : '0';

		var nombre = data.nombreCompleto.split(" ")[0];
		console.log(nombre);
		$(".mensualidad").text("$" + mensualidad);
		$(".usuario").text(nombre.toLowerCase());
		$(".sede").text(sede(data.idSede));
		$(".inscripcion").text("$" + data.valoresAfiliado.inscripcion);
		$(".mantenimiento").text("$" + mantenimiento);
		$(".btnInscripcion").attr("href", data.urlConvenio);
		$(".primeraVez").addClass("lightboxOpened");
	} else {
		/*$(".recordarEmail").attr("href", data.urlConvenio);*/
		$(".condigoRegistrado").addClass("lightboxOpened");
		$(".recordarEmail").attr("href", data.urlConvenio);
		$(".recordarEmail").one("click", function () { recordarEmail(data) });
	}
}

function error(data) {
	console.log("onerror!")
	//	alert("Ocurrió un error comprobando los datos, inténtelo nuevamente.")
	console.log(data.statusCode)
	if (data === 604) {
		$(".sinDatos").addClass("lightboxOpened");
		return;
	}
	if (data === 605) {
		$(".sinCodigos").addClass("lightboxOpened");
		return;
	}
	if (data === 500) {
		$(".errorInterno").addClass("lightboxOpened");
		return;
	}
}

function always() {
	$(".validarDatos").on("click", confirmData).text("Validar");
	//$(".validarDatos").removeClass("disable").one("click", getData);
}

function recordarEmail(data) {
	dataService.url = data.urlConvenio;
	$(".recordarEmail").text("Enviando...");
	var info = {
		url: data.urlConvenio,
		nombre: data.nombreCompleto,
		eMail: $("#email").val()
	}
	$.ajax({
		data: JSON.stringify(info),
		method: "POST",
		contentType: "application/json",
		//		url: "http://10.125.65.78:8080/WebServices/convenio/afiliados/sendemail",
		//		url: "http://10.125.64.78:8080/WebServices/convenio/afiliados/sendemail",
		//		url: "http://172.0.0.67:8080/convenio/afiliados/sendemail",
		//		url: "http://192.168.1.154:8080/convenio/afiliados/sendemail",
//		url: "http://localhost:8080/WebServices/convenio/afiliados/sendemail",
		url: "https://www.comfamasmartfit.com:8081/convenio/afiliados/sendemail",
		headers: {
			'Content-Type': 'application/json'
		}
	}).done(SuccesEmail).fail(errorEmail).always(alwaysEmail);
}

function SuccesEmail(data) {
	if (data.statusCode === 200) {
		/*window.open(dataService.url, "_blank");*/
	} else {
		$(".infoEmail").text("Ocurrió un error, intenta nuevamente")
	}
	$(".recordarEmail").one("click", function () { recordarEmail(data) });
}
function errorEmail() {
	$(".infoEmail").text("Ocurrió un error, intenta nuevamente")
	$(".recordarEmail").one("click", function () { recordarEmail(data) });
}
function alwaysEmail() {
	$(".recordarEmail").text("Continuar")
}

function sede(num) {
	switch (num) {
		//case 1:
		case 31:
			return 'Puerta del Norte';
			break;
		//case 7:
		case 32:
			return 'Parque Envigado';
			break;
		//case 8:
		case 33:
			return 'Bosque Plaza';
			break;
		case 9:
			return 'El Tesoro';
			break;
		case 10:
			return 'Sandiego';
			break;
		case 11:
			return 'Los Colores';
			break;
		case 12:
			return 'Unicentro Medellin';
			break;
		case 13:
			return 'Los Molinos';
			break;
		case 16:
			return 'Mayorca';
			break;
		case 17:
			return 'La 10';
			break;
		case 18:
			return 'Itagüi';
			break;
		case 19:
			return 'San Ignacio';
			break;
		default:
			break;
	}
}

function clearFields() {
	console.log("clear");
	$("#numeroDocumento").val('');
	$("#email").val('');
	$("#ConfirmarEmail").val('');
}
