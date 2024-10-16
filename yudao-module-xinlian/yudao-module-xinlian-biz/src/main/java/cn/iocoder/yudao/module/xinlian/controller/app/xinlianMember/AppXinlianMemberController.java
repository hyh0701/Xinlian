package cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.security.core.annotations.PreAuthenticated;
import cn.iocoder.yudao.module.infra.service.config.ConfigService;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.service.user.MemberUserService;
import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.XinlianMemberPageReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.XinlianMemberRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.XinlianMemberSaveReqVO;
import cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo.AppGroupedMemberVo;
import cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo.AppXinlianMemberSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberExtDO;
import cn.iocoder.yudao.module.xinlian.service.xinlianMember.XinlianMemberService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @author Administrator
 */
@Tag(name = "用户 APP - 新联会员")
@RestController
@RequestMapping("/xinlian/member")
@Validated
public class AppXinlianMemberController {

    @Resource
    private XinlianMemberService memberService;

    @Resource
    private ConfigService configService;

    @Resource
    private MemberUserService userService;

    @GetMapping("/get")
    @Operation(summary = "获得新联会员")
    @PreAuthenticated
    public CommonResult<XinlianMemberRespVO> getMember() {
        MemberUserDO user = userService.getUser(getLoginUserId());
        XinlianMemberDO member = memberService.getMemberByMemberId(user.getId());
        return success(BeanUtils.toBean(member, XinlianMemberRespVO.class));
    }


    @PutMapping("/update")
    @Operation(summary = "更新自己的信息")
    @PreAuthenticated
    public CommonResult<Boolean> updateMember(@Valid @RequestBody AppXinlianMemberSaveReqVO updateReqVO) {
        boolean ret = memberService.updateMember(updateReqVO);
        if (ret){
            return success(true);
        }
        else {
            return error(new ErrorCode(500,"更新失败"));
        }
    }

    @PutMapping("/apply")
    @Operation(summary = "申请成为会员")
    @PreAuthenticated
    public CommonResult<Boolean> applyMember(@Valid @RequestBody AppXinlianMemberSaveReqVO updateReqVO) {
        XinlianMemberDO updateObj = memberService.getMember(updateReqVO.getId());
        if (updateObj == null){
            return error(new ErrorCode(500,"未找到会员信息"));
        }
        XinlianMemberSaveReqVO saveReqVO = BeanUtils.toBean(updateObj, XinlianMemberSaveReqVO.class);
        saveReqVO.setState(3);
        boolean ret = memberService.updateMember(saveReqVO);
        if (ret){
            return success(true);
        }
        else {
            return error(new ErrorCode(500,"申请失败"));
        }
    }

    @GetMapping("/contact")
    @Operation(summary = "查看联系方式")
    public CommonResult<List<XinlianMemberExtDO>> getContact() {
        List<XinlianMemberExtDO> members = memberService.getContact();
        return success(members);
    }

    @GetMapping("/marker")
    @Operation(summary = "获取地图上面的会员")
    public CommonResult<List<AppGroupedMemberVo>> getMarker() {
        List<XinlianMemberDO> members = memberService.getMapMarker();
        // 将 memberType 转换为 memberTypeName 的方法
        Map<Short, String> memberTypeNames = new HashMap<>();
        memberTypeNames.put(Short.valueOf((short)2), "企业会员");
        memberTypeNames.put(Short.valueOf((short)3), "创新实践基地");
        memberTypeNames.put(Short.valueOf((short)4), "公益服务团队");

        // 使用 Stream API 进行分组并生成新的实体类
        List<AppGroupedMemberVo> groupedMembers = members.stream()
                .collect(Collectors.groupingBy(XinlianMemberDO::getMemberType))
                .entrySet().stream()
                .map(entry -> new AppGroupedMemberVo(
                        entry.getKey(),
                        memberTypeNames.get(entry.getKey()),
                        entry.getValue()
                ))
                .collect(Collectors.toList());

        return success(groupedMembers);
    }

}