package cn.iocoder.yudao.module.xinlian.service.activity;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.activity.XinlianActivityMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 活动记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class XinlianActivityServiceImpl implements XinlianActivityService {

    @Resource
    private XinlianActivityMapper activityMapper;

    @Override
    public Long createActivity(XinlianActivitySaveReqVO createReqVO) {
        // 插入
        XinlianActivityDO activity = BeanUtils.toBean(createReqVO, XinlianActivityDO.class);
        activityMapper.insert(activity);
        // 返回
        return activity.getId();
    }

    @Override
    public void updateActivity(XinlianActivitySaveReqVO updateReqVO) {
        // 校验存在
        validateActivityExists(updateReqVO.getId());
        // 更新
        XinlianActivityDO updateObj = BeanUtils.toBean(updateReqVO, XinlianActivityDO.class);
        activityMapper.updateById(updateObj);
    }

    @Override
    public void deleteActivity(Long id) {
        // 校验存在
        validateActivityExists(id);
        // 删除
        activityMapper.deleteById(id);
    }

    @Override
    public void batchDeleteActivity(List<Long> ids) {
        // 验证每个ID是否存在，可以优化为批量查询
        ids.forEach(this::validateActivityExists);

        // 执行批量删除
        activityMapper.deleteBatchIds(ids);
    }

    private void validateActivityExists(Long id) {
        if (activityMapper.selectById(id) == null) {
            throw exception(ACTIVITY_NOT_EXISTS);
        }
    }

    @Override
    public XinlianActivityDO getActivity(Long id) {
        return activityMapper.selectById(id);
    }

    @Override
    public PageResult<XinlianActivityDO> getActivityPage(XinlianActivityPageReqVO pageReqVO) {
        return activityMapper.selectPage(pageReqVO);
    }

    @Override
    public CommonResult<String> getActivityNameById(Long id) {
        XinlianActivityDO activity = activityMapper.selectById(id);
        if (activity != null) {
            return CommonResult.success(activity.getActivityName());
        } else {
            return CommonResult.error(404, "未知活动");
        }

    }

    @Override
    public List<XinlianActivityDO> getActivityList() {
        return activityMapper.selectList();
    }
}