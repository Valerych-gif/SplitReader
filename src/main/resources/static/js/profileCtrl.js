splitReaderApp.controller("ProfileCtrl", function ($scope, $http) {

    $http.get("api/v1/user")
        .then(
            function (response) {
                console.log(response);
                if (response.data.username != null) {
                    $scope.userDetails = response.data;
                }
            },
            function (response) {
                console.log(response);
            });

    $scope.logoutSubmit = function () {
        $http.get("logout")
            .then(
                function successCallback(response) {
                    console.log(response);
                    document.location.href = 'index.html';
                },
                function successCallback(response) {
                    console.log(response);
                }
            );
    }
});