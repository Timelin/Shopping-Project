app.service("infoService", function($http){

    this.register = function (entity) {
        return $http.post("info/register.do",entity);
    };
});

