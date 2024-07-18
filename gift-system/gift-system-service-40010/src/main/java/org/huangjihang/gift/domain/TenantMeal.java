package org.huangjihang.gift.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
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
@TableName("t_tenant_meal")
public class TenantMeal extends Model<TenantMeal> {

    private static final long serialVersionUID = 1L;

    @TableField("meal_id")
    private Long mealId;
    @TableField("tenant_id")
    private Long tenantId;
    /**
     * 该机构的该套餐到期时间
     */
    @TableField("expire_date")
    private Date expireDate;
    /**
     * 状态,是否过期 0 未支付，1已经购买，2过期
     */
    private Integer state;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
        return "TenantMeal{" +
        ", mealId=" + mealId +
        ", tenantId=" + tenantId +
        ", expireDate=" + expireDate +
        ", state=" + state +
        ", id=" + id +
        "}";
    }
}
