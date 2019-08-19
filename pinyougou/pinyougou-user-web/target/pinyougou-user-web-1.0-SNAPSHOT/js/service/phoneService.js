app.service("phoneService",function ($http) {

    this.register = function (entity, smsCode) {
        return $http.post("phone/register.do?smsCode=" + smsCode, entity);

    };

    this.sendSmsCode = function (phone) {
        return $http.get("phone/sendSmsCode.do?phone=" +phone+"&r=" + Math.random());
    };

});