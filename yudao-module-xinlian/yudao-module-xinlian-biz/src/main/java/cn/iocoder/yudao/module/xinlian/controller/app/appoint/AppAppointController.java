package cn.iocoder.yudao.module.xinlian.controller.app.appoint;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo.AppointPageReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo.AppointRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo.AppointSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.appoint.AppointDO;
import cn.iocoder.yudao.module.xinlian.service.appoint.AppointService;
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

@Tag(name = "用户 APP - 绩效计分")
@RestController
@RequestMapping("/xinlian/appoint")
@Validated
public class AppAppointController {

    @Resource
    private AppointService appointService;

    @PostMapping("/create")
    @Operation(summary = "创建绩效计分")
    @PreAuthenticated
    public CommonResult<Long> createAppoint(@Valid @RequestBody AppointSaveReqVO createReqVO) {
        return success(appointService.createAppAppoint(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新绩效计分")
    @PreAuthenticated
    public CommonResult<Boolean> updateAppoint(@Valid @RequestBody AppointSaveReqVO updateReqVO) {
        AppointDO appoint = appointService.getAppoint(updateReqVO.getId());
        if (appoint == null){
            return error(500,"未找到绩效记录");
        }
        if (appoint.getState().equals(Short.valueOf("2"))){
            return error(400,"绩效记录审核通过,不允许修改");
        }
        appointService.updateAppAppoint(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除绩效计分")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthenticated
    public CommonResult<Boolean> deleteAppoint(@RequestParam("id") Long id) {
        AppointDO appoint = appointService.getAppoint(id);
        if (appoint == null){
            return error(500,"未找到绩效记录");
        }
        if (appoint.getState().equals(Short.valueOf("2"))){
            return error(400,"绩效记录审核通过,不允许删除");
        }
        appointService.deleteAppoint(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得绩效计分")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<AppointRespVO> getAppoint(@RequestParam("id") Long id) {
        AppointDO appoint = appointService.getAppoint(id);
        return success(BeanUtils.toBean(appoint, AppointRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得绩效计分分页")
    @PreAuthenticated
    public CommonResult<PageResult<AppointRespVO>> getAppointPage(@Valid AppointPageReqVO pageReqVO) {
        PageResult<AppointDO> pageResult = appointService.getAppAppointPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppointRespVO.class));
    }


}