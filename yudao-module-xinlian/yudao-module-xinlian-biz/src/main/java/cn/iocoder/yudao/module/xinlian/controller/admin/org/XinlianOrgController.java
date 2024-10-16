package cn.iocoder.yudao.module.xinlian.controller.admin.org;

import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.XinlianActivitySimpleVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
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

import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import cn.iocoder.yudao.module.xinlian.service.org.XinlianOrgService;

@Tag(name = "管理后台 - 组织架构管理")
@RestController
@RequestMapping("/xinlian/org")
@Validated
public class XinlianOrgController {

    @Resource
    private XinlianOrgService orgService;

    @PostMapping("/create")
    @Operation(summary = "创建组织架构管理")
    @PreAuthorize("@ss.hasPermission('xinlian:org:create')")
    public CommonResult<Long> createOrg(@Valid @RequestBody XinlianOrgSaveReqVO createReqVO) {
        return success(orgService.createOrg(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新组织架构管理")
    @PreAuthorize("@ss.hasPermission('xinlian:org:update')")
    public CommonResult<Boolean> updateOrg(@Valid @RequestBody XinlianOrgSaveReqVO updateReqVO) {
        orgService.updateOrg(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除组织架构管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:org:delete')")
    public CommonResult<Boolean> deleteOrg(@RequestParam("id") Long id) {
        orgService.deleteOrg(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得组织架构管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:org:query')")
    public CommonResult<XinlianOrgRespVO> getOrg(@RequestParam("id") Long id) {
        XinlianOrgDO org = orgService.getOrg(id);
        return success(BeanUtils.toBean(org, XinlianOrgRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得组织架构管理列表")
    @PreAuthorize("@ss.hasPermission('xinlian:org:query')")
    public CommonResult<List<XinlianOrgRespVO>> getOrgList(@Valid XinlianOrgListReqVO listReqVO) {
        List<XinlianOrgDO> list = orgService.getOrgList(listReqVO);
        return success(BeanUtils.toBean(list, XinlianOrgRespVO.class));
    }
    @GetMapping("/export-excel")
    @Operation(summary = "导出组织架构管理 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:org:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrgExcel(@Valid XinlianOrgListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<XinlianOrgDO> list = orgService.getOrgList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "组织架构管理.xls", "数据", XinlianOrgRespVO.class,
                        BeanUtils.toBean(list, XinlianOrgRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得精简组织列表")
    @PreAuthorize("@ss.hasPermission('xinlian:org:query')")
    public CommonResult<List<XinlianOrgSimpleRespVo>> getSimpleActivityCategoryList() {

        List<XinlianOrgDO> list=orgService.getOrgList();
        return success(BeanUtils.toBean(list, XinlianOrgSimpleRespVo.class));

    }

}