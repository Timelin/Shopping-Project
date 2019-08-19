app.controller("phoneController",function ($scope, $controller, phoneService) {

    $scope.entity = { "phone":""};
    $scope.register = function () {

        if ($scope.entity.phone == "") {
            alert("请输入手机号");
            return;
        }

        phoneService.register($scope.entity,$scope.smsCode).success(function (response) {
            alert(response.message);
        });
    };



    $scope.entity = {};
    $scope.sendSmsCode = function () {
        if($scope.entity.phone == null || $scope.entity.phone=="") {
            alert("请输入手机号");
            return;
        }

        phoneService.sendSmsCode($scope.entity.phone).success(function (response) {
            alert(response.message);
        });
    };


});