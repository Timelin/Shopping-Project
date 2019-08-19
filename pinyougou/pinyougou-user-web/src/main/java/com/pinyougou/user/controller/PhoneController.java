package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.util.PhoneFormatCheckUtils;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
import com.pinyougou.vo.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.PatternSyntaxException;

@RequestMapping("/phone")
@RestController
public class PhoneController {


    @Reference
    private UserService userService;

    /**
     * 验证验证码
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody TbUser user, String smsCode) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setUsername(username);
        if (PhoneFormatCheckUtils.isPhoneLegal(user.getPhone())) {
            if (userService.checkCodePhone(user.getPhone(), smsCode,user.getUsername())) {
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
