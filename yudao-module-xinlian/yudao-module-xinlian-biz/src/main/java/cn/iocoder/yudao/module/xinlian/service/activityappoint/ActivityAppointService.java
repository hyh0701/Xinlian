package cn.iocoder.yudao.module.xinlian.service.activityappoint;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo.*;
import cn.iocoder.yudao.module.xinlian.controller.app.activity.vo.AppActivityAppointSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointExtDO;

/**
 * 活动报名 Service 接口
 *
 * @author 芋道源码
 */
public interface ActivityAppointService {

    /**
     * 创建活动报名
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createActivityAppoint(@Valid ActivityAppointSaveReqVO createReqVO);

    /**
     * 移动端报名
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createActivityAppoint(@Valid AppActivityAppointSaveReqVO createReqVO);

    /**
     * 更新活动报名
     *
     * @param updateReqVO 更新信息
     */
    void updateActivityAppoint(@Valid ActivityAppointSaveReqVO updateReqVO);

    /**
     * 删除活动报名
     *
     * @param id 编号
     */
    void deleteActivityAppoint(Long id);

    /**
     * 批量删除活动报名
     *
     * @param ids
     */
    void batchDeleteActivityAppoint(List<Long> ids);

    /**
     * 批量审批新联会员
     *
     * @param ids
     */
    void batchApproveActivityAppoint(List<Long> ids);

    /**
     * 获得活动报名
     *
     * @param id 编号
     * @return 活动报名
     */
    ActivityAppointDO getActivityAppoint(Long id);

    /**
     * 获得活动报名分页
     *
     * @param pageReqVO 分页查询
     * @return 活动报名分页
     */
    PageResult<ActivityAppointDO> getActivityAppointPage(ActivityAppointPageReqVO pageReqVO);

    /**
     * 获得活动报名分页带活动详情
     *
     * @param pageReqVO 分页查询
     * @return 活动报名分页
     */
    List<ActivityAppointExtDO> getActivityAppointPageWithAct(ActivityAppointPageReqVO pageReqVO);


    void updateaudit(ActivityAuditVO activityAuditVO);
}