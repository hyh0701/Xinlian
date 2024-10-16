package cn.iocoder.yudao.module.xinlian.controller.app.activity;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.XinlianActivityPageReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.XinlianActivityRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.XinlianActivitySaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.module.xinlian.service.activity.XinlianActivityService;
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

@Tag(name = "用户 APP - 活动记录")
@RestController
@RequestMapping("/xinlian/activity")
@Validated
public class AppXinlianActivityController {

    @Resource
    private XinlianActivityService activityService;

    @GetMapping("/get")
    @Operation(summary = "获得活动记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<XinlianActivityRespVO> getActivity(@RequestParam("id") Long id) {
        XinlianActivityDO activity = activityService.getActivity(id);
        return success(BeanUtils.toBean(activity, XinlianActivityRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动记录分页")
    public CommonResult<PageResult<XinlianActivityRespVO>> getActivityPage(@Valid XinlianActivityPageReqVO pageReqVO) {
        PageResult<XinlianActivityDO> pageResult = activityService.getActivityPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, XinlianActivityRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出活动记录 Excel")
    @PreAuthenticated
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