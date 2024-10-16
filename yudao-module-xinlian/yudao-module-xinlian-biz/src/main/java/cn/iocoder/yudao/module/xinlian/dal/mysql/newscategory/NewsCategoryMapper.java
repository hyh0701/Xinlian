package cn.iocoder.yudao.module.xinlian.dal.mysql.newscategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.*;

/**
 * 新闻目录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface NewsCategoryMapper extends BaseMapperX<NewsCategoryDO> {

    default List<NewsCategoryDO> selectList(Collection<Long> ids, Collection<Boolean> statuses) {
        return selectList(new LambdaQueryWrapperX<NewsCategoryDO>()
                .inIfPresent(NewsCategoryDO::getId, ids)
                .inIfPresent(NewsCategoryDO::getIsEnable, statuses));
    }

    default List<NewsCategoryDO> selectList(NewsCategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<NewsCategoryDO>()
                .betweenIfPresent(NewsCategoryDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(NewsCategoryDO::getIsEnable, reqVO.getIsEnable())
                .likeIfPresent(NewsCategoryDO::getName, reqVO.getName())
                .orderByDesc(NewsCategoryDO::getId));
    }

	default NewsCategoryDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(NewsCategoryDO::getParentId, parentId, NewsCategoryDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(NewsCategoryDO::getParentId, parentId);
    }

}