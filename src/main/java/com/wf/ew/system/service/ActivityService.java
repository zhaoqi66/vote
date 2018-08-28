package com.wf.ew.system.service;

import com.wf.ew.common.PageResult;
import com.wf.ew.common.exception.BusinessException;
import com.wf.ew.common.exception.ParameterException;
import com.wf.ew.system.model.Activity;

public interface ActivityService {



    PageResult<Activity> list(int pageNum, int pageSize, boolean showDelete, String searchKey, String searchValue);

    Activity getById(Integer id);

    boolean add(Activity user) throws BusinessException;

    boolean update(Activity user);

    boolean updateState(String userId, int state) throws ParameterException;

    boolean updatePsw(String userId, String newPsw);

    boolean delete(String userId);

}
