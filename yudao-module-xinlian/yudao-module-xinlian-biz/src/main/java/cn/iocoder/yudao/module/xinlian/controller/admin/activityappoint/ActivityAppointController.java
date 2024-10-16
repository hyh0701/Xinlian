package cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint;

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

import cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.module.xinlian.service.activityappoint.ActivityAppointService;

@Tag(name = "管理后台 - 活动报名")
@RestController
@RequestMapping("/xinlian/activity-appoint")
@Validated
public class ActivityAppointController {

    @Resource
    private ActivityAppointService activityAppointService;

    @PostMapping("/create")
    @Operation(summary = "创建活动报名")
    @PreAuthorize("@ss.hasPermission('xinlian:activity-appoint:create')")
    public CommonResult<Long> createActivityAppoint(@Valid @RequestBody ActivityAppointSaveReqVO createReqVO) {
        return success(activityAppointService.createActivityAppoint(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新活动报名")
    @PreAuthorize("@ss.hasPermission('xinlian:activity-appoint:update')")
    public CommonResult<Boolean> updateActivityAppoint(@Valid @RequestBody ActivityAppointSaveReqVO updateReqVO) {
        activityAppointService.updateActivityAppoint(updateReqVO);
        return success(true);
    }


    @PutMapping("/update/audit")
    @Operation(summary = "更新活动报名状态")
    @PreAuthorize("@ss.hasAnyPermissions('xinlian:activity-appoint:update')")
    public CommonResult<Boolean> updateAudit(@Valid @RequestBody ActivityAuditVO activityAuditVO){
        activityAppointService.updateaudit(activityAuditVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动报名")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:activity-appoint:delete')")
    public CommonResult<Boolean> deleteActivityAppoint(@RequestParam("id") Long id) {
        activityAppointService.deleteActivityAppoint(id);
        return success(true);
    }

    @DeleteMapping("/batch/delete")
    @Operation(summary = "批量删除活动报名")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:activity-appoint:delete')")
    public CommonResult<Boolean> batchDeleteActivity(@RequestBody List<Long> ids) {
        activityAppointService.batchDeleteActivityAppoint(ids);
        return success(true);
    }

    @PutMapping("/batch/approve")
    @Operation(summary = "批量审批活动报名")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasAnyPermissions('xinlian:activity-appoint:update')")
    public CommonResult<Boolean> batchApproveMembers(@RequestBody List<Long> ids) {
        activityAppointService.batchApproveActivityAppoint(ids);
        return success(true);
    }


    @GetMapping("/get")
    @Operation(summary = "获得活动报名")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:activity-appoint:query')")
    public CommonResult<ActivityAppointRespVO> getActivityAppoint(@RequestParam("id") Long id) {
        ActivityAppointDO activityAppoint = activityAppointService.getActivityAppoint(id);
        return success(BeanUtils.toBean(activityAppoint, ActivityAppointRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动报名分页")
    @PreAuthorize("@ss.hasPermission('xinlian:activity-appoint:query')")
    public CommonResult<PageResult<ActivityAppointRespVO>> getActivityAppointPage(@Valid ActivityAppointPageReqVO pageReqVO) {
        PageResult<ActivityAppointDO> pageResult = activityAppointService.getActivityAppointPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ActivityAppointRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动报名 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:activity-appoint:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportActivityAppointExcel(@Valid ActivityAppointPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ActivityAppointDO> list = activityAppointService.getActivityAppointPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "活动报名.xls", "数据", ActivityAppointRespVO.class,
                        BeanUtils.toBean(list, ActivityAppointRespVO.class));
    }

}