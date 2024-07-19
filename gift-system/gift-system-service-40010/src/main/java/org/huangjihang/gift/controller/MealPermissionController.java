package org.huangjihang.gift.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.huangjihang.gift.service.IMealPermissionService;
import org.huangjihang.gift.domain.MealPermission;
import org.huangjihang.gift.query.MealPermissionQuery;
import org.huangjihang.gift.util.AjaxResult;
import org.huangjihang.gift.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mealPermission")
public class MealPermissionController {
    @Autowired
    public IMealPermissionService mealPermissionService;


    /**
     * 保存和修改公用的
     * @param mealPermission  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody MealPermission mealPermission){
        try {
            if( mealPermission.getId()!=null)
                mealPermissionService.updateById(mealPermission);
            else
                mealPermissionService.insert(mealPermission);
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
            mealPermissionService.deleteById(id);
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

            MealPermission mealPermission = mealPermissionService.selectById(id);
            return AjaxResult.me().setResultObj(mealPermission);
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
            List< MealPermission> list = mealPermissionService.selectList(null);
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
    public AjaxResult json(@RequestBody MealPermissionQuery query)
    {
        Page<MealPermission> page = new Page<MealPermission>(query.getPage(),query.getRows());
        page = mealPermissionService.selectPage(page);
        PageList pageList = new PageList<MealPermission>(page.getTotal(),page.getRecords());
        return AjaxResult.me().setResultObj(pageList);
    }
}
