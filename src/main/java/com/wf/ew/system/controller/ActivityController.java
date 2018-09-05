package com.wf.ew.system.controller;

import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.model.Activity;
import com.wf.ew.system.service.ActivityService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author FHZD.xiaoxun
 * @date 2018/8/29
 */
@Api(value = "活动相关的接口", tags = "activity")
@Controller
@RequestMapping("/activity")
@RestController
@EnableScheduling
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 添加活动
     */
    @RequestMapping(value = "/addActivity", method = RequestMethod.POST)
    @ApiOperation(value = "添加活动", response = JsonResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "添加活动成功"),
            @ApiResponse(code = 500, message = "添加活动失败")
    }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityName", value = "活动名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "活动开始时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "活动结束时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "pic", value = "活动介绍", required = true, dataType = "String")}
    )
    public JsonResult addActivity(Activity activity) {
        if (StringUtils.isEmpty(activity)) {
            return JsonResult.error("活动信息添加错误，请仔细核对！！！");
        }
        if (StringUtils.isEmpty(activity.getActivityName())) {
            return JsonResult.error("活动名称不能为空！！！");
        }
        if (StringUtils.isEmpty(activity.getPic())) {
            return JsonResult.error("活动内容不能为空！！！");
        }
        if (StringUtils.isEmpty(activity.getStartTime())) {
            return JsonResult.error("活动开始时间不能为空！！！");
        }
        if (StringUtils.isEmpty(activity.getEndTime())) {
            return JsonResult.error("活动结束时间不能为空！！！");
        }

        //查询当前是否有活动在线
        List<Activity> list = activityService.selectByActivityStatus();

        if (!list.isEmpty()) {
            return JsonResult.error("当前有活动正在进行中,无法添加新活动!!!");
        }
        if (activityService.addActivity(activity)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 删除活动
     */
    @RequestMapping(value = "/deleteActivity", method = RequestMethod.POST)
    @ApiOperation(value = "删除活动", response = JsonResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "删除活动成功"),
            @ApiResponse(code = 500, message = "删除活动失败")
    }
    )
    @ApiImplicitParam(name = "activityId",
            value = "删除活动ID",
            dataType = "Integer")
    public JsonResult deleteActivity(Integer activityId) {
        if (StringUtils.isEmpty(activityId)) {
            return JsonResult.error("活动编号不能为空!!!");
        }
        //根据活动ID 查询活动
        Activity activity = activityService.selectByActicityId(activityId);

        if (StringUtils.isEmpty(activity)) {
            return JsonResult.error("没有查询到指定活动");
        }

        if (activityService.deleteActivity(activity)) {
            return JsonResult.ok("删除成功");
        } else {
            return JsonResult.error("删除失败");
        }
    }

    /**
     * 修改活动
     */
    @RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
    @ApiOperation(value = "修改活动", response = JsonResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "修改活动成功"),
            @ApiResponse(code = 500, message = "修改活动失败")
    }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityName", value = "活动名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "活动开始时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "活动结束时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "pic", value = "活动介绍", required = true, dataType = "String")}
    )
    public JsonResult updateActivity(Activity activity) {
        if (StringUtils.isEmpty(activity)) {
            return JsonResult.error("活动信息添加错误，请仔细核对！！！");
        }
        if (StringUtils.isEmpty(activity.getActivityName())) {
            return JsonResult.error("活动名称不能为空！！！");
        }
        if (StringUtils.isEmpty(activity.getPic())) {
            return JsonResult.error("活动内容不能为空！！！");
        }
        if (StringUtils.isEmpty(activity.getStartTime())) {
            return JsonResult.error("活动开始时间不能为空！！！");
        }
        if (StringUtils.isEmpty(activity.getEndTime())) {
            return JsonResult.error("活动结束时间不能为空！！！");
        }


        if (activityService.updateActivity(activity)) {
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.error("修改失败");
        }
    }

    /**
     * 查询所有活动
     */
    @RequestMapping(value = "/listActivity", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有活动", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条", required = true, dataType = "Integer")
    })
    public PageResult<Activity> listActivity(Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
            pageSize = 10;
        }
        return activityService.listActivity(pageNum, pageSize);
    }



    /**
     * 活动过期
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void autoUpdate() {
        //获取当前时间
        Date date = new Date();
        //查询所有未过期的活动
        List<Activity> list = activityService.selectByActivityStatus();
        for (Activity activity : list) {
            if (date.after(activity.getEndTime())) {
                //如果结束时间 已经过了 设置活动过期
                activity.setActivityStatus((byte) 1);
                activityService.updateActivity(activity);
            }
        }
    }

}
