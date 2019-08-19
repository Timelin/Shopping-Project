app.controller("safeController",function ($scope, $controller, safeService) {

    $scope.submit=function () {
        safeService.submit($scope.entity).success(function (response) {
             alert(response.message)
        });
    };


    $scope.entity = { "phone":""};
    $scope.register = function () {

        if ($scope.entity.phone == "") {
            alert("请输入手机号");
            return;
        }

        safeService.register($scope.entity,$scope.smsCode).success(function (response) {
            alert(response.message);
        });
    };


    $scope.entity = {};
    $scope.sendSmsCode = function () {
        if($scope.entity.phone == null || $scope.entity.phone=="") {
            alert("请输入手机号");
            return;
        }

        safeService.sendSmsCode($scope.entity.phone).success(function (response) {
            alert(response.message);
        });
    };

});