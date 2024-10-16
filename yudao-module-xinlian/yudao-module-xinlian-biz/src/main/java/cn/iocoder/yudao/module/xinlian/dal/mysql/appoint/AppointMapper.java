package cn.iocoder.yudao.module.xinlian.dal.mysql.appoint;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.appoint.AppointDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo.*;

/**
 * 绩效计分 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface AppointMapper extends BaseMapperX<AppointDO> {

    default PageResult<AppointDO> selectPage(AppointPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AppointDO>()
                .eqIfPresent(AppointDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(AppointDO::getAppointIntro, reqVO.getAppointIntro())
                .betweenIfPresent(AppointDO::getAppointDate, reqVO.getAppointDate())
                .eqIfPresent(AppointDO::getAppointScore, reqVO.getAppointScore())
                .eqIfPresent(AppointDO::getAppointArticle, reqVO.getAppointArticle())
                .eqIfPresent(AppointDO::getUserId,reqVO.getUserId())
                .eqIfPresent(AppointDO::getAppointPlan, reqVO.getAppointPlan())
                .eqIfPresent(AppointDO::getState, reqVO.getState())
                .eqIfPresent(AppointDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(AppointDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AppointDO::getId));
    }

}