package cn.iocoder.yudao.module.xinlian.service.newscategory;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.newscategory.NewsCategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 新闻目录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class NewsCategoryServiceImpl implements NewsCategoryService {

    @Resource
    private NewsCategoryMapper newsCategoryMapper;

    @Override
    public Long createNewsCategory(NewsCategorySaveReqVO createReqVO) {
        // 校验父ID的有效性
        validateParentNewsCategory(null, createReqVO.getParentId());
        // 校验分类名字的唯一性
        validateNewsCategoryNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        NewsCategoryDO newsCategory = BeanUtils.toBean(createReqVO, NewsCategoryDO.class);
        newsCategoryMapper.insert(newsCategory);
        // 返回
        return newsCategory.getId();
    }

    @Override
    public void updateNewsCategory(NewsCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateNewsCategoryExists(updateReqVO.getId());
        // 校验父ID的有效性
        validateParentNewsCategory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验分类名字的唯一性
        validateNewsCategoryNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        NewsCategoryDO updateObj = BeanUtils.toBean(updateReqVO, NewsCategoryDO.class);
        newsCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteNewsCategory(Long id) {
        // 校验存在
        validateNewsCategoryExists(id);
        // 校验是否有子新闻目录
        if (newsCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(NEWS_CATEGORY_EXITS_CHILDREN);
        }
        // 删除
        newsCategoryMapper.deleteById(id);
    }

    private void validateNewsCategoryExists(Long id) {
        if (newsCategoryMapper.selectById(id) == null) {
            throw exception(NEWS_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentNewsCategory(Long id, Long parentId) {
        if (parentId == null || NewsCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父新闻目录
        if (Objects.equals(id, parentId)) {
            throw exception(NEWS_CATEGORY_PARENT_ERROR);
        }
        // 2. 父新闻目录不存在
        NewsCategoryDO parentNewsCategory = newsCategoryMapper.selectById(parentId);
        if (parentNewsCategory == null) {
            throw exception(NEWS_CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父新闻目录，如果父新闻目录是自己的子新闻目录，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentNewsCategory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(NEWS_CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父新闻目录
            if (parentId == null || NewsCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentNewsCategory = newsCategoryMapper.selectById(parentId);
            if (parentNewsCategory == null) {
                break;
            }
        }
    }

    private void validateNewsCategoryNameUnique(Long id, Long parentId, String name) {
        NewsCategoryDO newsCategory = newsCategoryMapper.selectByParentIdAndName(parentId, name);
        if (newsCategory == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的新闻目录
        if (id == null) {
            throw exception(NEWS_CATEGORY_CATE_NAME_DUPLICATE);
        }
        if (!Objects.equals(newsCategory.getId(), id)) {
            throw exception(NEWS_CATEGORY_CATE_NAME_DUPLICATE);
        }
    }

    @Override
    public NewsCategoryDO getNewsCategory(Long id) {
        return newsCategoryMapper.selectById(id);
    }

    @Override
    public List<NewsCategoryDO> getNewsCategoryList(NewsCategoryListReqVO listReqVO) {
        return newsCategoryMapper.selectList(listReqVO);
    }
    @Override
    public List<NewsCategoryDO> getCateList(Collection<Long> ids, Collection<Boolean> statuses) {
        return newsCategoryMapper.selectList(ids, statuses);
    }
}