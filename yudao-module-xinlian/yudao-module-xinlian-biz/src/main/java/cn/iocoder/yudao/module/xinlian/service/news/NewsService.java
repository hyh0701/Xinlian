package cn.iocoder.yudao.module.xinlian.service.news;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.news.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.news.NewsDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 新闻 Service 接口
 *
 * @author 芋道源码
 */
public interface NewsService {

    /**
     * 创建新闻
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createNews(@Valid NewsSaveReqVO createReqVO);

    /**
     * 更新新闻
     *
     * @param updateReqVO 更新信息
     */
    void updateNews(@Valid NewsSaveReqVO updateReqVO);

    /**
     * 删除新闻
     *
     * @param id 编号
     */
    void deleteNews(Long id);

    /**
     * 获得新闻
     *
     * @param id 编号
     * @return 新闻
     */
    NewsDO getNews(Long id);

    /**
     * 获得新闻分页
     *
     * @param pageReqVO 分页查询
     * @return 新闻分页
     */
    PageResult<NewsDO> getNewsPage(NewsPageReqVO pageReqVO);

}