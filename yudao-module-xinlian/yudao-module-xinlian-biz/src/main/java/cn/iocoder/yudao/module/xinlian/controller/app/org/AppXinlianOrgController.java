package cn.iocoder.yudao.module.xinlian.controller.app.org;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.XinlianOrgListReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.XinlianOrgRespVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.XinlianOrgSaveReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.XinlianMemberPageReqVO;
import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.XinlianMemberSaveReqVO;
import cn.iocoder.yudao.module.xinlian.controller.app.org.vo.AppOrgJobMemberReqVO;
import cn.iocoder.yudao.module.xinlian.controller.app.org.vo.AppXinlianOrgListReqVO;
import cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo.AppXinlianMemberRespVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.job.XinlianJobDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDOExt;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberExtDO;
import cn.iocoder.yudao.module.xinlian.service.job.XinlianJobService;
import cn.iocoder.yudao.module.xinlian.service.org.XinlianOrgService;
import cn.iocoder.yudao.module.xinlian.service.xinlianMember.XinlianMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

@Tag(name = "用户 APP - 组织架构管理")
@RestController
@RequestMapping("/xinlian/org")
@Validated
public class AppXinlianOrgController {

    @Resource
    private XinlianOrgService orgService;
    @Resource
    private XinlianMemberService memberService;
    @Resource
    private XinlianJobService jobService;
    @GetMapping("/get")
    @Operation(summary = "可选,供二级页面,根据组织ID获取组织下面的职位列表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<XinlianOrgRespVO> getOrg(@RequestParam("id") Long id) {
        XinlianOrgDO org = orgService.getOrg(id);
        if (org == null){
            return CommonResult.error(ORG_NOT_EXISTS);
        }
        List<XinlianJobDO> jobs = jobService.getJobList();
        Set<Long> jobIds = org.getJobs();
        if (CollectionUtils.isNotEmpty(jobIds)){
            // 使用 Stream API 进行过滤
            List<XinlianJobDO> filteredJobs = jobs.stream()
                    .filter(job -> jobIds.contains(job.getId())).sorted(Comparator.comparing(XinlianJobDO::getId).reversed())
                    .collect(Collectors.toList());

            XinlianOrgRespVO respVO = BeanUtils.toBean(org, XinlianOrgRespVO.class);
            respVO.setJobList(filteredJobs);
            return success(respVO);
        }
        return CommonResult.error(JOB_NOT_EXISTS);
    }

    @GetMapping("/list")
    @Operation(summary = "获取组织管理列表,一级页面和二级页面共用")
    public CommonResult<List<XinlianOrgRespVO>> getOrgList() {
        List<XinlianOrgDO> oneList = orgService.getOneAppOrgList();
        List<XinlianOrgDO> twoList = orgService.getTwoAppOrgList();

        List<XinlianJobDO> jobs = jobService.getJobList();
        List<XinlianOrgRespVO> resps = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(oneList)){
            for (int i = 0; i < oneList.size(); i ++){
                XinlianOrgDO org = oneList.get(i);
                XinlianOrgRespVO vo = BeanUtils.toBean(org,XinlianOrgRespVO.class);
                Set<Long> jobIds = org.getJobs();
                if (CollectionUtils.isNotEmpty(jobIds)){
                    // 使用 Stream API 进行过滤
                    List<XinlianJobDO> filteredJobs = jobs.stream()
                            .filter(job -> jobIds.contains(job.getId()))
                            .collect(Collectors.toList());
                    vo.setJobList(filteredJobs);
                }
                resps.add(vo);
            }
        }
        return success(resps);
    }

    @GetMapping("/listById")
    @Operation(summary = "根据父ID获取组织管理列表,根ID传0")
    @Parameter(name = "id", description = "编号", required = true, example = "0")
    public CommonResult<List<XinlianOrgRespVO>> getOrgListByParentId(@RequestParam("id") Long id) {
        List<XinlianOrgDO> list = orgService.getAppOrgListByParentId(id);
        List<XinlianJobDO> jobs = jobService.getJobList();
        List<XinlianOrgRespVO> resps = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            for (int i = 0; i < list.size(); i ++){
                XinlianOrgDO org = list.get(i);
                XinlianOrgRespVO vo = BeanUtils.toBean(org,XinlianOrgRespVO.class);
                Set<Long> jobIds = org.getJobs();
                if (CollectionUtils.isNotEmpty(jobIds)){
                    // 使用 Stream API 进行过滤
                    List<XinlianJobDO> filteredJobs = jobs.stream()
                            .filter(job -> jobIds.contains(job.getId()))
                            .collect(Collectors.toList());
                    vo.setJobList(filteredJobs);
                }
                resps.add(vo);
            }
        }
        return success(resps);
    }

    @PostMapping("/listMember")
    @Operation(summary = "三级页面,根据orgId和jobId获取指定组织下面的成员")
    public CommonResult<List<XinlianMemberDO>> getOrgListMember(@Valid @RequestBody AppOrgJobMemberReqVO reqVO) {
        XinlianOrgDO org = orgService.getOrg(reqVO.getOrgId());
        Set<Long> ids = new HashSet<>();
        ids.add(org.getId());
        List<XinlianMemberDO> members = memberService.getMemberListByOrgIds(ids,true);
        List<XinlianMemberDO> filteredMembers = members.stream()
                .filter(member -> reqVO.getJobId().equals(member.getMainJobId()) || reqVO.getJobId().equals(member.getSecondJobId()))
                .collect(Collectors.toList());
        List<XinlianMemberDO> secondsMembers = filteredMembers.stream().filter(
                s -> s.getMemberType().equals(Short.valueOf("1"))).collect(Collectors.toList());
        return success(secondsMembers);
    }



    @GetMapping("/search")
    @Operation(summary = "根据名字搜索会员")
    @Parameter(name = "name", description = "名字", required = true, example = "张三")
    public CommonResult<List<XinlianMemberDO>> searchMembers(@RequestParam("name") String name) {
        XinlianMemberPageReqVO reqVO = new XinlianMemberPageReqVO();
        reqVO.setPageSize(100);
        reqVO.setName(name);
        System.out.println(reqVO+"555555555555");
        PageResult<XinlianMemberDO> pageResult = memberService.getMemberPage(reqVO);
        if (pageResult == null || pageResult.getTotal() <= 0){
            return CommonResult.error(MEMBER_SEARCH_NOT_EXISTS);
        }
        return success(pageResult.getList());
    }


}