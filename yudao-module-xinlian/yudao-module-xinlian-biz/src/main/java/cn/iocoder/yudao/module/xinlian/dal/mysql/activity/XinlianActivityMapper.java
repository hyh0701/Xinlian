package cn.iocoder.yudao.module.xinlian.dal.mysql.activity;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.*;
import org.apache.ibatis.annotations.Select;

/**
 * 活动记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface XinlianActivityMapper extends BaseMapperX<XinlianActivityDO> {

    default PageResult<XinlianActivityDO> selectPage(XinlianActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<XinlianActivityDO>()
                .likeIfPresent(XinlianActivityDO::getActivityName, reqVO.getActivityName())
                .eqIfPresent(XinlianActivityDO::getSponsor, reqVO.getSponsor())
                .eqIfPresent(XinlianActivityDO::getActivityType, reqVO.getActivityType())
                .betweenIfPresent(XinlianActivityDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(XinlianActivityDO::getIsLimit, reqVO.getIsLimit())
                .eqIfPresent(XinlianActivityDO::getContact, reqVO.getContact())
                .eqIfPresent(XinlianActivityDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(XinlianActivityDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(XinlianActivityDO::getId));
    }


}