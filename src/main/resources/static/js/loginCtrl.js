splitReaderApp.controller("LoginCtrl", function ($scope, $http) {

    clearUserAuthData();
    // $scope.userDetails = {
    //     email: ""
    // };

    showUserDetails();

    $scope.loginSubmit = function (userAuthData, loginForm) {

        $http.get("api/v1/csrf-token")
            .then(function successCallback(response) {
                console.log(response);
                csrf = response.data.token;

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
                                clearUserAuthData();
                            },
                            function (response) {
                                console.log("fail");
                                clearUserAuthData();
                            }
                        );
                }
            });
    }

    $scope.logoutSubmit = function () {
        $http.get("logout")
            .then(function successCallback(response) {
                console.log(response);
                hideUserDetailsBlock();
                showLoginFormBlock();
            });
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

    function getCsrf() {
        $http.get("api/v1/csrf-token")
            .then(function successCallback(response) {
                console.log(response);
                csrf = response.data.token;
            });
    }

    function showUserDetails() {

        $http.get("api/v1/user")
            .then(
                function (response) {
                    console.log(response);
                    if (response.data.username != null) {
                        $scope.userDetails = {
                            email: response.data.username,
                            firstName: response.data.firstName,
                            lastName: response.data.lastName
                        };
                        hideLoginFormBlock();
                        showUserDetailsBlock();
                    }
                },
                function (response) {
                    console.log(response);
                });
    }

    function clearUserAuthData() {
        $scope.userAuthData = {
            email: "",
            password: "",
            csrf: ""
        };
    }
});