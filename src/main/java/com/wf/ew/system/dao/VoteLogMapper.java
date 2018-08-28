package com.wf.ew.system.dao;

import com.wf.ew.system.model.VoteLog;
import com.wf.ew.system.model.VoteLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoteLogMapper {
    int countByExample(VoteLogExample example);

    int deleteByExample(VoteLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VoteLog record);

    int insertSelective(VoteLog record);

    List<VoteLog> selectByExample(VoteLogExample example);

    VoteLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VoteLog record, @Param("example") VoteLogExample example);

    int updateByExample(@Param("record") VoteLog record, @Param("example") VoteLogExample example);

    int updateByPrimaryKeySelective(VoteLog record);

    int updateByPrimaryKey(VoteLog record);
}