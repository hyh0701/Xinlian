package cn.iocoder.yudao.module.xinlian.dal.mysql.org;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.controller.app.org.vo.AppXinlianOrgListReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.*;

/**
 * 组织架构管理 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface XinlianOrgMapper extends BaseMapperX<XinlianOrgDO> {

    default List<XinlianOrgDO> selectList(XinlianOrgListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<XinlianOrgDO>()
                .likeIfPresent(XinlianOrgDO::getName, reqVO.getName())
                .eqIfPresent(XinlianOrgDO::getIsEnable, reqVO.getIsEnable())
                .eqIfPresent(XinlianOrgDO::getParentId, reqVO.getParentId())
                .eqIfPresent(XinlianOrgDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(XinlianOrgDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(XinlianOrgDO::getId));
    }
    default List<XinlianOrgDO> selectApptList() {
        return selectList(new LambdaQueryWrapperX<XinlianOrgDO>()
                .eqIfPresent(XinlianOrgDO::getIsEnable, true)
                .orderByDesc(XinlianOrgDO::getId));
    }

    default List<XinlianOrgDO> selectOrgListByParentId(Long parentId) {
        return selectList(new LambdaQueryWrapperX<XinlianOrgDO>()
                .eqIfPresent(XinlianOrgDO::getIsEnable, true)
                .eq(XinlianOrgDO::getParentId,parentId)
                .orderByDesc(XinlianOrgDO::getId));
    }
    default List<XinlianOrgDO> selectTwoApptList() {
        return selectList(new LambdaQueryWrapperX<XinlianOrgDO>()
                .eqIfPresent(XinlianOrgDO::getIsEnable, true)
                .ne(XinlianOrgDO::getParentId,0)
                .orderByDesc(XinlianOrgDO::getId));
    }

	default XinlianOrgDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(XinlianOrgDO::getParentId, parentId, XinlianOrgDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(XinlianOrgDO::getParentId, parentId);
    }

}