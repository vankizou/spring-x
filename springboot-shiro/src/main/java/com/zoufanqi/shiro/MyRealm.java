package com.zoufanqi.shiro;

import com.zoufanqi.po.User;
import com.zoufanqi.service.PermService;
import com.zoufanqi.service.RoleService;
import com.zoufanqi.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 登录认证、权限校验操作
 *
 * @author vanki
 * @date 2019-06-19 16:35
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermService permService;

    /**
     * 认证
     *
     * subject.login 登录回调此方法
     *
     * @param authenticationToken
     *
     * @return
     *
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();
        if (username == null) {
            throw new AccountException("用户名不能为空");
        }

        User user = this.userService.getUser(username);
        if (user == null) {
            throw new UnknownAccountException(String.format("用户[%s]不存在", username));
        }

        if (token.getPassword() == null || !new String(token.getPassword()).equals(user.getPassword())) {
            throw new AccountException("密码错误");
        }

        /**
         * 构建用户鉴权数据，在任何地方：
         * SecurityUtils.getSubject().getPrincipal()
         */
        ShiroPrincipal shiroPrincipal = new ShiroPrincipal();
        shiroPrincipal.setUsername(username);
        shiroPrincipal.setRoles(this.roleService.getRoles(user.getUserId()));
        shiroPrincipal.setPerms(this.permService.getPerms(user.getUserId()));
        shiroPrincipal.setLoginDate(new Date());

        return new SimpleAuthenticationInfo(shiroPrincipal, user.getPassword(), this.getName());
    }

    /**
     * 授权
     *
     * 当匹配的路径需要指定[角色(role)]或[权限(permission)]的时候回调此方法获取角色、权限信息
     *
     * @param principalCollection
     *
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroPrincipal shiroPrincipal = (ShiroPrincipal) this.getAvailablePrincipal(principalCollection);

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated() && subject.isRemembered()) {
            /**
             * 通过remember me 自动登录，可主动获取下用户信息，更新用户数据
             */
            User user = this.userService.getUser(shiroPrincipal.getUsername());
            subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword(), true));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(shiroPrincipal.getRoles());
        info.setStringPermissions(shiroPrincipal.getPerms());

        return info;
    }
}
