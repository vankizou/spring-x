```
springboot 与 权限框架shiro 整合案例
```

### 启动服务
```
ide直接执行 SpringbootShiroApplication
```

### 测试地址

- 登录
```
http://localhost:8080/login/vanki/123456
vanki：用户名
123456：密码
```

- 已主动登录或rememberMe登录 + 需要权限`permA`
```
http://localhost:8080/t/pa
```

- 已主动登录 + 需要权限`permB`
```
http://localhost:8080/t/pb
```

- 更多参数：`com.zoufanqi.shiro.ShiroConfiguration`

