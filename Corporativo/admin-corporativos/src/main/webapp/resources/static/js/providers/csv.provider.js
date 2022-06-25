(function() {
    'use strict';

    angular.module('servicesModule').service('csvProvider', csvProvider);

    csvProvider.$inject = ['$q', '$base64'];

    function csvProvider($q, $base64) {

        var service = {
            loadCsv: loadCsv,
            loadImage: loadImage,
            loadCsvAsString:loadCsvAsString,
            loadImageAsString:loadImageAsString,
            decodeCsv: decodeCsv,
            download: download
        };

        return service;


        /**
         * Carga un csv y lo devuelve como base64
         */
        function loadCsv(fileInput) {
            var defered = $q.defer();
            
            if( fileInput.files && fileInput.files.length > 0 ) {
            	
            	if( isCsv(fileInput.files[0].name) ) {
            		var reader = new FileReader();
    				reader.onload = function () {
    					defered.resolve(reader.result);
    				};
                    reader.readAsDataURL(fileInput.files[0]);
            	} else {
            		alert("El formato del archivo no es válido.");
                    defered.reject();
            	}
			} else {
                defered.reject();
            }
            return defered.promise;
        }

        /**
         * Carga un csv y lo devuelve como String
         */
        function loadCsvAsString(fileInput) {
            var defered = $q.defer();
            
            if(fileInput.files && fileInput.files.length > 0 && isCsv(fileInput.files[0].name)) {
            
                var reader = new FileReader();
				reader.onload = function () {
					let textDoc = decodeURIComponent(escape(reader.result));
					defered.resolve(textDoc.split(/\n/));
				};
                reader.readAsBinaryString(fileInput.files[0]);
                
			} else {
                defered.reject();
            }
            return defered.promise;
        }
        
        /**
         * Carga un jpg, png y lo devuelve como base64
         */
        function loadImage(fileInput) {
            var defered = $q.defer();
            
            if( fileInput.files && fileInput.files.length > 0 ) {
            
            	if( isImage(fileInput.files[0].name) ){
	                var reader = new FileReader();
					reader.onload = function () {
						defered.resolve(reader.result);
					};
	                reader.readAsDataURL(fileInput.files[0]);
            	} else {
            		alert("El formato del archivo no es válido.");
                    defered.reject();
            	}
			} else {
				defered.reject();
			}
            return defered.promise;
        }
        
        /**
         * Carga un jpg, png y lo devuelve como String
         */
        function loadImageAsString(fileInput) {
            var defered = $q.defer();
            
            if(fileInput.files && fileInput.files.length > 0 && isImage(fileInput.files[0].name)) {
            
                var reader = new FileReader();
				reader.onload = function () {
					defered.resolve(reader.result.split(/\n/));
				};
                reader.readAsBinaryString(fileInput.files[0]);
                
			} else {
                defered.reject();
            }
            return defered.promise;
        }

        function download(filename, encodedUri) {
			var element = document.createElement('a');
			element.setAttribute('href', encodedUri);
			element.setAttribute('download', filename);
			element.style.display = 'none';
			document.body.appendChild(element);
			element.click();
			document.body.removeChild(element);
		}

        function decodeCsv(csv) {
            var csvFile = "Tm9tYnJlLENvcnJlbyxUaXBvIERvY3VtZW50byxOdW1lcm8gRG9jdW1lbnRvDQpMaW5hIE1hcmNlbGEgTWFyaW4gQXJhbmdvLGxpbmEubWFyaW5AZm91cnBvaW50c21lZGVsbGluLmNvbSxDQywNCkVkaWVyIGRlIEplc8O6cyBWZWxhc3F1ZXogTG9wZXosZ2VyZW5jaWFAdGVybWluYWxkZWxzdXIuY29tLCwNClllc3NlbmlhIEfDs21leiBUb3JyZXMseWVzc2Vnb3RvQGhvdG1haWwuY29tLCwNCk9sZ2EgTHVjaWEgUm9sZGFuIFJ1aXosb2xyb2xkYW5AdW5lLm5ldC5jbywsDQpSb3NhIE1hcmlhIFJhbWlyZXogTm9yZcOxYSxyb3NhNTRjb2xvbWJpYUB5YWhvby5jb20sLA0KQ3Jpc3RpYW4gRGFuaWVsIFNpZXJyYSBQcm90byxEaW9zbWVoaXpvbmVncm9AaG90bWFpbC5jb20sLA0KTWFyaWEgU2FudG9zIFBhbGFjaW9zIFJvbWHDsWEsbWFyaWFzYW50b3NwYWxhQGhvdG1haWwuY29tLCwNCkNlc2FyIEF1Z3VzdG8gQ2FubyBNZWppYSxjZXNhcmNhbm8yMDIwQGdtYWlsLmNvbSwsDQpNYXVyaWNpbyBHYWxsZWdvIEZyYW5jbyxtYW9nZjgwQGhvdG1haWwuY29tLCwNCkVsaWFuYSBZdWxpZXRoIEdhcmNpYSBGcmFuY28sZWxpYW5hZ2FyY2lhMS5lc0BvdXRsb29rLmVzLCwNCkZyZWR5IEFsb25zbyBSb2RyaWd1ZXogSGVybmFuZGV6LGZyYXlyb2RyaWd1ZXpoQGhvdG1haWwuY29tLCwNCkplaXNvbiBTdGl2ZW4gU2FudGFuYSBQYWxhY2lvLGplaXNvbnNhbnRhbmExMEBob3RtYWlsLmNvbSwsDQpKb2hhbm5hIEFuZHJlYSBHaXJhbGRvIFNhbGF6YXIsam9oYW5uYWdzMTZAaG90bWFpbC5jb20sLA0KRGVpc3kgQ2Fyb2xpbmEgU2FuY2hleiBCbGFuZG9uLGthcml0by0xOTg2QGhvdG1haWwuY29tLCwNCk7DqXN0b3IgR29sZHd1aW4gTWFkcmlnYWwgQXJjaWxhLGZvdG9nb2xkMTk2N0Bob3RtYWlsLmNvbSwsDQpHbGFkeXMgwqBBcmNpbGEgQ2FyZG9uYSxnbGFkeXNjYXJkb25hMjRAaG90bWFpbC5jb20sLA0KQ2Fyb2xpbmEgQ29ycmVhIFZlbGV6LGNjb3JyZWFAaW5kYWVyLmNvbSwsDQpKb2huIEphaXJvIFF1aW50ZXJvIFBlcmV6ICxxdWludGVyb2NvQGdtYWlsLmNvbSwsDQpSaWNhcmRvIEFuZHJlcyBRdWludGVybyBHdXptYW4gLHF1aW50ZXJvY29AZ21haWwuY29tLCwNCkV2ZWx5biBRdWludGFuYSBCZXRhbmN1cixxdWludGVyb2NvQGdtYWlsLmNvbSwsDQpBbmRyZXMgTWF1cmljaW8gTXXDsWV0b24gVmFzcXVleixhbmQ2NTRAaG90bWFpbC5jb20sLA0KSm9hbiBEYXZpZCBIZXJuYW5kZXosamRhdmlkeXB1bnRvQGhvdG1haWwuY29tLCwNCkp1YW4gUGFibG8gQ2FubyBQZW5hZ29zLGNhbm9wZW5hZ29zQGdtYWlsLmNvbSwsDQpKYW5ldGggVmlyZ2luaWEgTm9yZcOxYSBWYXNxdWV6LHBhdWxhLmJvdGVyb0BlbmVyZ2lheXBvdGVuY2lhLmNvbSwsDQpMdWlzIE1pZ3VlbCBNaXJhbmRvIEJlbGxvLGx1aXNtYjI0MDFAZ21haWwuY29tLCwNCllvbGFuZGEgQW5kcmVhIEVzY3VkZXJvIFVycmVhICxhbmRyZWEuZXNjdWRlcm8xNDE0QGdtYWlsLmNvbSwsDQpKb3JnZSBDb21mYW1hLGphY29zdGFAY29tZmFtYS5jb20uY28sLA0KSm9yZ2UgR21haWwsam9yZ2VhY29zdGF0b3JyZXNAZ21haWwuY29tLCwNCkNhcmxvcyBUb2JvbixjYXJsb3N0b2JvbkBjb21mYW1hLmNvbS5jbywsDQpDYXJsb3MgVG9ib24gZ21haWwsY2FybG9zZXRvYkBnbWFpbC5jb20sLA0K";
            if(csv) return $base64.decode(csv);
            return $base64.decode(csvFile);
        }

        function isCsv(name) {
        	name = name.toLowerCase();
        	var extension = name.substring(name.length - 3 , name.length);
        	extension = extension.toLowerCase();
        	return  (extension === 'csv');
        }
        
        function isImage(name) {
        	name = name.toLowerCase();
        	var extension = name.substring(name.length - 3 , name.length);
        	extension = extension.toLowerCase();
            return  (extension === 'jpg' || extension === 'png');
        }

    }
})();