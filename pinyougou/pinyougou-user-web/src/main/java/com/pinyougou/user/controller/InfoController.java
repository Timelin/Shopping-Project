package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
import com.pinyougou.vo.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/info")
@RestController
public class InfoController {


    @Reference
    private UserService userService;

    /**
     * 注册个人信息
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody TbUser user){

        String infoname = SecurityContextHolder.getContext().getAuthentication().getName();
        TbUser one = userService.finduser(infoname);
        one.setNickName(user.getNickName());
        one.setSex(user.getSex());
        one.setBirthday(user.getBirthday());
        try {
           userService.update(one);
            return Result.ok("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("注册失败");
    }

}
