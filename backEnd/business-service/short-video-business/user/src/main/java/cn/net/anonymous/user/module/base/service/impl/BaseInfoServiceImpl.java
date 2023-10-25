package cn.net.anonymous.user.module.base.service.impl;

import cn.net.anonymous.user.module.base.entity.BaseInfo;
import cn.net.anonymous.user.module.base.mapper.BaseInfoMapper;
import cn.net.anonymous.user.module.base.service.IBaseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户基本信息服务实现
 *
 * @author anonymous
 * @version 1.0
 */
@Service
public class BaseInfoServiceImpl
        extends ServiceImpl<BaseInfoMapper, BaseInfo>
        implements IBaseInfoService {

}