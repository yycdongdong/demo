package com.x61.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("righttable")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Righttable {
    @TableId("right_id")
    private long right_id;
    @TableField("right_parent_id")
    private long rightParentId;
    @TableField("right_description")
    private String rightDescription;
    @TableField("right_name")
    private String rightName;
    @TableField("state")
    private long state;

}
