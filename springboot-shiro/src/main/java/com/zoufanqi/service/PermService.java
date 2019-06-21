package com.zoufanqi.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PermService {

    /**
     * 模拟根据用户id查询返回用户的所有权限
     * 权限与角色绑定或用户绑定 结合业务场景使用
     *
     * @param userId
     *
     * @return
     */
    public Set<String> getPerms(Long userId) {
        Set<String> perms = new HashSet<>();

        perms.add("permA");
        perms.add("permB");

        return perms;
    }

}