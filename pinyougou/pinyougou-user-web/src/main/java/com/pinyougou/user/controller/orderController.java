package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbAddress;
import com.pinyougou.user.service.AddressService;
import com.pinyougou.vo.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/address")
@RestController
public class orderController {

    @Reference
    private AddressService addressService;

    /**
     * 查询当前登录用户的收件人地址列表
     * @return 收件人地址列表
     */
    @GetMapping("/findAddressList")
    public List<TbAddress> findAddressList(){
        TbAddress param = new TbAddress();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        param.setUserId(userId);
        return addressService.findByWhere(param);
    }


    /**
     * 获取用户名
     *
     * @return
     */
    @GetMapping("/getUsername")
    public Map<String, Object> getUsername() {
        Map<String, Object> map = new HashMap<String, Object>();

        String username =
                SecurityContextHolder.getContext().getAuthentication().getName();

        map.put("username", username);

        return map;
    }


    @PostMapping("/add")
    public Result add(@RequestBody TbAddress address) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        address.setUserId(name);
        try {
            addressService.add(address);
            return Result.ok("增加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("增加失败");
    }

}
