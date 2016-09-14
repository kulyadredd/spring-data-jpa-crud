var app = angular.module('app', ['ngRoute', 'ngResource', 'ngAnimate', 'ui.bootstrap']);
app.config(function ($routeProvider) {
    $routeProvider
        .when('/users', {
            templateUrl: '/template/Users.html',
            controller: 'usersController'
        })
        .otherwise({
            redirectTo: '/users'
        })
});