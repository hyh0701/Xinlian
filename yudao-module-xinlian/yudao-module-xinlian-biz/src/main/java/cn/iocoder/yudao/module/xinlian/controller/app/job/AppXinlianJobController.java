package cn.iocoder.yudao.module.xinlian.controller.app.job;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.xinlian.controller.admin.job.vo.JobPageReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.job.vo.JobRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.job.vo.JobSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.job.XinlianJobDO;
import cn.iocoder.yudao.module.xinlian.service.job.XinlianJobService;
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
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 职务管理")
@RestController
@RequestMapping("/xinlian/job")
@Validated
public class AppXinlianJobController {

    @Resource
    private XinlianJobService xinlianJobService;


    @GetMapping("/get")
    @Operation(summary = "获得职务管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<JobRespVO> getJob(@RequestParam("id") Long id) {
        XinlianJobDO job = xinlianJobService.getJob(id);
        return success(BeanUtils.toBean(job, JobRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得职务管理分页")
    @PreAuthenticated
    public CommonResult<PageResult<JobRespVO>> getJobPage(@Valid JobPageReqVO pageReqVO) {
        PageResult<XinlianJobDO> pageResult = xinlianJobService.getJobPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, JobRespVO.class));
    }
    


}