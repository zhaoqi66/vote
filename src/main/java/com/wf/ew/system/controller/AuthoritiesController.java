package com.wf.ew.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wf.ew.common.BaseController;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.common.utils.ReflectUtil;
import com.wf.ew.system.model.Authorities;
import com.wf.ew.system.service.AuthoritiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(value = "权限管理相关的接口", tags = "authorities")
@RestController
@RequestMapping("/authorities")
public class AuthoritiesController extends BaseController {
    @Autowired
    private AuthoritiesService authoritiesService;

    @ApiOperation(value = "同步权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "权限列表json", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping("/sync")
    public JsonResult add(String json) {
        try {
            List<Authorities> list = new ArrayList<>();
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject paths = jsonObject.getJSONObject("paths");
            Set<String> pathsKeys = paths.keySet();
            for (String pathKey : pathsKeys) {
                JSONObject apiObject = paths.getJSONObject(pathKey);
                Set<String> apiKeys = apiObject.keySet();
                for (String apiKey : apiKeys) {
                    JSONObject methodObject = apiObject.getJSONObject(apiKey);
                    Authorities authorities = new Authorities();
                    authorities.setAuthority(apiKey + ":" + pathKey);
                    authorities.setAuthorityName(methodObject.getString("summary"));
                    list.add(authorities);
                }
            }
            authoritiesService.add(list);
            return JsonResult.ok("同步成功");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("同步失败");
        }
    }

    @ApiOperation(value = "查询所有权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping
    public PageResult<Map<String, Object>> list(String roleId) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<Authorities> authorities = authoritiesService.list();
        List<String> roleAuths = authoritiesService.listByRoleId(roleId);
        for (Authorities one : authorities) {
            Map<String, Object> map = ReflectUtil.objectToMap(one);
            map.put("checked", 0);
            for (String roleAuth : roleAuths) {
                if (one.getAuthority().equals(roleAuth)) {
                    map.put("checked", 1);
                    break;
                }
            }
            maps.add(map);
        }
        return new PageResult<>(maps);
    }

    @ApiOperation(value = "给角色添加权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "authId", value = "权限id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping("/role")
    public JsonResult addRoleAuth(String roleId, String authId) {
        if (authoritiesService.addRoleAuth(roleId, authId)) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    @ApiOperation(value = "移除角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "authId", value = "权限id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @DeleteMapping("/role")
    public JsonResult deleteRoleAuth(String roleId, String authId) {
        if (roleId.equals("admin")) {
            return JsonResult.error("演示系统不能对管理员操作");
        }
        if (authoritiesService.deleteRoleAuth(roleId, authId)) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }
}
