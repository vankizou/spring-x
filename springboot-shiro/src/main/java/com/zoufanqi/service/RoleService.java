package com.zoufanqi.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    /**
     * 模拟根据用户id查询返回用户的所有角色
     *
     * @param userId
     *
     * @return
     */
    public Set<String> getRoles(Long userId) {
        Set<String> roles = new HashSet<>();

        roles.add("roleA");
        roles.add("roleB");

        return roles;
    }

}