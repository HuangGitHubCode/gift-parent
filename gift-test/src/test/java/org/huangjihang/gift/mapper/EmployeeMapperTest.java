package org.huangjihang.gift.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.huangjihang.gift.domain.Employee;
import org.huangjihang.gift.service.IEmployeeService;
import org.huangjihang.gift.service.impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeMapperTest {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private IEmployeeService employeeService;
    @Test
    public void testSelect(){
        List<Employee> employeeList = employeeMapper.selectList(null);
        System.out.println(employeeList);
    }
    @Test
    public void testSelect2(){
        EntityWrapper<Employee> wrapper = new EntityWrapper<>();
        List<Employee> employeeList = employeeMapper.selectList(wrapper);
        wrapper.eq("username","yaosang").and().eq("password","1");
        System.out.println(employeeList);
    }
    @Test
    public void testSelect3(){
        Page<Employee> page = new Page<>();
        EntityWrapper<Employee> wrapper = new EntityWrapper<>();
        wrapper.eq("username","yaosang").and().eq("password","1");
        employeeService.selectPage(page,wrapper);
        System.out.println(page.getRecords());
        System.out.println(page.getTotal());
    }
}
