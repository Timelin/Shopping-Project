app.controller("orderInfoController", function ($scope, addressService,userService) {


    $scope.getUsername = function () {
        userService.getUsername().success(function (response) {
            $scope.username = response.username;
        });
    };

    //加载地址列表
    $scope.findAddressList = function () {
        addressService.findAddressList().success(function (response) {

            $scope.addressList = response;

            for (var i = 0; i < response.length; i++) {
                var address = response[i];
                if(address.isDefault=="1"){
                    $scope.address = address;
                    break;
                }
            }

        });

    };

    //选择地址
    $scope.selectAddress = function (address) {
        $scope.address = address;
    };

    //判断当前地址是否是选择了的那个地址
    $scope.isSelectedAddress = function (address) {
        if($scope.address == address){
            return true;
        }

        return false;
    };


    $scope.add =function () {
        addressService.add($scope.entity).success(function (response) {
            if (response.success) {
                alert(response.message);
                $scope.findAddressList();
            }else {
                alert(response.message);
            }
        });
    };


    $scope.delete = function () {
        addressService.delete($scope.entity.id).success(function (response) {
             if($scope.entity.id!==""){

             }
        });
    };

});