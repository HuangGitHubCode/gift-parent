package org.huangjihang.gift.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangjihang
 * @since 2024-07-18
 */
@TableName("t_meal_permission")
public class MealPermission extends Model<MealPermission> {

    private static final long serialVersionUID = 1L;

    @TableField("meal_id")
    private Long mealId;
    @TableField("permission_id")
    private Long permissionId;
    private Integer id;


    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MealPermission{" +
        ", mealId=" + mealId +
        ", permissionId=" + permissionId +
        ", id=" + id +
        "}";
    }
}
