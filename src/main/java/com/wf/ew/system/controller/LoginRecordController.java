package com.wf.ew.system.controller;

import com.wf.ew.common.PageResult;
import com.wf.ew.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.ew.system.model.LoginRecord;
import com.wf.ew.system.service.LoginRecordService;

@Api(value = "登录日志相关的接口", tags = "loginRecord")
@RestController
@RequestMapping("/loginRecord")
public class LoginRecordController {
    @Autowired
    private LoginRecordService loginRecordService;

    @ApiOperation(value = "查询所有登录日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "startDate", value = "开始时间，格式(yyyy-MM-dd)", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期，格式(yyyy-MM-dd)", dataType = "Integer"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping()
    public PageResult<LoginRecord> list(Integer page, Integer limit, String startDate, String endDate, String account) {
        if (StringUtil.isBlank(startDate)) {
            startDate = null;
        } else {
            startDate += " 00:00:00";
        }
        if (StringUtil.isBlank(endDate)) {
            endDate = null;
        } else {
            endDate += " 23:59:59";
        }
        if (StringUtil.isBlank(account)) {
            account = null;
        }
        return loginRecordService.list(page, limit, startDate, endDate, account);
    }

}
