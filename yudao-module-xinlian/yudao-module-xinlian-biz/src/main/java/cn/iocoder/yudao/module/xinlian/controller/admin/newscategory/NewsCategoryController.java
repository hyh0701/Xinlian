package cn.iocoder.yudao.module.xinlian.controller.admin.newscategory;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import cn.iocoder.yudao.module.xinlian.service.newscategory.NewsCategoryService;

@Tag(name = "管理后台 - 新闻目录")
@RestController
@RequestMapping("/xinlian/news-category")
@Validated
public class NewsCategoryController {

    @Resource
    private NewsCategoryService newsCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建新闻目录")
    @PreAuthorize("@ss.hasPermission('xinlian:news-category:create')")
    public CommonResult<Long> createNewsCategory(@Valid @RequestBody NewsCategorySaveReqVO createReqVO) {
        return success(newsCategoryService.createNewsCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新新闻目录")
    @PreAuthorize("@ss.hasPermission('xinlian:news-category:update')")
    public CommonResult<Boolean> updateNewsCategory(@Valid @RequestBody NewsCategorySaveReqVO updateReqVO) {
        newsCategoryService.updateNewsCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除新闻目录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:news-category:delete')")
    public CommonResult<Boolean> deleteNewsCategory(@RequestParam("id") Long id) {
        newsCategoryService.deleteNewsCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得新闻目录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:news-category:query')")
    public CommonResult<NewsCategoryRespVO> getNewsCategory(@RequestParam("id") Long id) {
        NewsCategoryDO newsCategory = newsCategoryService.getNewsCategory(id);
        return success(BeanUtils.toBean(newsCategory, NewsCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得新闻目录列表")
    @PreAuthorize("@ss.hasPermission('xinlian:news-category:query')")
    public CommonResult<List<NewsCategoryRespVO>> getNewsCategoryList(@Valid NewsCategoryListReqVO listReqVO) {
        List<NewsCategoryDO> list = newsCategoryService.getNewsCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, NewsCategoryRespVO.class));
    }
    @GetMapping("/simple-list")
    @Operation(summary = "获得精简新闻目录列表")
    @PreAuthorize("@ss.hasPermission('xinlian:news-category:query')")
    public CommonResult<List<NewsCateSimpleRespVO>> getSimpleNewsCategoryList() {

        // 获得岗位列表，只要开启状态的
        List<NewsCategoryDO> list = newsCategoryService.getCateList(null, Collections.singleton(Boolean.TRUE));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(NewsCategoryDO::getSort));
        return success(BeanUtils.toBean(list, NewsCateSimpleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出新闻目录 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:news-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportNewsCategoryExcel(@Valid NewsCategoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<NewsCategoryDO> list = newsCategoryService.getNewsCategoryList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "新闻目录.xls", "数据", NewsCategoryRespVO.class,
                        BeanUtils.toBean(list, NewsCategoryRespVO.class));
    }

}