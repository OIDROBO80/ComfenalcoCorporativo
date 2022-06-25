(function() {
    'use strict';

    angular.module('servicesModule').controller('adminController', adminController);

    adminController.$inject = [ 'adminService' ];

    function adminController(adminService) {
        var vm = this;

        vm.logout = logout;

        // onInit();

        // function onInit() {}

        function logout() {
            adminService.logout();
        }
    }
})();