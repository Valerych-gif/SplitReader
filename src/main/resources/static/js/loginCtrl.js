splitReaderApp.controller("LoginCtrl", function ($scope, $http) {
    $scope.userAuthData = {
        email: "",
        password: ""
    };

    $scope.loginSubmit = function (userAuthData, loginForm) {
        var data = {
            email: userAuthData.email,
            password: userAuthData.password
        };
        if (loginForm.$valid) {
            $http.post("api/v1/login", JSON.stringify(data));
        }
    }
})