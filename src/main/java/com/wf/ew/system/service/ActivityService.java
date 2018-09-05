package com.wf.ew.system.service;

import com.wf.ew.common.PageResult;
import com.wf.ew.system.model.Activity;

import java.util.List;

/**
 * @author FHZD.xiaoxun
 * @date 2018/8/29
 */
public interface ActivityService {

    boolean addActivity(Activity activity);

    Activity selectByActicityId(Integer activityId);

    boolean deleteActivity(Activity activity);

    boolean updateActivity(Activity activity);

    PageResult<Activity> listActivity(Integer page, Integer limit);

    List<Activity> selectByActivityStatus();
}
