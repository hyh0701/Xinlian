package cn.iocoder.yudao.module.xinlian.controller.app.advice;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo.AdvicePageReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo.AdviceRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo.AdviceSaveReqVO;
import cn.iocoder.yudao.module.xinlian.controller.app.advice.vo.AppAdviceSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.advice.AdviceDO;
import cn.iocoder.yudao.module.xinlian.service.advice.AdviceService;
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

@Tag(name = "用户APP - 咨询记录")
@RestController
@RequestMapping("/xinlian/advice")
@Validated
public class AppAdviceController {

    @Resource
    private AdviceService adviceService;

    @PostMapping("/create")
    @Operation(summary = "创建咨询记录")
    @PreAuthenticated
    public CommonResult<Long> createAdvice(@Valid @RequestBody AppAdviceSaveReqVO createReqVO) {
        return success(adviceService.createAppAdvice(createReqVO));
    }
    @PutMapping("/update")
    @Operation(summary = "更新咨询记录")
    @PreAuthenticated
    public CommonResult<Boolean> updateAdvice(@Valid @RequestBody AdviceSaveReqVO updateReqVO) {
        AdviceDO adviceDO = adviceService.getAdvice(updateReqVO.getId());
        if (adviceDO == null){
            return error(500,"未找到咨询记录");
        }
        if (!adviceDO.getStatus().equals(Short.valueOf("0"))){
            return error(400,"咨询已经处理,不允许修改");
        }
        adviceService.updateAdvice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除咨询记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthenticated
    public CommonResult<Boolean> deleteAdvice(@RequestParam("id") Long id) {
        AdviceDO adviceDO = adviceService.getAdvice(id);
        if (adviceDO == null){
            return error(500,"未找到咨询记录");
        }
        if (!adviceDO.getStatus().equals(Short.valueOf("0"))){
            return error(400,"咨询已经处理,不允许删除");
        }
        adviceService.deleteAdvice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得咨询记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<AdviceRespVO> getAdvice(@RequestParam("id") Long id) {
        AdviceDO advice = adviceService.getAdvice(id);
        return success(BeanUtils.toBean(advice, AdviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得咨询记录分页")
    @PreAuthenticated
    public CommonResult<PageResult<AdviceRespVO>> getAdvicePage(@Valid AdvicePageReqVO pageReqVO) {
        PageResult<AdviceDO> pageResult = adviceService.getAppAdvicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AdviceRespVO.class));
    }


}