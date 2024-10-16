package cn.iocoder.yudao.module.xinlian.service.news;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.news.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.news.NewsDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.news.NewsMapper;
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
 * {@link NewsServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(NewsServiceImpl.class)
public class NewsServiceImplTest extends BaseDbUnitTest {

    @Resource
    private NewsServiceImpl newsService;

    @Resource
    private NewsMapper newsMapper;

    @Test
    public void testCreateNews_success() {
        // 准备参数
        NewsSaveReqVO createReqVO = randomPojo(NewsSaveReqVO.class).setId(null);

        // 调用
        Long newsId = newsService.createNews(createReqVO);
        // 断言
        assertNotNull(newsId);
        // 校验记录的属性是否正确
        NewsDO news = newsMapper.selectById(newsId);
        assertPojoEquals(createReqVO, news, "id");
    }

    @Test
    public void testUpdateNews_success() {
        // mock 数据
        NewsDO dbNews = randomPojo(NewsDO.class);
        newsMapper.insert(dbNews);// @Sql: 先插入出一条存在的数据
        // 准备参数
        NewsSaveReqVO updateReqVO = randomPojo(NewsSaveReqVO.class, o -> {
            o.setId(dbNews.getId()); // 设置更新的 ID
        });

        // 调用
        newsService.updateNews(updateReqVO);
        // 校验是否更新正确
        NewsDO news = newsMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, news);
    }

    @Test
    public void testUpdateNews_notExists() {
        // 准备参数
        NewsSaveReqVO updateReqVO = randomPojo(NewsSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> newsService.updateNews(updateReqVO), NEWS_NOT_EXISTS);
    }

    @Test
    public void testDeleteNews_success() {
        // mock 数据
        NewsDO dbNews = randomPojo(NewsDO.class);
        newsMapper.insert(dbNews);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbNews.getId();

        // 调用
        newsService.deleteNews(id);
       // 校验数据不存在了
       assertNull(newsMapper.selectById(id));
    }

    @Test
    public void testDeleteNews_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> newsService.deleteNews(id), NEWS_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetNewsPage() {
       // mock 数据
       NewsDO dbNews = randomPojo(NewsDO.class, o -> { // 等会查询到
           o.setTitle(null);
           o.setIntroduction(null);
           o.setIsEnable(null);
           o.setSource(null);
           o.setCreateTime(null);
       });
       newsMapper.insert(dbNews);
       // 测试 cateId 不匹配
       newsMapper.insert(cloneIgnoreId(dbNews, o -> o.setCateIds(null)));
       // 测试 title 不匹配
       newsMapper.insert(cloneIgnoreId(dbNews, o -> o.setTitle(null)));
       // 测试 introduction 不匹配
       newsMapper.insert(cloneIgnoreId(dbNews, o -> o.setIntroduction(null)));
       // 测试 isEnable 不匹配
       newsMapper.insert(cloneIgnoreId(dbNews, o -> o.setIsEnable(null)));
       // 测试 source 不匹配
       newsMapper.insert(cloneIgnoreId(dbNews, o -> o.setSource(null)));
       // 测试 createTime 不匹配
       newsMapper.insert(cloneIgnoreId(dbNews, o -> o.setCreateTime(null)));
       // 准备参数
       NewsPageReqVO reqVO = new NewsPageReqVO();
       reqVO.setCateIds(null);
       reqVO.setTitle(null);
       reqVO.setIntroduction(null);
       reqVO.setIsEnable(null);
       reqVO.setSource(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<NewsDO> pageResult = newsService.getNewsPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbNews, pageResult.getList().get(0));
    }

}