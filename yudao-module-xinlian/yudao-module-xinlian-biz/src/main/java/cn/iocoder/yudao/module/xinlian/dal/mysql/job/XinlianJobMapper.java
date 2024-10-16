package cn.iocoder.yudao.module.xinlian.dal.mysql.job;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.job.XinlianJobDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.job.vo.*;

import java.util.List;

/**
 * 职务管理 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface XinlianJobMapper extends BaseMapperX<XinlianJobDO> {

    default PageResult<XinlianJobDO> selectPage(JobPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<XinlianJobDO>()
                .eqIfPresent(XinlianJobDO::getIsEnable, reqVO.getIsEnable())
                .betweenIfPresent(XinlianJobDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(XinlianJobDO::getName, reqVO.getName())
                .orderByDesc(XinlianJobDO::getId));
    }

    default List<XinlianJobDO> getJobList() {
        return selectList(new LambdaQueryWrapperX<XinlianJobDO>()
                .eqIfPresent(XinlianJobDO::getIsEnable, true)
                .orderByDesc(XinlianJobDO::getId));
    }
}