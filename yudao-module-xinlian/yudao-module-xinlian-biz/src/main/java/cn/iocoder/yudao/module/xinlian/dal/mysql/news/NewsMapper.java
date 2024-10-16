package cn.iocoder.yudao.module.xinlian.dal.mysql.news;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.news.NewsDO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.xinlian.controller.admin.news.vo.*;

/**
 * 新闻 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface NewsMapper extends BaseMapperX<NewsDO> {

    default PageResult<NewsDO> selectPage(NewsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NewsDO>()
                .eqIfPresent(NewsDO::getTitle, reqVO.getTitle())
                .likeIfPresent(NewsDO::getIntroduction, reqVO.getIntroduction())
                .eqIfPresent(NewsDO::getIsEnable, reqVO.getIsEnable())
                .eqIfPresent(NewsDO::getSource, reqVO.getSource())
                .betweenIfPresent(NewsDO::getCreateTime, reqVO.getCreateTime())
                .and(reqVO.getCateIds() != null ,s->{
                    if (CollectionUtils.isNotEmpty(Arrays.asList(reqVO.getCateIds()))){
                        for (Long cateId : reqVO.getCateIds()) {
                            s.or().apply("JSON_CONTAINS(cate_ids, JSON_ARRAY({0}))", cateId);
                        }
                    }
                })
                .orderByDesc(NewsDO::getId));
    }
    default PageResult<NewsDO> selectPage(NewsPageReqVO reqVO, Collection<Long> cateIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NewsDO>()
                .eqIfPresent(NewsDO::getTitle, reqVO.getTitle())
                .likeIfPresent(NewsDO::getIntroduction, reqVO.getIntroduction())
                .eqIfPresent(NewsDO::getIsEnable, reqVO.getIsEnable())
                .eqIfPresent(NewsDO::getSource, reqVO.getSource())
                .betweenIfPresent(NewsDO::getCreateTime, reqVO.getCreateTime())
                .inIfPresent(NewsDO::getCateIds, cateIds)
                .orderByDesc(NewsDO::getId));
    }
}