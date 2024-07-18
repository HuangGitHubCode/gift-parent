package org.huangjihang.gift.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;
@TableName("t_employee")
@Data
public class Employee {
    private  Long id;
    private  String username;
    private  String password;
    private  String real_name;
    private  String tel;
    private  String email;
    private Date input_time;
    private  Integer state;
    private  Long dept_id;
    private  Long tenant_id;
    private  Long type;
    @TableField(exist = false)
    private Dept dept;

}
