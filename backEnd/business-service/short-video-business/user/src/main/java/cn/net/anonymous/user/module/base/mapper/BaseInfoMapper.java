package cn.net.anonymous.user.module.base.mapper;

import cn.net.anonymous.user.module.base.entity.BaseInfo;
import common.mapper.CustomerMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户基本信息数据接口
 *
 * @author anonymous
 * @version 1.0
 */
@Repository
public interface BaseInfoMapper
        extends CustomerMapper<BaseInfo> {

}