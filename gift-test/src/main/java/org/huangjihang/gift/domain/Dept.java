package org.huangjihang.gift.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("t_department")
public class Dept {
    private Long id;
    private String sn;
    private String name;
    private String dir_path;
    private Integer state;
    private Long manager_id;
    private Long parent_id;
    private Long tenant_id;

}
