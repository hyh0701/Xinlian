package cn.iocoder.yudao.module.xinlian.service.advice;

import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.service.user.MemberUserService;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.xinlian.controller.app.advice.vo.AppAdviceSaveReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.advice.AdviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.advice.AdviceMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 咨询记录 Service 实现类
 *
 * @author 平台用户
 */
@Service
@Validated
public class AdviceServiceImpl implements AdviceService {

    @Resource
    private AdviceMapper adviceMapper;

    @Resource
    private MemberUserService memberUserService;

    @Resource
    private AdminUserService adminUserService;

    @Override
    public Long createAdvice(AdviceSaveReqVO createReqVO) {
        // 插入
        AdviceDO advice = BeanUtils.toBean(createReqVO, AdviceDO.class);
        AdminUserDO adminUserDO = adminUserService.getUser(SecurityFrameworkUtils.getLoginUserId());
        if (adminUserDO != null){
            advice.setMemberId(adminUserDO.getId());
            advice.setMemberName(adminUserDO.getUsername());
        }
        adviceMapper.insert(advice);
        // 返回
        return advice.getId();
    }

    @Override
    public Long createAppAdvice(AppAdviceSaveReqVO createReqVO) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        MemberUserDO memberUser = memberUserService.getUser(userId);

        // 插入
        AdviceDO advice = BeanUtils.toBean(createReqVO, AdviceDO.class);
        advice.setMemberId(userId);
        if (memberUser != null){
            advice.setMemberName(memberUser.getName());
        }
        adviceMapper.insert(advice);

        // 返回
        return advice.getId();
    }

    @Override
    public void updateAdvice(AdviceSaveReqVO updateReqVO) {
        // 校验存在
        validateAdviceExists(updateReqVO.getId());

        // 获取当前登录用户信息
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        AdminUserDO adminUserDO = adminUserService.getUser(SecurityFrameworkUtils.getLoginUserId());

        // 输出获取到的用户信息（用于调试）
        System.out.println(adminUserDO);

        // 将更新请求对象转换为数据对象
        AdviceDO updateObj = BeanUtils.toBean(updateReqVO, AdviceDO.class);

        // 如果获取到的用户信息不为空，将用户ID和用户名填充到更新数据对象中
        if (adminUserDO != null) {
            updateObj.setReplyUserId(adminUserDO.getId());
            updateObj.setReplyUserName(adminUserDO.getUsername());
        }

        // 调试输出 updateObj
        System.out.println("1111"+updateObj);

        // 更新咨询记录
        adviceMapper.updateById(updateObj);
    }


    @Override
    public void deleteAdvice(Long id) {
        // 校验存在
        validateAdviceExists(id);
        // 删除
        adviceMapper.deleteById(id);
    }

    private void validateAdviceExists(Long id) {
        if (adviceMapper.selectById(id) == null) {
            throw exception(ADVICE_NOT_EXISTS);
        }
    }

    @Override
    public AdviceDO getAdvice(Long id) {
        return adviceMapper.selectById(id);
    }

    @Override
    public PageResult<AdviceDO> getAdvicePage(AdvicePageReqVO pageReqVO) {
        return adviceMapper.selectPage(pageReqVO);
    }


    @Override
    public PageResult<AdviceDO> getAppAdvicePage(AdvicePageReqVO pageReqVO) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        MemberUserDO memberUser = memberUserService.getUser(userId);
        if (memberUser== null){
            throw exception(ADVICE_NOT_EXISTS);
        }
        pageReqVO.setMemberId(memberUser.getId().toString());
        return adviceMapper.selectAppPage(pageReqVO);
    }
}