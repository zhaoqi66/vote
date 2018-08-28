package com.wf.ew.system.controller;

import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.common.utils.ReflectUtil;
import com.wf.ew.system.model.Role;
import com.wf.ew.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Api(value = "角色相关的接口", tags = "role")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询所有角色")
    @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    @GetMapping()
    public PageResult<Role> list(String keyword) {
        List<Role> list = roleService.list(false);
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Role> iterator = list.iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                boolean b = next.getRoleId().contains(keyword) || next.getRoleName().contains(keyword) || next.getComments().contains(keyword);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return new PageResult<>(list);
    }

    @ApiOperation(value = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "Role"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping()
    public JsonResult add(Role role) {
        if (roleService.add(role)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "Role"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping()
    public JsonResult update(Role role) {
        if (roleService.update(role)) {
            return JsonResult.ok("修改成功！");
        } else {
            return JsonResult.error("修改失败！");
        }
    }

    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") String roleId) {
        if (true) {
            return JsonResult.error("演示系统关闭该功能");
        }
        if (roleService.updateState(roleId, 1)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }
}
