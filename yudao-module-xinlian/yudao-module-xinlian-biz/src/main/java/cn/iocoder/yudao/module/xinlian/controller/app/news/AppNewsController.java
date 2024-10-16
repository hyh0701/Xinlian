package cn.iocoder.yudao.module.xinlian.controller.app.news;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.xinlian.controller.admin.news.vo.NewsPageReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.news.vo.NewsRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.news.vo.NewsSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.news.NewsDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import cn.iocoder.yudao.module.xinlian.service.news.NewsService;
import cn.iocoder.yudao.module.xinlian.service.newscategory.NewsCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 新闻")
@RestController
@RequestMapping("/xinlian/news")
@Validated
public class AppNewsController {

    @Resource
    private NewsService newsService;
    @Resource
    NewsCategoryService categoryService;

    @GetMapping("/get")
    @Operation(summary = "获得新闻")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<NewsRespVO> getNews(@RequestParam("id") Long id) {
        NewsDO news = newsService.getNews(id);
        return success(BeanUtils.toBean(news, NewsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得新闻分页")
    public CommonResult<PageResult<NewsRespVO>> getNewsPage(@Valid NewsPageReqVO pageReqVO) {
        PageResult<NewsDO> pageResult = newsService.getNewsPage(pageReqVO);
        PageResult<NewsRespVO> newsDOPageResult = BeanUtils.toBean(pageResult, NewsRespVO.class);
        NewsCategoryDO categoryDO = categoryService.getNewsCategory(pageReqVO.getCateIds()[0]);
        if (newsDOPageResult != null){
            if(CollectionUtils.isNotEmpty(newsDOPageResult.getList())){
                List<NewsRespVO> list = newsDOPageResult.getList();
                list.stream().forEach(s->{
                    s.setCateName(categoryDO.getName());
                });
                newsDOPageResult.setList(list);
            }
        }
        return success(newsDOPageResult);
    }

}