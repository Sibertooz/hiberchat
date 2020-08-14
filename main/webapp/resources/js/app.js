'use strict';

var app = angular.module('AngularSpringApp', []).run(function ($rootScope, $http) {
    $rootScope.hw = "HELLO PRIDURKI";

    /*var getCreditHistoryPersonalURL = 'personalCredit/getCreditHistoryPersonal';
    $rootScope.fetchCreditHistoryPersonal = function () {
        $http.get(getCreditHistoryPersonalURL).success(function (creditHistory) {
            $rootScope.creditHistoryPersonal = creditHistory;
        });
    };*/

});
