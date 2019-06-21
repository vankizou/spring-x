package com.zoufanqi.controller;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * shiro测试访问地址
 *
 * @author vanki
 * @date 2019-06-19 17:26
 */
@RestController
@RequestMapping("/")
public class TestController {

    /**
     * 动态两层路径访问地址，可结合 ShiroConfiguration.shiroFilterFactoryBean 进行测试
     *
     * @param a
     * @param b
     *
     * @return
     */
    @RequestMapping("/{a}/{b}")
    public String test(@PathVariable("a") String a, @PathVariable("b") String b) {
        Subject subject = SecurityUtils.getSubject();
        System.out.println();
        System.out.println("previousPrincipals: " + JSON.toJSONString(subject.getPreviousPrincipals()));
        System.out.println("principal: " + JSON.toJSONString(subject.getPrincipal()));
        System.out.println("principals: " + JSON.toJSONString(subject.getPrincipals()));
        System.out.println("session: " + JSON.toJSONString(subject.getSession()));
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        System.out.println("isRemembered: " + subject.isRemembered());
        return String.format("请求路径：/%s/%s", a, b);
    }

    /**
     * 登录地址
     *
     * @param username
     * @param password
     *
     * @return
     */
    @RequestMapping("/login/{username}/{password}")
    public String login(@PathVariable("username") String username, @PathVariable("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        subject.login(token);
        return "登录成功";
    }

}
