package org.huangjihang.gift.controller;

import com.baomidou.mybatisplus.plugins.Page;
import org.huangjihang.gift.service.IDepartmentService;
import org.huangjihang.gift.domain.Department;
import org.huangjihang.gift.query.DepartmentQuery;
import org.huangjihang.gift.util.AjaxResult;
import org.huangjihang.gift.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    public IDepartmentService departmentService;


    /**
     * 保存和修改公用的
     * @param department  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Department department){
        try {
            if( department.getId()!=null)
                departmentService.updateById(department);
            else
                departmentService.insert(department);
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
            departmentService.deleteById(id);
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

            Department department = departmentService.selectById(id);
            return AjaxResult.me().setResultObj(department);
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
            List< Department> list = departmentService.selectList(null);
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
    public AjaxResult json(@RequestBody DepartmentQuery query)
    {
        Page<Department> page = new Page<Department>(query.getPage(),query.getRows());
        page = departmentService.selectPage(page);
        PageList pageList = new PageList<Department>(page.getTotal(),page.getRecords());
        return AjaxResult.me().setResultObj(pageList);
    }
}
