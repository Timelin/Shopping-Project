package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.util.PhoneFormatCheckUtils;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;


import java.util.regex.PatternSyntaxException;

@RequestMapping("/safe")
@RestController
public class safeController {

    @Reference
    private UserService userService;

    /**
     * 更改密码
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody TbUser user){
        String username= user.getUsername();
        String password= user.getPassword();
        try {
            userService.updatePassword(username,password);
            return Result.ok("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("更新失败");
    }


    /**
     * 验证验证码
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody TbUser user, String smsCode) {
        if (PhoneFormatCheckUtils.isPhoneLegal(user.getPhone())) {
            if (userService.checkCode(user.getPhone(), smsCode)) {
                return Result.ok("验证成功");
            } else {
                return Result.fail("验证失败");
            }
        }else {
            return Result.fail("手机号非法");
        }
    }



    /**
     * 发送短信验证码
     * @param phone 手机号
     * @return 发送结果
     */
    @GetMapping("/sendSmsCode")
    public Result sendSmsCode(String phone){
        try {
            if (PhoneFormatCheckUtils.isPhoneLegal(phone)) {
                userService.sendSmsCode(phone);

                return Result.ok("发送验证码成功");
            } else {
                return Result.fail("手机号不合法");
            }
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
        return Result.fail("发送验证码失败");
    }


}
