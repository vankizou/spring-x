package com.zoufanqi.shiro;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * shiro登录信息
 * 如果remember me，会将该如果通过aes加密存储至cookie中
 *
 * @author vanki
 * @date 2019-06-21 15:50
 */
public class ShiroPrincipal implements Serializable {

    private static final long serialVersionUID = 8383300800542483708L;

    private String username;

    private Set<String> roles;

    private Set<String> perms;

    private Date loginDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPerms() {
        return perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
