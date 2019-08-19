app.service("contentService", function ($http) {
    this.findContentListByCategoryId = function (categoryId) {
        return $http.get("content/findContentListByCategoryId.do?categoryId="+categoryId);
    };
    this.getUsername = function () {
        return $http.get("user/getUsername.do?r=" + Math.random());
    }
});