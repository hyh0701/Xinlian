package cn.iocoder.yudao.module.xinlian.service.activity;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 活动记录 Service 接口
 *
 * @author 芋道源码
 */
public interface XinlianActivityService {

    /**
     * 创建活动记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createActivity(@Valid XinlianActivitySaveReqVO createReqVO);

    /**
     * 更新活动记录
     *
     * @param updateReqVO 更新信息
     */
    void updateActivity(@Valid XinlianActivitySaveReqVO updateReqVO);

    /**
     * 删除活动记录
     *
     * @param id 编号
     */
    void deleteActivity(Long id);

    /**
     * 批量删除活动记录
     *
     * @param ids
     */
    void batchDeleteActivity(List<Long> ids);

    /**
     * 获得活动记录
     *
     * @param id 编号
     * @return 活动记录
     */
    XinlianActivityDO getActivity(Long id);

    /**
     * 获得活动记录分页
     *
     * @param pageReqVO 分页查询
     * @return 活动记录分页
     */
    PageResult<XinlianActivityDO> getActivityPage(XinlianActivityPageReqVO pageReqVO);

    CommonResult<String> getActivityNameById(Long id);


    List<XinlianActivityDO> getActivityList();
}