splitReaderApp.controller("LoginCtrl", function ($scope, $http) {
    // $scope.userAuthData;
    //  = {
    //     email: "",
    //     password: "",
    //     csrf: ""
    // };

    $http.get("api/v1/csrf-token")
        .then(function successCallback(response) {
            csrf = response.data.token;
        });

    $scope.loginSubmit = function (userAuthData, loginForm) {

        if (loginForm.$valid) {
            var res = $http.post("authuser", "",
                {
                    params:
                    {
                        "username": userAuthData.email,
                        "password": userAuthData.password,
                        "_csrf": csrf
                    }
                });
        }
    }
})