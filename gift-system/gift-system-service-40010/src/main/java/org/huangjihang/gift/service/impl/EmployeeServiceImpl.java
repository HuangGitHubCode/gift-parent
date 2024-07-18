package org.huangjihang.gift.service.impl;

import org.huangjihang.gift.domain.Employee;
import org.huangjihang.gift.mapper.EmployeeMapper;
import org.huangjihang.gift.service.IEmployeeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjihang
 * @since 2024-07-18
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
