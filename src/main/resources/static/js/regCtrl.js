splitReaderApp.controller("RegCtrl", function ($scope, $http) {

    $scope.csrgToken = null;

    $scope.regSubmit = function (userAuthData, regForm) {
        $http.get("api/v1/csrf-token")
            .then(function successCallback(response) {
                console.log(response);
                csrf = response.data.token;

                if (regForm.$valid) {
                    var regData = {
                        "username": userAuthData.email,
                        "password": userAuthData.password,
                        "confirmPassword": userAuthData.confirmPassword,
                        "acceptRules": userAuthData.acceptRules
                    };
                    console.log(regData);
                    $http.post("api/v1/registration", regData,
                        {
                            params:
                            {
                                "_csrf": csrf
                            }
                        })
                        .then(
                            function (response) {
                                console.log(response.status);
                                document.location.href = 'index.html';
                            },
                            function (response) {
                                console.log("fail");
                            }
                        );
                }
            });
    }
}
);