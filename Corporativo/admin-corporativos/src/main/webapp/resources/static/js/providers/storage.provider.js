(function() {
    'use strict';

    angular.module('providersModule', []).service('storageProvider', storageProvider);

    storageProvider.$inject = ['$window'];

    function storageProvider($window) {

        var service = {
            addVar: addVar,
            getVar: getVar,
            editVar: editVar,
            deleteVar: deleteVar,
            clear: clear
        };

        return service;

        ////////////

        /**
         * Añade valor a storage
         * 
         */
        function addVar(key, value) {
            $window.localStorage.setItem(key, JSON.stringify(value));
            return true;
        }

        /** 
         * Obtiene valor de storage
         * 
         */
        function getVar(key) {
            return $window.localStorage && JSON.parse($window.localStorage.getItem(key));
        }

        /**
         * Edita el valor de storage
         * 
         */
        function editVar(key, value) {
            $window.localStorage.setItem(key, JSON.stringify(value));
            return true;
        }

        /**
         * Elimina el valor de storage
         * 
         */
        function deleteVar(key) {
            $window.localStorage.removeItem(key);
            return true;
        }
        
        /**
         * Limpia el storage
         * 
         */
        function clear() {
            $window.localStorage.clear();
            return true;
        }

    }
})();