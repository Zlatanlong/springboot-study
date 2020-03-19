package cn.lcl;

import cn.lcl.pojo.User;
import cn.lcl.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb05ShrioApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        User root = userService.queryUserByName("root");

        System.out.println(root);

    }

}
