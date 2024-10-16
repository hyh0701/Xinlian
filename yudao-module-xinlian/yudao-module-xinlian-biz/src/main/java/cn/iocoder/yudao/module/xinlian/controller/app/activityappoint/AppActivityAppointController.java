package cn.iocoder.yudao.module.xinlian.controller.app.activityappoint;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo.ActivityAppointPageReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo.ActivityAppointRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo.ActivityAppointSaveReqVO;
import cn.iocoder.yudao.module.xinlian.controller.app.activity.vo.AppActivityAppointSaveReqVO;
import cn.iocoder.yudao.module.xinlian.controller.app.activityappoint.vo.AppActivityAppointRespVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointExtDO;
import cn.iocoder.yudao.module.xinlian.service.activity.XinlianActivityService;
import cn.iocoder.yudao.module.xinlian.service.activityappoint.ActivityAppointService;
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
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 活动报名")
@RestController
@RequestMapping("/xinlian/activity-appoint")
@Validated
public class AppActivityAppointController {

    @Resource
    private ActivityAppointService activityAppointService;


    @Resource
    private XinlianActivityService activityService;

    @PostMapping("/create")
    @Operation(summary = "创建活动报名")
    @PreAuthenticated
    public CommonResult<Long> createActivityAppoint(@Valid @RequestBody AppActivityAppointSaveReqVO createReqVO) {
        return success(activityAppointService.createActivityAppoint(createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得活动报名")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<AppActivityAppointRespVO> getActivityAppoint(@RequestParam("id") Long id) {
        ActivityAppointDO activityAppoint = activityAppointService.getActivityAppoint(id);
        AppActivityAppointRespVO respVO = BeanUtils.toBean(activityAppoint, AppActivityAppointRespVO.class);
        XinlianActivityDO xinlianActivityDO = activityService.getActivity(activityAppoint.getActivityId());
        respVO.setActivity(xinlianActivityDO);
        return success(respVO);
    }
    @PutMapping("/update")
    @Operation(summary = "更新活动报名")
    @PreAuthenticated
    public CommonResult<Boolean> updateActivityAppoint(@Valid @RequestBody ActivityAppointSaveReqVO updateReqVO) {
        ActivityAppointDO activityAppoint = activityAppointService.getActivityAppoint(updateReqVO.getId());
        if (activityAppoint == null){
            return error(500,"未找到活动报名");
        }
        if (activityAppoint.getState().equals(Short.valueOf("2"))){
            return error(400,"报名审核通过,不允许修改");
        }
        activityAppointService.updateActivityAppoint(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除活动报名")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthenticated
    public CommonResult<Boolean> deleteActivityAppoint(@RequestParam("id") Long id) {
        ActivityAppointDO activityAppoint = activityAppointService.getActivityAppoint(id);
        if (activityAppoint == null){
            return error(500,"未找到活动报名");
        }
        if (activityAppoint.getState().equals(Short.valueOf("2"))){
            return error(400,"报名审核通过,不允许删除");
        }
        activityAppointService.deleteActivityAppoint(id);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得活动报名分页")
    @PreAuthenticated
    public CommonResult<PageResult<ActivityAppointExtDO>> getActivityAppointPage(@Valid ActivityAppointPageReqVO pageReqVO) {
        List<ActivityAppointExtDO> list = activityAppointService.getActivityAppointPageWithAct(pageReqVO);
        PageResult<ActivityAppointExtDO> result = new PageResult<>();
        result.setList(list);
        result.setTotal(Long.valueOf(list.size()));
        return success(result);
    }

}