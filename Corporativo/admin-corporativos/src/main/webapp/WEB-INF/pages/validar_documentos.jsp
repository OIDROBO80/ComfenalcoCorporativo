<!DOCTYPE html>

<head>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-csv/0.71/jquery.csv-0.71.min.js"></script>
	<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
	<!-- Optional theme -->
	<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
	crossorigin="anonymous">
    <style>
        .menuHeader {
            left: 0px;
            top: 0px;
            z-index: 3;
            width: 100%;
            height: 90px;
            background-color: #000;
        }

        .width1150 {
            width: 100%;
            margin: 0 auto;
            position: relative;
        }

        .height100 {
            height: 100%;
        }

        .containerCenter {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .width100 {
            width: 100%;
        }
    </style>
</head>

<body>

    <div class="menuHeader">
        <div class="width1150 height100">
            <div class="col-md-12 col-sm-3 height100 containerCenter">
                <a href="#"><img class="width100 logoFooter center-block" src="https://www.comfamasmartfit.com:8081/Admin/resources/img/logoFooter.png"></a>
            </div>
        </div>
    </div>
     <section>
        <div class="panel panel-default">
            <div class="panel-body">
                <ol class="breadcrumb section-menu" style="display: flex;justify-content: center;">
                    <li class="active"><a href="/Admin/crear_convenio_empresa">Crear Empresa</a></li>
                    <li><a href="/Admin/listar_convenio_empresas">Listar Empresas</a></li>
                    <!--li><a>Validar</a></li-->
                </ol>
            </div>
        </div>
    </section>
    <br/>
    <a href="EjemploCsv.csv">Descargue CSV de ejemplo</a>
    <br/><br/>
	tipo_documento, no_documento, promo_title
    <br/><br/>
	Comfama
	<br/>
	|  1 | CC01   | cedula                       |
	<br/>
	|  2 | TI01   | tarjeta de identidad         |
	<br/>
	|  3 | RC01   | registro civil               |
	<br/>
	|  5 | CE01   | cedula extranjeria           |
	<br/>
	|  6 | NT01   | NIT                          |
	<br/>
	|  8 | PPN0   | Pasaporte                    |
	<br/>
	|  9 | PEP0   | Permiso Especial Permanencia |
	<br/><br/>
    <div id="dvImportSegments" class="fileupload ">
        <fieldset>
            <legend>Seleccione un archivo en formato CSV</legend>
            <input type="file" name="File Upload" id="txtFileUpload" accept=".csv" />
        </fieldset>
    </div>
    <br/>
    <div class="result">
        <fieldset>
            <legend>RESULTADOS</legend>
            <div id="resultados">

            </div>

        </fieldset>
        <fieldset>
            <legend>ERRORES</legend>
            <div id="errores">

            </div>

        </fieldset>
    </div>

    <script type="text/javascript">
        $(document).ready(function () {
            var i = 0;
            var data = null;
            // The event listener for the file upload
            document.getElementById('txtFileUpload').addEventListener('change', upload, false);

            // Method that checks that the browser supports the HTML5 File API
            function browserSupportFileUpload() {
                var isCompatible = false;
                if (window.File && window.FileReader && window.FileList && window.Blob) {
                    isCompatible = true;
                }
                return isCompatible;
            }

            // Method that reads and processes the selected file
            function upload(evt) {

                if (!browserSupportFileUpload()) {
                    alert('The File APIs are not fully supported in this browser!');
                } else {
                    var file = evt.target.files[0];
                    var reader = new FileReader();
                    reader.readAsText(file);
                    reader.onload = function (event) {
                        var csvData = event.target.result;
                        data = $.csv.toArrays(csvData);
                        if (data && data.length > 0) {
                            console.log(data[1][1]);
                            llamarAjax();
                        } else {
                            alert('No data to import!');
                        }
                    };
                    reader.onerror = function () {
                        alert('Unable to read ' + file.fileName);
                    };
                }
            }

            function llamarAjax() {
                i++;
                console.log(i);
                $.ajax({
                    type: "GET",
					url: "https://www.comfamasmartfit.com:8081/convenio/afiliados?tipoDocumento=" +
                    data[i][0] + "&nDocumento=" + data[i][1],
                    success: function (data2) {
                        try {
                            console.log("DEB: ", data2);
							
                            $("#resultados").append(data2 + "<br>");
                            if (i < data.length) {
                                llamarAjax();
                            }
                        }
                        catch (err) {
                            console.log("Ocurrio un error despues de la llamada al servicio  --> " + err);
                            $("#errores").append(data2 + "Se presento un error con los datos" + "<br>");
                            llamarAjax(); 
                            console.log("Se continua con el llamado despues del ERROR tipo 1 ");
                        }
                    },
                    error: function (xhr, status, error, data, data2) {
                        console.log("El detalle del error es " + xhr.status + error);
                        console.log("Error!" + xhr.status); 
                        llamarAjax();
                        console.log("Se continua con el llamado despues del ERROR tipo 2 ");

                    }
                });
            }
        });
    </script>
</body>

</html>