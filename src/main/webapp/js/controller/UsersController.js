/**
 * Controller that handles all user action
 *
 * Created by Kulinenko Roman
 */
app.controller("usersController", function ($scope, serverService, modalService, userService) {

    $scope.itemPerPage = 5;
    $scope.defaultPage = 1;
    $scope.showPage = 5;
    $scope.disableFirstAndPrevBtn = true;
    $scope.disableLastAndNextBtn = true;
    $scope.pages = [];

    $scope.headingTitle = "Data JPA simple CRUD";
    $scope.animationsEnabled = true;
    $scope.data = [];

    loadUsers($scope.defaultPage);

    $scope.updateUser = function (user) {
        serverService.updateUser(user).then(function (answer) {
            loadUsers($scope.data.number);
        });
    };

    $scope.deleteUser = function (user) {
        serverService.deleteUser(user.id).then(function (answer) {
            loadUsers($scope.data.number);
        });

    };

    $scope.openModalAddNewUser = function () {
        modalService.show($scope.animationsEnabled).then(function (users) {
            loadUsers($scope.defaultPage);
        });
    };

    $scope.goToPage = function (page) {
        loadUsers(page);
    }

    $scope.nextPage = function () {
        loadUsers(++$scope.data.number);
    };

    $scope.lastPage = function () {
        loadUsers($scope.data.totalPages);
    };

    $scope.prevPage = function () {
        loadUsers(--$scope.data.number);
    };

    function loadUsers(page) {
        serverService.getUsers(page, $scope.itemPerPage).then(function (answer) {
            $scope.data = answer;
            btnControls();
            $scope.pages = userService.pageControls($scope.defaultPage, $scope.showPage,
                $scope.data.number, $scope.data.totalPages)
        });
    }

    function btnControls() {
        if ($scope.data.totalPages < $scope.defaultPage
            || $scope.data.number == $scope.data.totalPages - 1)
            $scope.disableLastAndNextBtn = true;
        else
            $scope.disableLastAndNextBtn = false;

        $scope.data.number++;

        if ($scope.data.number > $scope.defaultPage)
            $scope.disableFirstAndPrevBtn = false;
        else
            $scope.disableFirstAndPrevBtn = true;
    }
});