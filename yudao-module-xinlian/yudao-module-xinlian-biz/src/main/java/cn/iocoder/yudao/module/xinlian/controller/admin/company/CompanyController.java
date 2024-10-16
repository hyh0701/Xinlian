package cn.iocoder.yudao.module.xinlian.controller.admin.company;

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

import cn.iocoder.yudao.module.xinlian.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.xinlian.service.company.CompanyService;

@Tag(name = "管理后台 - 企业会员")
@RestController
@RequestMapping("/xinlian/company")
@Validated
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @PostMapping("/create")
    @Operation(summary = "创建企业会员")
    @PreAuthorize("@ss.hasPermission('xinlian:company:create')")
    public CommonResult<Long> createCompany(@Valid @RequestBody CompanySaveReqVO createReqVO) {
        return success(companyService.createCompany(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新企业会员")
    @PreAuthorize("@ss.hasPermission('xinlian:company:update')")
    public CommonResult<Boolean> updateCompany(@Valid @RequestBody CompanySaveReqVO updateReqVO) {
        companyService.updateCompany(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除企业会员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:company:delete')")
    public CommonResult<Boolean> deleteCompany(@RequestParam("id") Long id) {
        companyService.deleteCompany(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得企业会员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:company:query')")
    public CommonResult<CompanyRespVO> getCompany(@RequestParam("id") Long id) {
        CompanyDO company = companyService.getCompany(id);
        return success(BeanUtils.toBean(company, CompanyRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得企业会员分页")
    @PreAuthorize("@ss.hasPermission('xinlian:company:query')")
    public CommonResult<PageResult<CompanyRespVO>> getCompanyPage(@Valid CompanyPageReqVO pageReqVO) {
        PageResult<CompanyDO> pageResult = companyService.getCompanyPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CompanyRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出企业会员 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:company:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCompanyExcel(@Valid CompanyPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CompanyDO> list = companyService.getCompanyPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "企业会员.xls", "数据", CompanyRespVO.class,
                        BeanUtils.toBean(list, CompanyRespVO.class));
    }

}