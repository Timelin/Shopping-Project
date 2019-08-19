app.controller("infoController",function($scope, $controller, infoService) {

    $scope.entity = {"nickName": "", "sex": "", "year": "", "month": "", "day": "", "birthday": null, "headPic":null};

       $scope.register = function () {
           var pic = $("#up_img_WU_FILE_0").attr("src");
           $scope.entity.headPic = pic;
           var birthday = new Date();
           birthday.setFullYear($scope.entity.year);
           birthday.setMonth($scope.entity.month - 1);
           birthday.setDate($scope.entity.day);
           $scope.entity.birthday = birthday;
           infoService.register($scope.entity).success(function (response) {
               $scope.entity = response.entity;
               alert(response.message);
               location.href = "home-setting-info.html";
           });

       };

});