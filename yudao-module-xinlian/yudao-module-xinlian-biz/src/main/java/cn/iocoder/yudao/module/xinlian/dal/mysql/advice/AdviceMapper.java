package cn.iocoder.yudao.module.xinlian.dal.mysql.advice;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.advice.AdviceDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo.*;

/**
 * 咨询记录 Mapper
 *
 * @author 平台用户
 */
@Mapper
public interface AdviceMapper extends BaseMapperX<AdviceDO> {

    default PageResult<AdviceDO> selectPage(AdvicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AdviceDO>()
                .eqIfPresent(AdviceDO::getAdviceTitle, reqVO.getAdviceTitle())
                .eqIfPresent(AdviceDO::getAdviceType, reqVO.getAdviceType())
                .likeIfPresent(AdviceDO::getMemberName, reqVO.getMemberName())
                .likeIfPresent(AdviceDO::getReplyUserName, reqVO.getReplyUserName())
                .betweenIfPresent(AdviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AdviceDO::getId));
    }

    default PageResult<AdviceDO> selectAppPage(AdvicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AdviceDO>()
                .eqIfPresent(AdviceDO::getAdviceTitle, reqVO.getAdviceTitle())
                .eqIfPresent(AdviceDO::getAdviceType, reqVO.getAdviceType())
                .eqIfPresent(AdviceDO::getMemberId,reqVO.getMemberId())
                .likeIfPresent(AdviceDO::getMemberName, reqVO.getMemberName())
                .likeIfPresent(AdviceDO::getReplyUserName, reqVO.getReplyUserName())
                .betweenIfPresent(AdviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AdviceDO::getId));
    }

}