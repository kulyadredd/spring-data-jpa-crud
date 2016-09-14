/**
 * Server Service
 * Service that holds all possible server request
 *
 * Created by Kulinenko Roman
 */
app.factory('serverService', function ($http, $q) {

    var server = {
        restGET: '/api/users',
        restPOST: '/api/users',
        restPUT: '/api/users',
        restDELETE: '/api/users/'
    };

    return {
        getUsers: function (page, perPage) {
            var deferred = $q.defer();
            $http.get(server.restGET, {params: {page: page, perPage: perPage}}).then(function (response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        },
        createUser: function (user) {
            return $http.post(server.restPOST, user);
        },
        deleteUser: function (userId) {
            return $http.delete(server.restDELETE + userId);
        },
        updateUser: function (user) {
            return $http.put(server.restPUT, user);
        }

    }
});