package com.wf.ew.system.controller;


import com.wf.ew.common.BaseController;
import com.wf.ew.system.model.Activity;
import com.wf.ew.system.model.User;
import com.wf.ew.system.service.ActivityService;
import com.wf.ew.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "活动", tags = "activity")
@RestController
@RequestMapping("/activity")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityService activityService;

    @ApiOperation(value = "查询活动", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping()
    public Activity getActivityById(Integer id) {

        return activityService.getById(id);
    }
}
