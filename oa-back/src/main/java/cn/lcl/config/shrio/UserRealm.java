package cn.lcl.config.shrio;

import cn.lcl.pojo.User;
import cn.lcl.service.AuthzService;
import cn.lcl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    AuthzService authzService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行了==>授权doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Subject subject = SecurityUtils.getSubject();

        Long urdId = (Long) subject.getSession().getAttribute("urdId");

        info.addRole(authzService.getRoleInDept(urdId));

        return info;
    }

    /**
     * 认证
     * @param authenticationToken 从上下文中传递的token（可以自定义）
     * @return 一个info对象传递回给shiro
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进行了==>认证doGetAuthenticationInfo");

        // 获取当前用户userToken
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        // user : 根据输入的用户名去查到的用户对象
        User user = userService.queryUserByPhone(userToken.getUsername());

        if (user == null) {
            // 没有这个人，防止下面空指针异常
            return null;
        } else if (user.getState()!=1){
            // 如果未激活也按没找到处理
            throw new NotActiveException();
        }

        // 封装用户登录数据
        // 密码认证由shiro做
        return
                /*
                 * 第一个参数是数据库中查到的用户
                 * 如果认证成功
                 * 可以传递到授权，授权时候就可以看到用户的身份了。
                 */
                new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}