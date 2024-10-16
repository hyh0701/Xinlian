package cn.iocoder.yudao.module.xinlian.service.appoint;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.appoint.AppointDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 绩效计分 Service 接口
 *
 * @author 芋道源码
 */
public interface AppointService {

    /**
     * 创建绩效计分
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAppoint(@Valid AppointSaveReqVO createReqVO);

    /**
     * 创建绩效计分
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAppAppoint(@Valid AppointSaveReqVO createReqVO);

    /**
     * 更新绩效计分
     *
     * @param updateReqVO 更新信息
     */
    void updateAppoint(@Valid AppointSaveReqVO updateReqVO);

    /**
     * 更新绩效计分
     *
     * @param updateReqVO 更新信息
     */
    void updateAppAppoint(@Valid AppointSaveReqVO updateReqVO);
    /**
     * 删除绩效计分
     *
     * @param id 编号
     */
    void deleteAppoint(Long id);

    /**
     * 获得绩效计分
     *
     * @param id 编号
     * @return 绩效计分
     */
    AppointDO getAppoint(Long id);

    /**
     * 获得绩效计分分页
     *
     * @param pageReqVO 分页查询
     * @return 绩效计分分页
     */
    PageResult<AppointDO> getAppointPage(AppointPageReqVO pageReqVO);



    /**
     * 获得绩效计分分页
     *
     * @param pageReqVO 分页查询
     * @return 绩效计分分页
     */
    PageResult<AppointDO> getAppAppointPage(AppointPageReqVO pageReqVO);
}