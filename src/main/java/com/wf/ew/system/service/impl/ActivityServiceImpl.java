package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.dao.ActivityMapper;
import com.wf.ew.system.model.Activity;
import com.wf.ew.system.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author FHZD.xiaoxun
 * @date 2018/8/29
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 添加活动
     */
    @Override
    public boolean addActivity(Activity activity) {
        //设置活动创建时间
        activity.setCreateTime(new Date());
        //设置活动状态0活动报名中 1活动已结束
        activity.setActivityStatus((byte) 0);
        //活动参与人数    默认为0
        activity.setActivityTakeCount(0);
        //设置删除标记    默认为1正常
        activity.setDeleteFlag((byte) 1);

        int insert = activityMapper.insert(activity);
        if (insert > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据活动ID查询活动
     */
    @Override
    public Activity selectByActicityId(Integer activityId) {
        Activity activity = activityMapper.selectById(activityId);
        return activity;
    }

    /**
     * 删除活动
     */
    @Override
    public boolean deleteActivity(Activity activity) {
        //设置删除标记    0删除
        activity.setDeleteFlag((byte) 0);
        Integer i = activityMapper.updateById(activity);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新活动
     */
    @Override
    public boolean updateActivity(Activity activity) {
        activity.setModifiedTime(new Date());
        Integer i = activityMapper.updateById(activity);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询所有活动
     */
    @Override
    public PageResult<Activity> listActivity(Integer pageNum, Integer pageSize) {
        Page<Activity> activityPage = new Page<Activity>(pageNum, pageSize);

        EntityWrapper<Activity> wrapper = new EntityWrapper<>();
        wrapper.eq("delete_flag", 1);
        wrapper.orderBy("activity_status", true);

        List<Activity> list = activityMapper.selectPage(activityPage, wrapper);
        return new PageResult<>(activityPage.getTotal(), list);

    }

    @Override
    public List<Activity> selectByActivityStatus() {
        EntityWrapper<Activity> wrapper = new EntityWrapper<>();
        //活动未被删除    活动进行中
        Wrapper<Activity> eq = wrapper.eq("delete_flag", 1).eq("activity_status", 0);
        List<Activity> list = activityMapper.selectList(eq);
        return list;
    }


}
