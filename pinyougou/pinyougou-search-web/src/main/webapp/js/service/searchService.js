app.service("searchService", function ($http) {

    this.search = function (searchMap) {

        return $http.post("itemSearch/search.do", searchMap);
    };
    this.getUsername = function () {
        return $http.get("user/getUsername.do?r=" + Math.random());
    }
});
