splitReaderApp.controller("LoginCtrl", function ($scope, $http) {
    $scope.userAuthData = {
        email: "",
        password: ""
    };

    var csrf = "";

    $http.get("api/v1/csrf-token")
        .then(function successCallback(response) {
            csrf = response.data.token;
            alert(csrf);
        });

    $scope.loginSubmit = function (userAuthData, loginForm) {
        var data = {
            "email": userAuthData.email,
            "password": userAuthData.password
        };
        console.log(data);
        if (loginForm.$valid) {
            $http(
                {
                    method: 'POST',
                    url: "api/v1/login",
                    headers: {
                        "X-CSRF-TOKEN": csrf
                    },
                    data: JSON.stringify(data)
                });
        }
    }
})