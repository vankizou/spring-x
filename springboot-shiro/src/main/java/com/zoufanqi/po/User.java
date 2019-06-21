package com.zoufanqi.po;

import java.util.Date;

/**
 * @author vanki
 */
public class User {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录名，不可改
     */
    private String username;

    /**
     * 用户昵称，可改
     */
    private String nickname;

    /**
     * 已加密的登录密码
     */
    private String password;

    private Date created;

    private Date updated;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}