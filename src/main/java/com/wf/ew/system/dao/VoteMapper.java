package com.wf.ew.system.dao;

import com.wf.ew.system.model.Vote;
import com.wf.ew.system.model.VoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoteMapper {
    int countByExample(VoteExample example);

    int deleteByExample(VoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Vote record);

    int insertSelective(Vote record);

    List<Vote> selectByExample(VoteExample example);

    Vote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByExample(@Param("record") Vote record, @Param("example") VoteExample example);

    int updateByPrimaryKeySelective(Vote record);

    int updateByPrimaryKey(Vote record);
}