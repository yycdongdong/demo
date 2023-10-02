package com.randream.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.randream.bean.Righttable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RightMapper extends BaseMapper<Righttable> {
    public Long FindRight(@Param("authority") String Authority);
}
