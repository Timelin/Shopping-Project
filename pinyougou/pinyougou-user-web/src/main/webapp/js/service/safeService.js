app.service("safeService",function ($http) {

    this.submit=function (entity) {
        return $http.post("safe/submit.do",entity);
    };

    this.register = function (entity, smsCode) {
        return $http.post("safe/register.do?smsCode=" + smsCode, entity);

    };

    this.sendSmsCode = function (phone) {
        return $http.get("safe/sendSmsCode.do?phone=" +phone+"&r=" + Math.random());
    };


});