splitReaderApp.controller("ProfileCtrl", function ($scope, $http) {

    showUserDetails();

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

    $scope.editProfile = function () {
        console.log("Profile editing");
        $scope.userData = $scope.userDetails;
        hideUserDetailsBlock();
        showUserDetailsEditBlock();
    }

    $scope.saveUserData = function (userData, editProfileForm) {
        $http.get("api/v1/csrf-token")
            .then(function successCallback(response) {
                console.log(response);
                csrf = response.data.token;

                if (editProfileForm.$valid) {
                    console.log("User data sending...");
                    console.log(userData);
                    $http.put("api/v1/user", userData,
                        {
                            params:
                            {
                                "_csrf": csrf
                            }
                        })
                        .then(
                            function (response) {
                                console.log(response.status);
                                showUserDetails();
                                $scope.userData = {};
                            },
                            function (response) {
                                console.log("fail");
                                $scope.userData = {};
                            }
                        );
                }
            });
        console.log("Profile saving");
        hideUserDetailsEditBlock()
        showUserDetailsBlock();
    }

    function showUserDetailsBlock() {
        document.getElementById("user_details_block").classList.remove("invisible");
        document.getElementById("edit-profile-link").classList.remove("invisible");
    }

    function showUserDetailsEditBlock() {
        document.getElementById("user_details_edit_block").classList.remove("invisible");
    }

    function hideUserDetailsBlock() {
        document.getElementById("user_details_block").classList.add("invisible");
        document.getElementById("edit-profile-link").classList.add("invisible");
    }

    function hideUserDetailsEditBlock() {
        document.getElementById("user_details_edit_block").classList.add("invisible");
    }

    function showUserDetails() {

        $http.get("api/v1/user")
            .then(
                function (response) {
                    console.log(response);
                    if (response.data.username != null) {
                        $scope.userDetails = response.data;
                        hideUserDetailsEditBlock();
                        showUserDetailsBlock();
                    }
                },
                function (response) {
                    console.log(response);
                });
    }

});