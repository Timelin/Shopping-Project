package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.UserMapper;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl extends BaseServiceImpl<TbUser> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ActiveMQQueue itcastSmsQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${signName}")
    private String signName;

    @Value("${templateCode}")
    private String templateCode;

    @Override
    public PageResult search(Integer page, Integer rows, TbUser user) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria();
        /*if(!StringUtils.isEmpty(user.get***())){
            criteria.andLike("***", "%" + user.get***() + "%");
        }*/

        List<TbUser> list = userMapper.selectByExample(example);
        PageInfo<TbUser> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void sendSmsCode(String phone) {
        //1、生成随机数
        String code = (long)( Math.random() * 1000000) + "";
        System.out.println("验证码：" + code);

        //2、存入redis
        redisTemplate.boundValueOps(phone).set(code);
        //设置该值最大生存时间为5分钟
        redisTemplate.boundValueOps(phone).expire(5, TimeUnit.MINUTES);

        //3、将信息发送到mq
        jmsTemplate.send(itcastSmsQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();

                mapMessage.setString("mobile", phone);
                mapMessage.setString("signName", signName);
                mapMessage.setString("templateCode", templateCode);
                mapMessage.setString("templateParam", "{\"code\":" + code + "}");

                return mapMessage;
            }
        });
    }

    @Override
    public boolean checkCode (String phone, String smsCode) {
        String code = (String) redisTemplate.boundValueOps(phone).get();
        if (!StringUtils.isEmpty(code) && code.equals(smsCode)){
        /*if (true){*/

            redisTemplate.delete(phone);

            return true;
        }
        return false;
    }


    @Override
    public TbUser finduser(String infoname) {
        Example example=new Example(TbUser.class);
        example.createCriteria().andEqualTo("username",infoname);

        TbUser tbUser=new TbUser();
        tbUser.setUsername(infoname);
        TbUser tbUser1 = userMapper.selectOne(tbUser);

        return tbUser1;
    }

    @Override
    public TbUser updatePassword(String username, String password) {

        TbUser user=new TbUser();
        user.setUsername(username);
        TbUser user1 = userMapper.selectOne(user);
        user1.setPassword(DigestUtils.md5Hex(password));
        userMapper.updateByPrimaryKeySelective(user1);
        return user1;
    }

    @Override
    public boolean checkCodePhone(String phone, String smsCode, String username) {
        String code = (String) redisTemplate.boundValueOps(phone).get();
        if (!StringUtils.isEmpty(code) && code.equals(smsCode)){

              /*  if(true){*/
             TbUser user=new TbUser();
             user.setUsername(username);
             user=userMapper.selectOne(user);
             user.setPhone(phone);
             userMapper.updateByPrimaryKeySelective(user);


            redisTemplate.delete(phone);

            return true;
        }
        return false;
    }

}
