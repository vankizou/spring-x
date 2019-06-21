package com.zoufanqi.service;

import com.zoufanqi.po.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class UserService {

    /**
     * 模拟查询返回用户信息
     *
     * @return
     */
    public User getUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setNickname("nick_" + username);
        // 密码明文是123456
        user.setPassword("123456");
        // 随机分配一个id
        user.setUserId(new Random().nextLong());
        user.setCreated(new Date());
        user.setUpdated(new Date());
        return user;
    }
}
