package com.randream.bean;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("userrightrelation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Userrightrelation {
    @TableId("uid")
    private long uid;
    @TableField("user_id")
    private long userId;
    @TableField("right_id")
    private long rightId;
    @TableField("state")
    private long state;
    @TableField("right_time")
    private String rightTime;
}
