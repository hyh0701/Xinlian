package cn.iocoder.yudao.module.xinlian.service.newscategory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.lang.Nullable;

/**
 * 新闻目录 Service 接口
 *
 * @author 芋道源码
 */
public interface NewsCategoryService {

    /**
     * 创建新闻目录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createNewsCategory(@Valid NewsCategorySaveReqVO createReqVO);

    /**
     * 更新新闻目录
     *
     * @param updateReqVO 更新信息
     */
    void updateNewsCategory(@Valid NewsCategorySaveReqVO updateReqVO);

    /**
     * 删除新闻目录
     *
     * @param id 编号
     */
    void deleteNewsCategory(Long id);

    /**
     * 获得新闻目录
     *
     * @param id 编号
     * @return 新闻目录
     */
    NewsCategoryDO getNewsCategory(Long id);

    /**
     * 获得新闻目录列表
     *
     * @param listReqVO 查询条件
     * @return 新闻目录列表
     */
    List<NewsCategoryDO> getNewsCategoryList(NewsCategoryListReqVO listReqVO);

    /**
     * 获得符合条件的岗位列表
     *
     * @param ids 岗位编号数组。如果为空，不进行筛选
     * @param statuses 状态数组。如果为空，不进行筛选
     * @return 部门列表
     */
    List<NewsCategoryDO> getCateList(@Nullable Collection<Long> ids,
                             @Nullable Collection<Boolean> statuses);
}