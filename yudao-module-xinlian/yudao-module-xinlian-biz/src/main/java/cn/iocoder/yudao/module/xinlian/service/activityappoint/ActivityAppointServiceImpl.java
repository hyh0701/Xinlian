package cn.iocoder.yudao.module.xinlian.service.activityappoint;

import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.xinlian.controller.app.activity.vo.AppActivityAppointSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointExtDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.service.activity.XinlianActivityService;
import cn.iocoder.yudao.module.xinlian.service.xinlianMember.XinlianMemberService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.activityappoint.ActivityAppointMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 活动报名 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ActivityAppointServiceImpl implements ActivityAppointService {

    @Resource
    private ActivityAppointMapper activityAppointMapper;
    @Resource
    private XinlianMemberService memberService;

    @Resource
    private XinlianActivityService activityService;

    @Override
    public Long createActivityAppoint(ActivityAppointSaveReqVO createReqVO) {
        // 插入
        ActivityAppointDO activityAppoint = BeanUtils.toBean(createReqVO, ActivityAppointDO.class);
        activityAppointMapper.insert(activityAppoint);
        // 返回
        return activityAppoint.getId();
    }

    /**
     * 移动端报名
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    @Override
    public Long createActivityAppoint(AppActivityAppointSaveReqVO createReqVO) {
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        XinlianMemberDO memberDO = memberService.getMemberByMemberId(loginUser.getId());

        //判断是否能报名
        checkAppoint(createReqVO,memberDO);
        // 插入
        ActivityAppointDO activityAppoint = BeanUtils.toBean(createReqVO, ActivityAppointDO.class);
        activityAppoint.setMemberId(loginUser.getId());
        activityAppoint.setMemberName(memberDO.getName());
        activityAppointMapper.insert(activityAppoint);

        // 返回
        return activityAppoint.getId();
    }

    private void checkAppoint(AppActivityAppointSaveReqVO createReqVO,XinlianMemberDO memberDO)
    {
        XinlianActivityDO activityDO = activityService.getActivity(createReqVO.getActivityId());
        if (activityDO == null){
            throw exception(ACTIVITY_NOT_EXISTS);
        }
        if (memberDO == null){
            throw exception(MEMBER_NOT_EXISTS);
        }
        //判断报名时间是否超过
        if (LocalDateTime.now().isAfter(activityDO.getEndTime())){
            throw exception(ACTIVITY_APPOINT_INVALID);
        }
        Integer status = 2;
        if (activityDO.getIsLimit() && !status.equals(memberDO.getState())){
            throw exception(ACTIVITY_APPOINT_LIMIT_MEMBER);
        }

        //检查本人是否已有报名数据
        ActivityAppointPageReqVO req = new ActivityAppointPageReqVO();
        req = new ActivityAppointPageReqVO();
        req.setMemberId(SecurityFrameworkUtils.getLoginUserId());
        req.setActivityId(createReqVO.getActivityId());
        PageResult<ActivityAppointDO> selectPage = activityAppointMapper.selectPage(req);
        if (selectPage != null && selectPage.getTotal() > 0){
            if (selectPage.getTotal() >= activityDO.getLimitNum()){
                throw exception(ACTIVITY_APPOINT_HAS_EXIST);
            }
        }

        //获取已经报名的人数
        req = new ActivityAppointPageReqVO();
        req.setActivityId(createReqVO.getActivityId());
        req.setPageSize(1000);
        PageResult<ActivityAppointDO> pageResult = activityAppointMapper.selectPage(req);
        if (pageResult != null && pageResult.getTotal() > 0){
            if (pageResult.getTotal() >= activityDO.getLimitNum()){
                throw exception(ACTIVITY_APPOINT_LIMIT_MAX);
            }
        }

    }
    @Override
    public void updateActivityAppoint(ActivityAppointSaveReqVO updateReqVO) {
        // 校验存在
        validateActivityAppointExists(updateReqVO.getId());
        // 更新
        ActivityAppointDO updateObj = BeanUtils.toBean(updateReqVO, ActivityAppointDO.class);
        activityAppointMapper.updateById(updateObj);
    }

    @Override
    public void deleteActivityAppoint(Long id) {
        // 校验存在
        validateActivityAppointExists(id);
        // 删除
        activityAppointMapper.deleteById(id);
    }

    @Override
    public void batchDeleteActivityAppoint(List<Long> ids) {
        // 验证每个ID是否存在，可以优化为批量查询
        ids.forEach(this::validateActivityAppointExists);

        // 执行批量删除
        activityAppointMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchApproveActivityAppoint(List<Long> ids) {
        // 更新状态为已审批
        UpdateWrapper<ActivityAppointDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids); // 匹配ID列表中的ID
        updateWrapper.set("state", 2); // 设置状态为2（已审批）

        activityAppointMapper.update(null, updateWrapper);
    }

    private void validateActivityAppointExists(Long id) {
        if (activityAppointMapper.selectById(id) == null) {
            throw exception(ACTIVITY_APPOINT_NOT_EXISTS);
        }
    }

    @Override
    public ActivityAppointDO getActivityAppoint(Long id) {
        return activityAppointMapper.selectById(id);
    }

    @Override
    public PageResult<ActivityAppointDO> getActivityAppointPage(ActivityAppointPageReqVO pageReqVO) {
        return activityAppointMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ActivityAppointExtDO> getActivityAppointPageWithAct(ActivityAppointPageReqVO pageReqVO) {
        pageReqVO.setMemberId(SecurityFrameworkUtils.getLoginUserId());
        return activityAppointMapper.getActivityAppointPageWithAct(pageReqVO);
    }

    @Override
    public void updateaudit(ActivityAuditVO activityAuditVO) {
        // 根据 id 查询活动报名记录
        Long id = activityAuditVO.getId();
        ActivityAppointDO entity = activityAppointMapper.selectById(id);

        // 更新相关字段
        if (entity != null) {
            entity.setContact(activityAuditVO.getContact());
            entity.setTelephone(activityAuditVO.getTelephone());
            entity.setIdCard(activityAuditVO.getIdCard());
            entity.setState(activityAuditVO.getState());
            // 更新到数据库
            activityAppointMapper.updateById(entity);
        } else {
            throw new RuntimeException("未找到对应的活动报名记录，id：" + id);
        }
    }

}