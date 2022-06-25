$(document).on("ready", function () {
    calculateSlider();
    populateSelect();

    $(".duda").on("click", function () {
        if (!$(this).hasClass("open")) {
            $(".duda").removeClass("open");
            $(this).addClass("open");
        } else {
            $(".duda").removeClass("open");
        }
    });

    $("#formInsc").submit(function () {
        var url =
            "https://www.smartfit.com.co/carts?location_id=" +
            $("#sede").val() +
            "&plan=black&code=" +
            $("#inputProm").val() +
            "&landingOrigen=corp";
        window.location.replace(url);
        return false;
    });
});

function calculateSlider() {
    $(".headSliderWrapper").slick({
        dots: true,
        infinite: true
    });
    $(".middleSliderWrapper").slick({
        dots: true,
        infinite: true
    });
    $(".secondSliderWrapper").slick({
        dots: false,
        infinite: true
    });

    $(window).resize(function () {
        if (window.innerWidth <= 900) { }
    });
}

function populateSelect() {
    var selectValues = {
        Bogotá: {
            "410": "Plaza las americas",
            "435": "Calima Bogotá",
            "458": "Calle 116",
            "459": "Suba",
            "497": "Multiplaza La Felicidad",
            "539": "Calle 85",
            "555": "Mercurio",
            "556": "Calle 80",
            "585": "Éxito Bosa",
            "602": "Cedritos",
            "628": "El Tunal",
            "638": "Metrópolis",
            "552": "Salitre Plaza",
            "640": "Chapinero",
            "709": "Colina",
            "710": "Éxito Country",
            "722": "Bacatá",
            "865": "Metro Fontibón",
            "864": "Niza",
            "889": "20 De Julio",
            "586": "Eco Plaza",
            "988": "Calle 100"
        },
        Medellín: {
            "706": "Apartadó",
            "437": "Puerta Del Norte",
            "637": "Cabañas",
            "721": "Parque de Bello",
            "396": "Parque Envigado",
            "536": "Viva Envigado",
            "653": "La intermedia",
            "781": "Villa Grande",
            "454": "Parque Itagüí",
            "537": "Plaza Arrayanes Itagüi",
            "394": "El Tesoro",
            "395": "Sandiego",
            "389": "Bosque Plaza",
            "397": "Los Colores",
            "425": "Unicentro Medellin",
            "436": "Los Molinos",
            "453": "La 10",
            "476": "San Ignacio",
            "498": "Milla De Oro",
            "634": "La Central Buenos Aires",
            "875": "Florida Parque",
            "882": "Arkadia",
            "898": "De Moda Outlet",
            "392": "Mayorca",
            "779": "Aves Maria"
        },
        Cali: {
            "941": "La 14 de Buenaventura",
            "411": "Avenida Sexta",
            "499": "Único Cali",
            "553": "La Flora",
            "650": "Valle de Lilí La 14",
            "655": "Cosmocentro",
            "720": "Pasoancho",
            "719": "Calima Cali",
            "839": "La Hacienda",
            "949": "La 14 de Alfaguara",
            "409": "Unicentro Palmira",
            "1136": "Éxito Simón Bolívar",
            "1183": "Palmetto Plaza",

        },
        Monteria: {
            "393": "Alamedas"
        },
        Manizales: {
            "641": "Mall Plaza Manizales"
        },
        Neiva: {
            "636": "Único Neiva"
        },
        Cartagena: {
            "438": "Éxito Cartagena",
            "899": "Cuatro Vientos",
            "426": "El Castillo"
        },
        Barranquilla: {
            "500": "Único Barranquilla",
            "538": "Portal del Prado",
            "557": "Villa Carolina",
            "656": "Éxito Murillo",
            "654": "Nuestro Atlantico",
            "863": "Buenavista Mall Plaza",
            "1085": "San Vicente"
        },
        Popayán: {
            "554": "Campanario"
        },
        SantaMarta: {
            "612": "Ocean Mall Santa Marta"
        },
        Valledupar: {
            "633": "Unicentro Valledupar"
        },
        Villavicencio: {
            "639": "Único Villavicencio"
        },
        Tunja: {
            "707": "Viva Tunja"
        },
        Ibagué: {
            "708": "Arkacentro Ibagué",
            "826": "M30 Ibagué"
        },
        Sincelejo: {
            "796": "Viva Sincelejo"
        },
        Armenia: {
            "824": "Mocawa"
        },
        Barrancabermeja: {
            "933": "San Silvestre Barrancabermeja"
        },
        Yopal: {
            "894": "Alcaraván Yopal"
        },
        Riohacha: {
            "934": "Viva Wajiira"
        },
        Pereira: {
            "943": "La 14 Pereira",
            "1107": "Éxito Victoria Plaza"
        }
    };

    var cityCoord = {
        Bogotá: {
            lat: 4.643493,
            lng: -74.076921
        },
        Medellín: {
            lat: 6.249601,
            lng: -75.580111
        },
        Cali: {
            lat: 3.422472,
            lng: -76.523617
        },
        Monteria: {
            lat: 8.747653,
            lng: -75.882142
        },
        Manizales: {
            lat: 2.9377887,
            lng: -75.3424297
        },
        Neiva: {
            lat: 2.9377887,
            lng: -75.3424297
        },
        Cartagena: {
            lat: 10.394432,
            lng: -75.501905
        },
        Barranquilla: {
            lat: 10.98439,
            lng: -74.810831
        },
        Popayán: {
            lat: 2.459622,
            lng: -76.595166
        },
        SantaMarta: {
            lat: 11.2407904,
            lng: -74.1990433
        },
        Valledupar: {
            lat: 10.464853,
            lng: -73.2941668
        },
        Villavicencio: {
            lat: 2.9377887,
            lng: -75.3424297
        },
        Tunja: {
            lat: 5.533,
            lng: -73.367
        },
        Ibagué: {
            lat: 4.433,
            lng: -75.217
        },
        Sincelejo: {
            lat: 9.303794,
            lng: -75.395173
        },
        Armenia: {
            lat: 4.5364981,
            lng: -75.689634
        },
        Barrancabermeja: {
            lat: 7.11392,
            lng: -73.1198
        },
        Yopal: {
            lat: 5.35,
            lng: -72.45
        },
        Riohacha: {
            lat: 11.5444400,
            lng: -72.9072200
        },
        Pereira: {
            lat: 4.802535,
            lng: -75.6948276
        }
    };

    var $city = $("select.city");
    var $sede = $("select.sede");

    $city.append(function () {
        var output = "";
        $.each(selectValues, function (key, value) {
            output += "<option>" + key + "</option>";
        });
        $sede.prop("disabled", true);
        return output;
    });

    $city.change(function () {
        $sede.prop("disabled", false);
        $sede.empty().append(function () {
            var output = "";
            $.each(selectValues[$city.val()], function (key, value) {
                // output += '<option selected="selected" disabled>Elige <b>una sede</b></option>'
                output += "<option value=" + key + ">" + value + "</option>";
            });
            return output;
        });
        $sede.prepend(
            '<option selected="selected" value="" disabled>Elige <b>una sede</b></option>'
        );

        map.setCenter(cityCoord[$city.val()]);
        map.setZoom(10);
    });

    $sede.change(function () {
        var codSede = $sede.val();
        var nameSede = selectValues[$city.val()][codSede];

        $.each(sedes, function (index, value) {
            var pos = value[0].split(",");
            var sede = pos[0];

            if (nameSede == sede) {
                console.log(sede);
                var sedeLatLng = {
                    lat: value[1],
                    lng: value[2]
                };

                map.setCenter(sedeLatLng);
                map.setZoom(18);
            }
        });
    });
}

var map;

var sedes = [
    ["Plaza las americas, Bogotá, D.C.", 4.620231, -74.135864],
    ["Calima Bogotá, Bogotá, D.C.", 4.617689, -74.086561],
    ["Calle 116, Bogotá, D.C.", 4.698539, -74.050424],
    ["Suba, Bogotá, D.C.", 4.738635, -74.086493],
    ["Multiplaza La Felicidad, Bogotá, D.C.", 4.652483, -74.124899],
    ["Calle 85, Bogotá, D.C.", 4.669666, -74.054787],
    ["Mercurio, Bogotá, D.C.", 4.58896, -74.205315],
    ["Calle 80, Bogotá, D.C.", 4.691805, -74.08489],
    ["Éxito Bosa, Bogotá, D.C.", 4.605318, -74.183197],
    ["Cedritos , Bogotá, D.C.", 4.720822, -74.0272208],
    ["El Tunal, Bogotá, D.C.", 4.5779149, -74.1305891],
    ["Metrópolis, Bogotá, D.C.", 4.5779149, -74.1305891],
    ["Salitre Plaza, Bogotá, D.C.", 4.653466, -74.109908],
    ["Chapinero, Bogotá, D.C.", 4.639266, -74.065360],
    ["Colina, Bogotá, D.C.", 4.734382, -74.066225],
    ["Éxito Country, Bogotá, D.C.", 4.713269, -74.032541],
    ["Bacatá, Bogotá, D.C.", 4.604915, -74.069805],
    ["Metro Fontibón, Bogotá, D.C.", 4.680597, -74.152842],
    ["Niza, Bogotá, D.C.", 4.715387, -74.073201],
    ["20 De Julio, Bogotá, D.C.", 4.568892, -74.099720],
    ["Eco Plaza, Bogotá, D.C.", 4.713066, -74.221391],
    ["Calle 100, Bogotá, D.C.", 4.686814, -74.052061],
    ["Apartadó,  Antioquia", 7.885534, -76.632307],
    ["Puerta Del Norte,  Antioquia", 6.33992, -75.543461],
    ["Cabañas,  Antioquia", 6.317388, -75.555611],
    ["Parque de Bello,  Antioquia", 6.333287, -75.557155],
    ["Parque Envigado,  Antioquia", 6.168733, -75.58676],
    ["Viva Envigado,  Antioquia", 6.176816, -75.591466],
    ["La Intermedia,  Antioquia", 6.173416, -75.568048],
    ["Villa Grande,  Antioquia", 6.178033, -75.584386],
    ["Parque Itagüí,  Antioquia", 6.173617, -75.610520],
    ["Plaza Arrayanes Itagüi,  Antioquia", 6.167361, -75.619759],
    ["El Tesoro,  Antioquia", 6.196944, -75.558394],
    ["Sandiego,  Antioquia", 6.236371, -75.569038],
    ["Bosque Plaza,  Antioquia", 6.26911, -75.564993],
    ["Los Colores,  Antioquia", 6.2621455, -75.5872529],
    ["Unicentro Medellin,  Antioquia", 6.24089, -75.58661],
    ["Los Molinos,  Antioquia", 6.233735, -75.604132],
    ["La 10,  Antioquia", 6.212015, -75.573796],
    ["San Ignacio,  Antioquia", 6.245687, -75.564532],
    ["Milla De Oro,  Antioquia", 6.199608, -75.572592],
    ["La Central Buenos Aires,  Antioquia", 6.238790, -75.548650],
    ["Florida Parque,  Antioquia", 6.271077, -75.577175],
    ["Arkadia,  Antioquia", 6.212555, -75.594372],
    ["De Moda Outlet,  Antioquia", 6.219734, -75.583437],
    ["Mayorca,  Antioquia", 6.161205, -75.604876],
    ["Aves María,  Antioquia", 6.150843, -75.616870],
    ["La 14 de Buenaventura, Valle del Cauca", 3.882884, -77.020231],
    ["Avenida Sexta, Valle del Cauca", 3.471295, -76.527259],
    ["Único Cali, Valle del Cauca", 3.465169, -76.500091],
    ["La Flora, Valle del Cauca", 3.486028, -76.51652],
    ["Valle de Lilí La 14, Valle del Cauca", 3.367841, -76.523692],
    ["Cosmocentro, Valle del Cauca", 3.414471, -76.547692],
    ["Pasoancho, Valle del Cauca", 3.386656, -76.540867],
    ["Calima Cali, Valle del Cauca", 3.485458, -76.496947],
    ["La Hacienda, Valle del Cauca", 3.397253, -76.527608],
    ["La 14 de Alfaguara, Valle del Cauca", 3.258811, -76.557146],
    ["Unicentro Palmira, Valle del Cauca", 3.540262, -76.310996],
    ["Éxito Simón Bolívar, Valle del Cauca",  3.4275752, -76.5004785],
    ["Palmetto Plaza, Valle del Cauca", 3.4127525, -76.5405282],
    ["Alamedas, Córdoba", 8.763857, -75.873312],
    ["Éxito Cartagena, Bolívar", 10.394666, -75.485474],
    ["Cuatro Vientos, Bolívar", 10.405973, -75.500589],
    ["El Castillo, Bolívar", 10.425311, -75.540879],
    ["San Vicente, Atlántico", 11.004723, -74.823442],
    ["Único Barranquilla, Atlántico", 10.989892, -74.812163],
    ["Portal del Prado, Atlántico", 10.988783, -74.788723],
    ["Villa Carolina, Atlántico", 11.020795, -74.817091],
    ["Éxito Murillo, Atlántico", 10.969347, -74.790689],
    ["Nuestro Atlántico, Atlántico", 10.904664, -74.801762],
    ["Buenavista Mall Plaza, Atlántico", 11.016906, -74.831389],
    ["Campanario, Cauca", 2.459622, -76.595166],
    ["Unicentro Valledupar, Cesar", 10.4878888, -73.2617408],
    ["Viva Sincelejo, Sucre", 9.3022552, -75.3895295],
    ["Viva Tunja, Boyacá", 5.546247, -73.347911],
    ["Mall Plaza Manizales, Caldas", 5.066475, -75.490989],
    ["Alcaraván Yopal, Casanare", 5.335879, -72.385867],
    ["Viva Wajiira, Guajira", 11.536276, -72.921440],
    ["Único Neiva, Huila", 2.961972, -75.293387],
    ["Ocean Mall Santa Marta, Magdalena", 11.232342, -74.199678],
    ["Único Villavicencio, Meta", 4.130330, -73.623565],
    ["Mocawa, Quindío", 4.550530, -75.659516],
    ["La 14 de Pereira, Risaralda", 4.803573, -75.694762],
    ["Éxito Victoria Plaza Pereira, Risaralda", 4.811056,-75.692911],
    ["San Silvestre Barrancabermeja, Bucaramanga", 7.067377, -73.858141],
    ["M 30 Ibagué, Tolima", 4.441398, -75.221931]
];

var locations_detail = [
    '<div class="mylabel"><span class="titleSede"><b>Plaza las Americas</b></span><b> Bogotá, D.C. Bogotá, D.C.</b><br/><p>CLL 3 SUR # 71C - 19 Bogotá Plaza de las americas</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Calima</b></span><b> Bogotá, D.C. Bogotá, D.C.</b><br/><p>Av. calle 19 # 28-80 Bogota. CC Calima Local C41, Piso 3 Paloquemao</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Calle 116</b></span><b> Bogotá, D.C. Bogotá, D.C.</b><br/><p>Calle 116 # 19 - 41 Piso 4. Pepe Sierra</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Suba </b></span><b> Bogotá, D.C. Bogotá, D.C.</b><br/><p>Calle 145 # 94 - 11  Suba</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>La Felicidad</b></span><b> Bogotá, D.C. Bogotá, D.C.</b><br/><p>Av. Boyacá #13 Ciudadela La Felicidad</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Calle 85</b></span><b> Bogotá, D.C.</b><br/><p>Calle 85 # 14 - 05, Piso 2 La Cabrera</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Mercurio Soacha</b></span><b> Soacha Cundinamarca</b><br/><p>Carrera 7 # 32 - 35 C.C. Mercurio Rincón de Santa fe</p><br/><span class="divText"></span> <div>Mie y Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Calle 80</b></span><b>Bogotá, D.C.</b><br/><p>Calle 80 # 69T - 60 Santa Rosa</p><br/><span class="divText"></span><div>Mie y Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Éxito Bosa</b></span><b> Bogotá, D.C. Bogotá, D.C.</b><br/><p>Calle 65 sur # 78H - 54 - Éxito Bosa </p><br/><div>Martes: 5h - 22h <br></div><span class="divText"></span><div/>',
    '<div class="mylabel"><span class="titleSede"><b>Cedritos</b></span><b> Bogotá, D.C.</b><br/><p>Bogotá, D.C. Carrera 7 # 145 - 87 Cedritos</p><br/><span class="divText"></span><div>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span></div>',
    '<div class="mylabel"><span class="titleSede"><b>El Tunal</b></span><b> Bogotá, D.C.</b><br/><p>Bogotá, D.C. Carrera 24C # 48 - 94 El tunal</p><br/><span class="divText"></span><div>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span></div>',
    '<div class="mylabel"><span class="titleSede"><b>Metrópolis</b></span><b> </b><br/><p>Bogotá, D.C.Avenida 68 # 75 A - 50 Metrópolis</p><br/><span class="divText"></span><div>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span></div>',
    '<div class="mylabel"><span class="titleSede"><b>Salitre Plaza</b></span><b> Bogotá, D.C.</b><br/><p>Carrera 68B # 24 - 39 C.C. Salitre Plaza</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Chapinero</b></span><b> Bogotá, D.C.</b><br/><p>Carrera 13 # 51-57, Chapinero</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Colina</b></span><b> Bogotá, D.C.</b><br/><p>Av. Boyacá, Carrera 72 con Calle 146B, Colina</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Éxito Country</b></span><b> Bogotá, D.C.</b><br/><p>Calle 134 N° 9 – 51, Éxito Country</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Bacatá</b></span><b> Bogotá, D.C.</b><br/><p>Calle 19 N°5-30 Local 10, Bacatá</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Metro Fontibón</b></span><b> Bogotá, D.C.</b><br/><p>Calle 17 N°112-58, Fontibón</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Niza</b></span><b> Bogotá, D.C.</b><br/><p> Tv 60 N° 128-70 , CC Niza</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>20 De Julio</b></span><b> Bogotá, D.C.</b><br/><p>Carrera 10  # 30 B - 20 Sur, Bogotá</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Eco Plaza Mosquera</b></span><b> Mosquera Cundinamarca</b><br/><p>Carrera 3 # 15A - 57, Local 307 Mosquera</p><br/><span class="divText"></span><div>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br></div><span class="divText"></span></div>',
    '<div class="mylabel"><span class="titleSede"><b>Calle 100</b></span><b>Bogotá, D.C.</b><br/><p>AV. Carrera 19 # 100 - 45</p><br/><span class="divText"></span><div>Lun a Jue: 5h - 22h <br> Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span></div>',
    
    '<div class="mylabel"><span class="titleSede"><b>Apartadó</b></span><b> Antioquia</b><br/><p>Calle 103 # 100 – 43, Local 3372, CC Plaza del Rio, Apartadó, Antioquia </p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Puerta Del Norte</b></span><b> Bello Antioquia</b><br/><p>Autonorte # 34 - 67 Bello, CC Puerta del Norte bello</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Cabañas</b></span><b> Antioquia</b><br/><p>Carrera 51 # 25-23, Bello</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Parque de Bello</b></span><b> Antioquia</b><br/><p>Cr 49 N° 47-35, Bello</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Parque Envigado</b></span><b> Envigado Antioquia</b><br/><p>Cr 41 #38 Sur - 56 Envigado</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Viva Envigado</b></span><b> Envigado Antioquia</b><br/><p>Carrera 48 # 34 sur - 29 Envigado</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>La Intermedia</b></span><b> Envigado Antioquia</b><br/><p>Carrera 27 # 23 Sur - 205, Envigado</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Villa Grande</b></span><b> Envigado Antioquia</b><br/><p>Transversal 29 sur # 32b-126, Envigado</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Parque Itagüí</b></span><b> Itagüí Antioquia</b><br/><p>Carrera 52 # 49 - 37 Itagüí</p><br/><span class="divText"></span> <div>Lun y Jue: 5h - 22h <br>Martes: 6h - 23h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Plaza Arrayanes</b></span><b> Itagüí Antioquia</b><br/><p>CARRERA 50ª #36-90, Itagûí  Pilsen</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>El Tesoro</b></span><b> Medellín Antioquia</b><br/><p>Cra. 25A  #1A Sur-45 C.C. El Tesoro Piso 3 El poblado</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Sandiego</b></span><b> Medellín Antioquia</b><br/><p>Calle 34 # 43-66 C.C. Sandiego Sandiego</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Bosque  Plaza</b></span><b> Medellín Antioquia</b><br/><p>Calle 73 #51D-71 Local 3021 Universidad de Antioquia</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Los Colores</b></span><b> Medellín Antioquia</b><br/><p>Calle 53 # 73-22 Segundo piso Los Colores</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Unicentro Medellin</b></span><b> Medellín Antioquia</b><br/><p>Av. Bolivariana 66B #34a - 76 C.C. Unicentro piso 3 Laureles</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Los Molinos</b></span><b> Medellín Antioquia</b><br/><p>Cl. 30a # 82a - 26 Medellín CC Los Molinos, Piso 3 Belen</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>La 10</b></span><b> Medellín Antioquia</b><br/><p>Calle 10 # 43E - 60 El Poblado</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>San Ignacio</b></span><b> Medellín Antioquia</b><br/><p>Calle 48 No. 43-81 San Ignacio</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Milla De Oro</b></span><b> Medellín Antioquia</b><br/><p>Carrera 42 # 3 sur 81 Piso 2 El Poblado</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>La Central Buenos Aires</b></span><b> Medellín Antioquia</b><br/><p>Calle 49 # 20 - 210, Buenos Aires</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Florida Parque</b></span><b> Medellín Antioquia</b><br/><p>Calle 71 # 65-150, CC Florida Parque Comercial</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Arkadia</b></span><b> Medellín Antioquia</b><br/><p>Cr 80 # 1 - 09, Piso 5, Loca 0540 CC</p><br/><span class="divText"></span> <div>Lun a Jue: 7h - 22h <br>Viernes: 7h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>De Moda Outlet</b></span><b> Medellín Antioquia</b><br/><p>Calle 16 No. 55-129 local 16-3 nivel 1 - Santa Fe</p><br/><span class="divText"></span> <div>Lun a Jue: 7h - 22h <br>Viernes: 7h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Mayorca</b></span><b> Sabaneta Antioquia</b><br/><p>Cl. 51 Sur ##48-57 Piso 11, Sabaneta, Antioquia</p><br/><span class="divText"></span> <div>Lun a Jue: 7h - 22h <br>Viernes: 7h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Aves Mari</b></span><b> Sabaneta Antioquia</b><br/><p>Cl. 75 Sur #43-202, Sabaneta, Antioquia</p><br/><span class="divText"></span> <div>Lun a Jue: 7h - 22h <br>Viernes: 7h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',


    '<div class="mylabel"><span class="titleSede"><b>San Vicente</b></span><b> Barranquilla Atlántico</b><br/><p>Cll. 85 # 49c - 56 - Cra. 50 # 84 - 197 Ed. GZ Tower - San Vicente</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
    '<div class="mylabel"><span class="titleSede"><b>Éxito Victoria Plaza</b></span><b> Pereira Risaralda</b><br/><p> Carrera 10 # 14-71, Éxito Victoria Plaza</p><br/><span class="divText"></span> <div>Lun a Jue: 5h - 22h <br>Viernes: 5h - 21h <br>Sábado: 7h - 17h <br>Domingo: 8h - 17h <br></div><span class="divText"></span> </div>',
];

var myLatLng = {
    lat: 4.634169,
    lng: -73.504493
};

function initMap() {
    navigator.geolocation.getCurrentPosition(geo_success, geo_error);
}

function geo_error() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: myLatLng,
        scrollwheel: false,
        // zoom: 16
        zoom: 5
    });
    createMark();
}

function geo_success(position) {
    myLatLng.lat = position.coords.latitude;
    myLatLng.lng = position.coords.longitude;

    // Create a map object and specify the DOM element for display.
    map = new google.maps.Map(document.getElementById("map"), {
        center: myLatLng,
        scrollwheel: false,
        zoom: 16
    });
    var iconBase = "img/marcador-mapa.png";

    // Create a marker and set its position.
    var marker = new google.maps.Marker({
        map: map,
        position: myLatLng,
        title: "Usted esta aqui"
    });

    createMark();
}

function createMark() {
    var markers = [];
    var infoBubble = [];
    $.each(sedes, function (index, value) {
        var myLatLng = {
            lat: value[1],
            lng: value[2]
        };
        var iconBase = "img/marcador-mapa.png";

        // Create a marker and set its position.
        markers[index] = new google.maps.Marker({
            map: map,
            position: myLatLng,
            title: "Smart Fit |" + value[0],
            icon: iconBase
        });

        google.maps.event.addListener(markers[index], "click", function () {
            if (!infoBubble[index].isOpen()) {
                infoBubble[index].open(map, this);
            } else {
                infoBubble[index].close();
            }
        });

        infoBubble[index] = new InfoBubble({
            map: map,
            content: locations_detail[index],
            shadowStyle: 1,
            padding: 0,
            backgroundColor: "rgba(57,57,57,0.8)",
            borderRadius: 4,
            arrowSize: 10,
            borderWidth: 1,
            borderColor: "#2c2c2c",
            disableAutoPan: true,
            hideCloseButton: false,
            arrowPosition: 30,
            backgroundClassName: "phoney",
            arrowStyle: 2
        });
    });
}