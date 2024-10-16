package cn.iocoder.yudao.module.xinlian.controller.app.newscategory;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.NewsCateSimpleRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.NewsCategoryListReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.NewsCategoryRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.NewsCategorySaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import cn.iocoder.yudao.module.xinlian.service.newscategory.NewsCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 新闻目录")
@RestController
@RequestMapping("/xinlian/news-category")
@Validated
public class AppNewsCategoryController {

    @Resource
    private NewsCategoryService newsCategoryService;



    @GetMapping("/get")
    @Operation(summary = "获得新闻目录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<NewsCategoryRespVO> getNewsCategory(@RequestParam("id") Long id) {
        NewsCategoryDO newsCategory = newsCategoryService.getNewsCategory(id);
        return success(BeanUtils.toBean(newsCategory, NewsCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得新闻目录列表")
    public CommonResult<List<NewsCategoryRespVO>> getNewsCategoryList(@Valid NewsCategoryListReqVO listReqVO) {
        List<NewsCategoryDO> list = newsCategoryService.getNewsCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, NewsCategoryRespVO.class));
    }
    @GetMapping("/simple-list")
    @Operation(summary = "获得精简新闻目录列表")
    public CommonResult<List<NewsCateSimpleRespVO>> getSimpleNewsCategoryList() {

        // 获得岗位列表，只要开启状态的
        List<NewsCategoryDO> list = newsCategoryService.getCateList(null, Collections.singleton(Boolean.TRUE));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(NewsCategoryDO::getSort));
        return success(BeanUtils.toBean(list, NewsCateSimpleRespVO.class));
    }

}