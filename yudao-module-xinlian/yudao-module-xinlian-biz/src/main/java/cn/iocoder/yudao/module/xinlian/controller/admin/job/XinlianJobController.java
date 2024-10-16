package cn.iocoder.yudao.module.xinlian.controller.admin.job;

import cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo.NewsCateSimpleRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.XinlianOrgSimpleRespVo;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import cn.iocoder.yudao.module.xinlian.controller.admin.job.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.job.XinlianJobDO;
import cn.iocoder.yudao.module.xinlian.service.job.XinlianJobService;

@Tag(name = "管理后台 - 职务管理")
@RestController
@RequestMapping("/xinlian/job")
@Validated
public class XinlianJobController {

    @Resource
    private XinlianJobService xinlianJobService;

    @PostMapping("/create")
    @Operation(summary = "创建职务管理")
    @PreAuthorize("@ss.hasPermission('xinlian:job:create')")
    public CommonResult<Long> createJob(@Valid @RequestBody JobSaveReqVO createReqVO) {
        return success(xinlianJobService.createJob(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新职务管理")
    @PreAuthorize("@ss.hasPermission('xinlian:job:update')")
    public CommonResult<Boolean> updateJob(@Valid @RequestBody JobSaveReqVO updateReqVO) {
        xinlianJobService.updateJob(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除职务管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('xinlian:job:delete')")
    public CommonResult<Boolean> deleteJob(@RequestParam("id") Long id) {
        xinlianJobService.deleteJob(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得职务管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('xinlian:job:query')")
    public CommonResult<JobRespVO> getJob(@RequestParam("id") Long id) {
        XinlianJobDO job = xinlianJobService.getJob(id);
        return success(BeanUtils.toBean(job, JobRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得职务管理分页")
    @PreAuthorize("@ss.hasPermission('xinlian:job:query')")
    public CommonResult<PageResult<JobRespVO>> getJobPage(@Valid JobPageReqVO pageReqVO) {
        PageResult<XinlianJobDO> pageResult = xinlianJobService.getJobPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, JobRespVO.class));
    }
    @GetMapping("/list")
    @Operation(summary = "获得精简职务管理分页")
    @PreAuthorize("@ss.hasPermission('xinlian:job:query')")
    public CommonResult<List<JobRespVO>> getSimpleJobList() {
        // 获得岗位列表，只要开启状态的
        List<XinlianJobDO> list = xinlianJobService.getJobList();
        // 排序后，返回给前端
        list.sort(Comparator.comparing(XinlianJobDO::getCreateTime));
        return success(BeanUtils.toBean(list, JobRespVO.class));
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出职务管理 Excel")
    @PreAuthorize("@ss.hasPermission('xinlian:job:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportJobExcel(@Valid JobPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<XinlianJobDO> list = xinlianJobService.getJobPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "职务管理.xls", "数据", JobRespVO.class,
                        BeanUtils.toBean(list, JobRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得精简职位列表")
    @PreAuthorize("@ss.hasPermission('xinlian:job:query')")
    public CommonResult<List<XinlianJobSimpleRespVo>> getSimpleJobCategoryList() {

        List<XinlianJobDO> list=xinlianJobService.getJobList();
        return success(BeanUtils.toBean(list, XinlianJobSimpleRespVo.class));

    }

}