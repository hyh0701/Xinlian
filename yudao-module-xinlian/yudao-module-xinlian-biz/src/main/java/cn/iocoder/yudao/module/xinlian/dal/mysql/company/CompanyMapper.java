package cn.iocoder.yudao.module.xinlian.dal.mysql.company;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.company.CompanyDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.company.vo.*;

/**
 * 企业会员 Mapper
 *
 * @author 平台用户
 */
@Mapper
public interface CompanyMapper extends BaseMapperX<CompanyDO> {

    default PageResult<CompanyDO> selectPage(CompanyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CompanyDO>()
                .likeIfPresent(CompanyDO::getName, reqVO.getName())
                .eqIfPresent(CompanyDO::getOrgId, reqVO.getOrgId())
                .betweenIfPresent(CompanyDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CompanyDO::getId));
    }

}