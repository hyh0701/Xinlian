package cn.iocoder.yudao.module.xinlian.service.appoint;

import cn.iocoder.yudao.framework.datapermission.core.annotation.DataPermission;
import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.infra.dal.dataobject.config.ConfigDO;
import cn.iocoder.yudao.module.infra.service.config.ConfigService;
import cn.iocoder.yudao.module.xinlian.controller.app.activity.vo.AppActivityAppointSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.service.xinlianMember.XinlianMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.appoint.AppointDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.appoint.AppointMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 绩效计分 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class AppointServiceImpl implements AppointService {

    @Resource
    private AppointMapper appointMapper;

    @Resource
    private ConfigService configService;

    @Resource
    private XinlianMemberService memberService;

    @Override
    public Long createAppoint(AppointSaveReqVO createReqVO) {
        // 插入
        AppointDO appoint = BeanUtils.toBean(createReqVO, AppointDO.class);
        appointMapper.insert(appoint);
        // 返回
        return appoint.getId();
    }
    @Override
    public Long createAppAppoint(AppointSaveReqVO createReqVO) {
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        XinlianMemberDO memberDO = memberService.getMemberByMemberId(loginUser.getId());
        //判断是否创建
        check(createReqVO,memberDO);
        // 插入
        createReqVO.setMemberId(SecurityFrameworkUtils.getLoginUserId());
        AppointDO appoint = BeanUtils.toBean(createReqVO, AppointDO.class);
        appointMapper.insert(appoint);
        // 返回
        return appoint.getId();
    }

    private void check(AppointSaveReqVO createReqVO, XinlianMemberDO memberDO)
    {
        AppointPageReqVO pageReqVO = new AppointPageReqVO();
        pageReqVO.setAppointPlan(createReqVO.getAppointPlan());
        pageReqVO.setMemberId(SecurityFrameworkUtils.getLoginUserId());
        PageResult<AppointDO> selectPage = appointMapper.selectPage(pageReqVO);
        if (selectPage != null && selectPage.getTotal() > 0){
            throw exception(APPOINT_HAS_EXIST_USER);
        }


    }
    @Override
    public void updateAppoint(AppointSaveReqVO updateReqVO) {
        // 校验存在
        validateAppointExists(updateReqVO.getId());
        // 更新
        AppointDO updateObj = BeanUtils.toBean(updateReqVO, AppointDO.class);
        appointMapper.updateById(updateObj);
    }
    @Override
    public void updateAppAppoint(AppointSaveReqVO updateReqVO) {
        // 校验存在
        AppointDO appointDO = validateAppointExists(updateReqVO.getId());
        //
//        if (checkAppointDept()){
//            if (appointDO.getUserId() != 0L && appointDO.getUserId().equals(SecurityFrameworkUtils.getLoginUserId())){
//                throw exception(APPOINT_NO_PERMISSION);
//            }
//        }
        // 更新
        AppointDO updateObj = BeanUtils.toBean(updateReqVO, AppointDO.class);
        appointMapper.updateById(updateObj);
    }

    @Override
    public void deleteAppoint(Long id) {
        // 校验存在
        validateAppointExists(id);
        // 删除
        appointMapper.deleteById(id);
    }

    private AppointDO validateAppointExists(Long id) {
        AppointDO appointDO = appointMapper.selectById(id);
        if (appointDO == null) {
            throw exception(APPOINT_NOT_EXISTS);
        }
        return appointDO;
    }

    @Override
    public AppointDO getAppoint(Long id) {
        return appointMapper.selectById(id);
    }

    @Override
    public PageResult<AppointDO> getAppointPage(AppointPageReqVO pageReqVO) {
        return appointMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<AppointDO> getAppAppointPage(AppointPageReqVO pageReqVO) {
//        if (checkAppointDept()){
//            pageReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
//        }
        pageReqVO.setMemberId(SecurityFrameworkUtils.getLoginUserId());
        return appointMapper.selectPage(pageReqVO);
    }

    private boolean checkAppointDept()
    {
        Long deptId = SecurityFrameworkUtils.getLoginUserDeptId();
        ConfigDO configDO = configService.getConfigByKey("appoint_dept");
        if (configDO != null && StringUtils.isNotEmpty(configDO.getValue())){
            if (deptId.equals(Long.valueOf(configDO.getValue()))){
                return true;
            }
        }
        return false;
    }
}