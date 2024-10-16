package cn.iocoder.yudao.module.xinlian.service.newscategory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.newscategory.NewsCategoryMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link NewsCategoryServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(NewsCategoryServiceImpl.class)
public class NewsCategoryServiceImplTest extends BaseDbUnitTest {

    @Resource
    private NewsCategoryServiceImpl newsCategoryService;

    @Resource
    private NewsCategoryMapper newsCategoryMapper;

    @Test
    public void testCreateNewsCategory_success() {
        // 准备参数
        NewsCategorySaveReqVO createReqVO = randomPojo(NewsCategorySaveReqVO.class).setId(null);

        // 调用
        Long newsCategoryId = newsCategoryService.createNewsCategory(createReqVO);
        // 断言
        assertNotNull(newsCategoryId);
        // 校验记录的属性是否正确
        NewsCategoryDO newsCategory = newsCategoryMapper.selectById(newsCategoryId);
        assertPojoEquals(createReqVO, newsCategory, "id");
    }

    @Test
    public void testUpdateNewsCategory_success() {
        // mock 数据
        NewsCategoryDO dbNewsCategory = randomPojo(NewsCategoryDO.class);
        newsCategoryMapper.insert(dbNewsCategory);// @Sql: 先插入出一条存在的数据
        // 准备参数
        NewsCategorySaveReqVO updateReqVO = randomPojo(NewsCategorySaveReqVO.class, o -> {
            o.setId(dbNewsCategory.getId()); // 设置更新的 ID
        });

        // 调用
        newsCategoryService.updateNewsCategory(updateReqVO);
        // 校验是否更新正确
        NewsCategoryDO newsCategory = newsCategoryMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, newsCategory);
    }

    @Test
    public void testUpdateNewsCategory_notExists() {
        // 准备参数
        NewsCategorySaveReqVO updateReqVO = randomPojo(NewsCategorySaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> newsCategoryService.updateNewsCategory(updateReqVO), NEWS_CATEGORY_NOT_EXISTS);
    }

    @Test
    public void testDeleteNewsCategory_success() {
        // mock 数据
        NewsCategoryDO dbNewsCategory = randomPojo(NewsCategoryDO.class);
        newsCategoryMapper.insert(dbNewsCategory);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbNewsCategory.getId();

        // 调用
        newsCategoryService.deleteNewsCategory(id);
       // 校验数据不存在了
       assertNull(newsCategoryMapper.selectById(id));
    }

    @Test
    public void testDeleteNewsCategory_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> newsCategoryService.deleteNewsCategory(id), NEWS_CATEGORY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetNewsCategoryList() {
       // mock 数据
       NewsCategoryDO dbNewsCategory = randomPojo(NewsCategoryDO.class, o -> { // 等会查询到
           o.setCreateTime(null);
           o.setIsEnable(null);
           o.setName(null);
       });
       newsCategoryMapper.insert(dbNewsCategory);
       // 测试 createTime 不匹配
       newsCategoryMapper.insert(cloneIgnoreId(dbNewsCategory, o -> o.setCreateTime(null)));
       // 测试 isEnable 不匹配
       newsCategoryMapper.insert(cloneIgnoreId(dbNewsCategory, o -> o.setIsEnable(null)));
       // 测试 name 不匹配
       newsCategoryMapper.insert(cloneIgnoreId(dbNewsCategory, o -> o.setName(null)));
       // 准备参数
       NewsCategoryListReqVO reqVO = new NewsCategoryListReqVO();
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setIsEnable(null);
       reqVO.setName(null);

       // 调用
       List<NewsCategoryDO> list = newsCategoryService.getNewsCategoryList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbNewsCategory, list.get(0));
    }

}