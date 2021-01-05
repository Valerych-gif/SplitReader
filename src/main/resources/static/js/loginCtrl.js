splitReaderApp.controller("LoginCtrl", function ($scope, $http) {
    $scope.userAuthData = {
        email: "",
        password: "",
        csrf: ""
    };

    $scope.userDetails = {
        email: ""
    };

    $http.get("api/v1/csrf-token")
        .then(function successCallback(response) {
            console.log(response);
            csrf = response.data.token;
        });

    showUserDetails();

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
                })
                .then(
                    function (response) {
                        console.log(response.status);
                        showUserDetails();
                    },
                    function (response) {
                        console.log("fail");
                    }
                );
        }
    }

    function hideLoginFormBlock() {
        element = document.getElementById("form_login_block");
        element.classList.add("invisible");
    }

    function showLoginFormBlock() {
        element = document.getElementById("form_login_block");
        element.classList.remove("invisible");
    }

    function hideUserDetailsBlock() {
        element = document.getElementById("user_details_block");
        element.classList.add("invisible");
    }

    function showUserDetailsBlock() {
        element = document.getElementById("user_details_block");
        element.classList.remove("invisible");
    }

    function showUserDetails() {

        $http.get("api/v1/user")
            .then(
                function (response) {
                    console.log(response);
                    if (response.data.username != null) {
                        $scope.userDetails = {
                            email: response.data.username
                        };
                        hideLoginFormBlock();
                        showUserDetailsBlock();
                    }
                },
                function (response) {
                    console.log(response);
                });
    }
});