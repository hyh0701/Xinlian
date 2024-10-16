package cn.iocoder.yudao.module.xinlian.dal.mysql.activityappoint;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointExtDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberExtDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo.*;

/**
 * 活动报名 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ActivityAppointMapper extends BaseMapperX<ActivityAppointDO> {

    default PageResult<ActivityAppointDO> selectPage(ActivityAppointPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ActivityAppointDO>()
                .eqIfPresent(ActivityAppointDO::getActivityId, reqVO.getActivityId())
                .likeIfPresent(ActivityAppointDO::getMemberName, reqVO.getMemberName())
                .eqIfPresent(ActivityAppointDO::getContact, reqVO.getContact())
                .eqIfPresent(ActivityAppointDO::getMemberId,reqVO.getMemberId())
                .eqIfPresent(ActivityAppointDO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(ActivityAppointDO::getState, reqVO.getState())
                .eqIfPresent(ActivityAppointDO::getPayState, reqVO.getPayState())
                .eqIfPresent(ActivityAppointDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ActivityAppointDO::getOffice,reqVO.getOffice())
                .betweenIfPresent(ActivityAppointDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ActivityAppointDO::getId));
    }

    default List<ActivityAppointExtDO> getActivityAppointPageWithAct(ActivityAppointPageReqVO reqVO){
        return selectJoinList(ActivityAppointExtDO.class, new MPJLambdaWrapper<ActivityAppointDO>()
                .selectAll(ActivityAppointDO.class)
                .selectAssociation(XinlianActivityDO.class, ActivityAppointExtDO::getActivity)
                //.eq(XinlianActivityDO::getId, reqVO.getActivityId())
                .leftJoin(XinlianActivityDO.class, XinlianActivityDO::getId, ActivityAppointDO::getActivityId)
                .eq(ActivityAppointDO::getMemberId,reqVO.getMemberId())
        );
    }
}