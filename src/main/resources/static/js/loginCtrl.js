angular.module("SplitReaderApp").controller("LoginCtrl", function ($scope, $http, $localStorage) {

    clearUserAuthData();
    showUserDetails();

    $scope.loginSubmit = function (userAuthData, loginForm) {

        console.log("Try to login...");

        if (loginForm.$valid) {
            console.log("Form was checked");
            var res = $http.post("authuser",
                {
                    "username": userAuthData.email,
                    "password": userAuthData.password,
                },
                {
                    params:
                    {
                        "username": userAuthData.email,
                        "password": userAuthData.password,
                    }
                })
                .then(function successCallback(response) {
                    console.log(response.data);
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.currentUser = { username: userAuthData.email, token: response.data.token };
                        console.log($localStorage.currentUser);
                        showUserDetails();
                        clearUserAuthData();
                    }
                }, function errorCallback(response) {
                    window.alert(response.data.message);
                    console.log("fail");
                    clearUserAuthData();
                });

        }
    }

    $scope.logoutSubmit = function () {

        $scope.clearUser();
        if ($scope.userAuthData.email) {
            $scope.userAuthData.email = null;
        }
        if ($scope.userAuthData.password) {
            $scope.userAuthData.password = null;
        }

    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
        console.log($localStorage.currentUser);
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            return true;
        } else {
            return false;
        }
    };

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
                        $scope.userDetails = response.data;
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