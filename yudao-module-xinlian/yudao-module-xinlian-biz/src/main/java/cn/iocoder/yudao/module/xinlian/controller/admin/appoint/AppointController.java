package cn.iocoder.yudao.module.xinlian.controller.admin.appoint;

import cn.iocoder.yudao.framework.datapermission.core.annotation.DataPermission;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.infra.dal.dataobject.config.ConfigDO;
import cn.iocoder.yudao.module.infra.service.config.ConfigService;
import org.apache.commons.lang3.StringUtils;
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

import cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.appoint.AppointDO;
import cn.iocoder.yudao.module.xinlian.service.appoint.AppointService;

@Tag(name = "管理后台 - 绩效计分")
@RestController
@RequestMapping("/xinlian/appoint")
@Validated
@DataPermission(includeRules = {})
public class AppointController {

    @Resource
    private AppointService appointService;

    @PostMapping("/create")
    @Operation(summary = "创建绩效计分")
    @PreAuthorize("@ss.hasPermission('xinlian:appoint:create')")
    public CommonResult<Long> createAppoint(@Valid @RequestBody AppointSaveReqVO createReqVO) {
        return success(appointService.createAppoint(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新绩效计分")
    @PreAuthorize("@ss.hasPermission('xinlian:appoint:update')")
    public CommonResult<Boolean> updateAppoint(@Valid @RequestBody AppointSaveReqVO updateReqVO) {

        appointService.updateAppoint(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除绩效计分")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:appoint:delete')")
    public CommonResult<Boolean> deleteAppoint(@RequestParam("id") Long id) {
        appointService.deleteAppoint(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得绩效计分")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:appoint:query')")
    public CommonResult<AppointRespVO> getAppoint(@RequestParam("id") Long id) {
        AppointDO appoint = appointService.getAppoint(id);
        return success(BeanUtils.toBean(appoint, AppointRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得绩效计分分页")
    @PreAuthorize("@ss.hasPermission('xinlian:appoint:query')")
    public CommonResult<PageResult<AppointRespVO>> getAppointPage(@Valid AppointPageReqVO pageReqVO) {
        PageResult<AppointDO> pageResult = appointService.getAppointPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppointRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出绩效计分 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:appoint:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAppointExcel(@Valid AppointPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AppointDO> list = appointService.getAppointPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "绩效计分.xls", "数据", AppointRespVO.class,
                        BeanUtils.toBean(list, AppointRespVO.class));
    }


}