package cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
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

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.service.xinlianMember.XinlianMemberService;

@Tag(name = "管理后台 - 新联会员")
@RestController
@RequestMapping("/xinlian/member")
@Validated
public class XinlianMemberController {

    @Resource
    private XinlianMemberService memberService;

    @PostMapping("/create")
    @Operation(summary = "创建新联会员")
    @PreAuthorize("@ss.hasPermission('xinlian:member:create')")
    public CommonResult<Long> createMember(@Valid @RequestBody XinlianMemberSaveReqVO createReqVO) {
        Long id = memberService.createMember(createReqVO);
        if (id > 0){
            return success(id);
        }
        return error(500,"创建用户失败");
    }

    @PutMapping("/update")
    @Operation(summary = "更新新联会员")
    @PreAuthorize("@ss.hasPermission('xinlian:member:update')")
    public CommonResult<Boolean> updateMember(@Valid @RequestBody XinlianMemberSaveReqVO updateReqVO) {
        boolean ret = memberService.updateMember(updateReqVO);
        if (ret){
            return success(true);
        }
        else {
            return error(new ErrorCode(500,"更新失败"));
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除新联会员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:member:delete')")
    public CommonResult<Boolean> deleteMember(@RequestParam("id") Long id) {
        memberService.deleteMember(id);
        return success(true);
    }

    @DeleteMapping("/batch/delete")
    @Operation(summary = "批量删除新联会员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:member:delete')")
    public CommonResult<Boolean> batchDeleteMembers(@RequestBody List<Long> ids) {
        memberService.batchDeleteMembers(ids);
        return success(true);
    }


    @PutMapping("/batch/approve")
    @Operation(summary = "批量审批新联会员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:member:update')")
    public CommonResult<Boolean> batchApproveMembers(@RequestBody List<Long> ids) {
        memberService.batchApproveMembers(ids);
        return success(true);
    }




    @GetMapping("/get")
    @Operation(summary = "获得新联会员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:member:query')")
    public CommonResult<XinlianMemberRespVO> getMember(@RequestParam("id") Long id) {
        XinlianMemberDO member = memberService.getMember(id);
        return success(BeanUtils.toBean(member, XinlianMemberRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得新联会员分页")
    @PreAuthorize("@ss.hasPermission('xinlian:member:query')")
    public CommonResult<PageResult<XinlianMemberRespVO>> getMemberPage(@Valid XinlianMemberPageReqVO pageReqVO) {
        PageResult<XinlianMemberDO> pageResult = memberService.getMemberPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, XinlianMemberRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出新联会员 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:member:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMemberExcel(@Valid XinlianMemberPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<XinlianMemberDO> list = memberService.getMemberPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "新联会员.xls", "数据", XinlianMemberRespVO.class,
                        BeanUtils.toBean(list, XinlianMemberRespVO.class));
    }

}