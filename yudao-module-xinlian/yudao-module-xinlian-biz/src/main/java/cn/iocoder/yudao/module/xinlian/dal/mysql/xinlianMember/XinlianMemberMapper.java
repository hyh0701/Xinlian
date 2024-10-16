package cn.iocoder.yudao.module.xinlian.dal.mysql.xinlianMember;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberExtDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.*;
import org.apache.ibatis.annotations.Select;

/**
 * 新联会员 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface XinlianMemberMapper extends BaseMapperX<XinlianMemberDO> {

    default PageResult<XinlianMemberDO> selectPage(XinlianMemberPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<XinlianMemberDO>()
                .likeIfPresent(XinlianMemberDO::getName, reqVO.getName())
                .eqIfPresent(XinlianMemberDO::getSex, reqVO.getSex())
                .eqIfPresent(XinlianMemberDO::getAvatar, reqVO.getAvatar())
                .eqIfPresent(XinlianMemberDO::getPermitLogin, reqVO.getPermitLogin())
                .eqIfPresent(XinlianMemberDO::getTelephone, reqVO.getTelephone())
                .eqIfPresent(XinlianMemberDO::getMemberType, reqVO.getMemberType())
                .eqIfPresent(XinlianMemberDO::getMainTitle, reqVO.getMainTitle())
                // 修改这一行，使用 inIfPresent 处理 List<Long> 类型的参数
                .inIfPresent(XinlianMemberDO::getMainOrgId, reqVO.getMainOrgId())
                .inIfPresent(XinlianMemberDO::getMainJobId, reqVO.getMainJobId())
                .eqIfPresent(XinlianMemberDO::getState, reqVO.getState())
                .eqIfPresent(XinlianMemberDO::getIsEnable, reqVO.getIsEnable())
                .betweenIfPresent(XinlianMemberDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(XinlianMemberDO::getId));
    }

    default XinlianMemberDO selectByMemberUserId(Long userId){
        return selectOne(new LambdaQueryWrapperX<XinlianMemberDO>()
                .eq(XinlianMemberDO::getMemberUserId,userId));
    }

//    @Select("SELECT m.*, o.name AS orgName " +
//            "FROM xinlian_member m " +
//            "LEFT JOIN xinlian_org o ON m.main_org_id = o.id " +
//            "WHERE m.id IN (${idList})")
    default List<XinlianMemberExtDO> selectByMemberIdList(Set<Long> idList,boolean containSecondOrg){
        return selectJoinList(XinlianMemberExtDO.class, new MPJLambdaWrapper<XinlianMemberDO>()
                .selectAll(XinlianMemberDO.class)
                .selectAs(XinlianOrgDO::getName, XinlianMemberExtDO::getMainOrgName)
                .in(XinlianMemberDO::getId, idList)
                .leftJoin(XinlianOrgDO.class, XinlianOrgDO::getId, XinlianMemberDO::getMainOrgId)
                .or(containSecondOrg)
                .leftJoin(XinlianOrgDO.class,XinlianOrgDO::getId, XinlianMemberDO::getSecondOrgId)
                .isNotNull(XinlianMemberDO::getMemberType)
                .orderByAsc("main_org_id")
        );
    }
    default List<XinlianMemberDO> selectMembersByOrgIds(Set<Long> ordIdList,boolean containSecondOrg) {
        return selectList(new LambdaQueryWrapperX<XinlianMemberDO>()
                .in(XinlianMemberDO::getMainOrgId, ordIdList)
                .or(containSecondOrg)
                .in(containSecondOrg, XinlianMemberDO::getSecondOrgId, ordIdList)
                .isNotNull(XinlianMemberDO::getMemberType)
        );
    }

    default List<XinlianMemberDO> selectMemberByMemberTypes(List<Short> list){
        return selectList(new LambdaQueryWrapperX<XinlianMemberDO>()
                .in(XinlianMemberDO::getMemberType, list)
        );
    }
}