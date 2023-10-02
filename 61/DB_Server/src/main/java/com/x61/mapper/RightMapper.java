package com.x61.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x61.bean.Righttable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RightMapper extends BaseMapper<Righttable> {
    public Long FindRight(@Param("authority") String Authority);
}
