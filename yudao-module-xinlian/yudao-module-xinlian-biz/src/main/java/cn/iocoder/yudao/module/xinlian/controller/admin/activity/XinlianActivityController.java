package cn.iocoder.yudao.module.xinlian.controller.admin.activity;

import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.NewsCateSimpleRespVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
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

import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.module.xinlian.service.activity.XinlianActivityService;

@Tag(name = "管理后台 - 活动记录")
@RestController
@RequestMapping("/xinlian/activity")
@Validated
public class XinlianActivityController {

    @Resource
    private XinlianActivityService activityService;

    @PostMapping("/create")
    @Operation(summary = "创建活动记录")
    @PreAuthorize("@ss.hasPermission('xinlian:activity:create')")
    public CommonResult<Long> createActivity(@Valid @RequestBody XinlianActivitySaveReqVO createReqVO) {
        return success(activityService.createActivity(createReqVO));
    }

    @GetMapping("/{id}/name")
    public CommonResult<String> getActivityName(@PathVariable Long id) {
        return activityService.getActivityNameById(id);
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动记录")
    @PreAuthorize("@ss.hasPermission('xinlian:activity:update')")
    public CommonResult<Boolean> updateActivity(@Valid @RequestBody XinlianActivitySaveReqVO updateReqVO) {
        activityService.updateActivity(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:activity:delete')")
    public CommonResult<Boolean> deleteActivity(@RequestParam("id") Long id) {
        activityService.deleteActivity(id);
        return success(true);
    }

    @DeleteMapping("/batch/delete")
    @Operation(summary = "批量删除活动记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:activity:delete')")
    public CommonResult<Boolean> batchDeleteActivity(@RequestBody List<Long> ids) {
        activityService.batchDeleteActivity(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:activity:query')")
    public CommonResult<XinlianActivityRespVO> getActivity(@RequestParam("id") Long id) {
        XinlianActivityDO activity = activityService.getActivity(id);
        return success(BeanUtils.toBean(activity, XinlianActivityRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得精简活动目录列表")
    @PreAuthorize("@ss.hasPermission('xinlian:activity:query')")
    public CommonResult<List<XinlianActivitySimpleVO>> getSimpleActivityCategoryList() {

        List<XinlianActivityDO> list=activityService.getActivityList();
        return success(BeanUtils.toBean(list, XinlianActivitySimpleVO.class));

    }

    @GetMapping("/page")
    @Operation(summary = "获得活动记录分页")
    @PreAuthorize("@ss.hasPermission('xinlian:activity:query')")
    public CommonResult<PageResult<XinlianActivityRespVO>> getActivityPage(@Valid XinlianActivityPageReqVO pageReqVO) {
        PageResult<XinlianActivityDO> pageResult = activityService.getActivityPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, XinlianActivityRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动记录 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:activity:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportActivityExcel(@Valid XinlianActivityPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<XinlianActivityDO> list = activityService.getActivityPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动记录.xls", "数据", XinlianActivityRespVO.class,
                        BeanUtils.toBean(list, XinlianActivityRespVO.class));
    }

}