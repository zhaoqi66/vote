package com.wf.ew.system.service.impl;

import com.wf.ew.common.PageResult;
import com.wf.ew.common.exception.BusinessException;
import com.wf.ew.common.exception.ParameterException;
import com.wf.ew.system.dao.ActivityMapper;
import com.wf.ew.system.model.Activity;
import com.wf.ew.system.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public PageResult<Activity> list(int pageNum, int pageSize, boolean showDelete, String searchKey, String searchValue) {
        return null;
    }

    @Override
    public Activity getById(Integer id) {
        System.out.println(1231);

        return activityMapper.selectByPrimaryKey(id);

    }

    @Override
    public boolean add(Activity user) throws BusinessException {
        return false;
    }

    @Override
    public boolean update(Activity user) {
        return false;
    }

    @Override
    public boolean updateState(String userId, int state) throws ParameterException {
        return false;
    }

    @Override
    public boolean updatePsw(String userId, String newPsw) {
        return false;
    }

    @Override
    public boolean delete(String userId) {
        return false;
    }
}
