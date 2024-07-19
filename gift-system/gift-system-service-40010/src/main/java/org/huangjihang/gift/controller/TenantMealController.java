package org.huangjihang.gift.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.huangjihang.gift.service.ITenantMealService;
import org.huangjihang.gift.domain.TenantMeal;
import org.huangjihang.gift.query.TenantMealQuery;
import org.huangjihang.gift.util.AjaxResult;
import org.huangjihang.gift.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenantMeal")
public class TenantMealController {
    @Autowired
    public ITenantMealService tenantMealService;


    /**
     * 保存和修改公用的
     * @param tenantMeal  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody TenantMeal tenantMeal){
        try {
            if( tenantMeal.getId()!=null)
                tenantMealService.updateById(tenantMeal);
            else
                tenantMealService.insert(tenantMeal);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }
    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            tenantMealService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public AjaxResult get(@PathVariable("id")Long id)
    {
        try {

            TenantMeal tenantMeal = tenantMealService.selectById(id);
            return AjaxResult.me().setResultObj(tenantMeal);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取一个失败！"+e.getMessage());
        }
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping
    public AjaxResult list(){

        try {
            List< TenantMeal> list = tenantMealService.selectList(null);
            return AjaxResult.me().setResultObj(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取所有失败！"+e.getMessage());
        }
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */

    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public AjaxResult json(@RequestBody TenantMealQuery query)
    {
        Page<TenantMeal> page = new Page<TenantMeal>(query.getPage(),query.getRows());
        page = tenantMealService.selectPage(page);
        PageList pageList = new PageList<TenantMeal>(page.getTotal(),page.getRecords());
        return AjaxResult.me().setResultObj(pageList);
    }
}
