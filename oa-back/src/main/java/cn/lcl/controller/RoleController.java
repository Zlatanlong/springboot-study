package cn.lcl.controller;

import cn.lcl.pojo.Role;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping
    public Result add(@RequestBody Role role) {
        return roleService.add(role);
    }
}
