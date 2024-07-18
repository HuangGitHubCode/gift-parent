package org.huangjihang.gift.service.impl;

import org.huangjihang.gift.domain.Config;
import org.huangjihang.gift.mapper.ConfigMapper;
import org.huangjihang.gift.service.IConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 参数配置表 服务实现类
 * </p>
 *
 * @author huangjihang
 * @since 2024-07-18
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
