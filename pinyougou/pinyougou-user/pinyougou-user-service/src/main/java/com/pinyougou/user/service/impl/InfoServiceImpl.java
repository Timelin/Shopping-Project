package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.user.service.InfoService;

@Service(interfaceClass = InfoService.class)
public class InfoServiceImpl extends BaseServiceImpl<TbUser>implements InfoService {

}
