splitReaderApp.controller("LoginCtrl", function ($scope, $http) {
    $scope.userAuthData = {
        email: "",
        password: ""
    };

    $scope.csrf = null;

    $http.get("api/v1/csrf-token")
        .then(function successCallback(response) {
            $scope.csrf = response.data;
        });

    $scope.loginSubmit = function (userAuthData, loginForm) {
        var data = {
            "username": userAuthData.email,
            "password": userAuthData.password,
            "_csrf": $scope.csrf.token
        };

        if (loginForm.$valid) {
            var res = $http.post("authuser", "",
                {
                    method: "POST",
                    url: "login",
                    params:
                    {
                        "username": userAuthData.email,
                        "password": userAuthData.password,
                        "_csrf": $scope.csrf.token
                    }
                });
        }
    }
})