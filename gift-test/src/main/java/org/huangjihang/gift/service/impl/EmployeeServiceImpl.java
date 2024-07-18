package org.huangjihang.gift.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.huangjihang.gift.domain.Employee;
import org.huangjihang.gift.mapper.EmployeeMapper;
import org.huangjihang.gift.service.IEmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements IEmployeeService {

}
