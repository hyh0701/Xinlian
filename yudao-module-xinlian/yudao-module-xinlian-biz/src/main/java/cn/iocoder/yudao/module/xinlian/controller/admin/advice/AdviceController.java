package cn.iocoder.yudao.module.xinlian.controller.admin.advice;

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

import cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.advice.AdviceDO;
import cn.iocoder.yudao.module.xinlian.service.advice.AdviceService;

@Tag(name = "管理后台 - 咨询记录")
@RestController
@RequestMapping("/xinlian/advice")
@Validated
public class AdviceController {

    @Resource
    private AdviceService adviceService;

    @PostMapping("/create")
    @Operation(summary = "创建咨询记录")
    @PreAuthorize("@ss.hasPermission('xinlian:advice:create')")
    public CommonResult<Long> createAdvice(@Valid @RequestBody AdviceSaveReqVO createReqVO) {
        return success(adviceService.createAdvice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新咨询记录")
    @PreAuthorize("@ss.hasPermission('xinlian:advice:update')")
    public CommonResult<Boolean> updateAdvice(@Valid @RequestBody AdviceSaveReqVO updateReqVO) {
        adviceService.updateAdvice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除咨询记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:advice:delete')")
    public CommonResult<Boolean> deleteAdvice(@RequestParam("id") Long id) {
        adviceService.deleteAdvice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得咨询记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:advice:query')")
    public CommonResult<AdviceRespVO> getAdvice(@RequestParam("id") Long id) {
        AdviceDO advice = adviceService.getAdvice(id);
        return success(BeanUtils.toBean(advice, AdviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得咨询记录分页")
    @PreAuthorize("@ss.hasPermission('xinlian:advice:query')")
    public CommonResult<PageResult<AdviceRespVO>> getAdvicePage(@Valid AdvicePageReqVO pageReqVO) {
        PageResult<AdviceDO> pageResult = adviceService.getAdvicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AdviceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出咨询记录 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:advice:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportAdviceExcel(@Valid AdvicePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AdviceDO> list = adviceService.getAdvicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "咨询记录.xls", "数据", AdviceRespVO.class,
                        BeanUtils.toBean(list, AdviceRespVO.class));
    }

}