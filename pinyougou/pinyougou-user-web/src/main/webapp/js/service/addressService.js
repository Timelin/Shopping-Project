app.service("addressService", function ($http) {

    this.getUsername = function () {
        return $http.get("address/getUsername.do?t=" + Math.random());
    };

    this.findAddressList = function () {

        return $http.get("address/findAddressList.do");

    };

    this.add = function (entity) {
        return $http.post("address/add.do?",entity);
    };



});