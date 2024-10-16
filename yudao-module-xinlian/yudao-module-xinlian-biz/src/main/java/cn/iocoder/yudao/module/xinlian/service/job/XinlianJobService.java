package cn.iocoder.yudao.module.xinlian.service.job;

import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.job.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.job.XinlianJobDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory.NewsCategoryDO;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * 职务管理 Service 接口
 *
 * @author 芋道源码
 */
public interface XinlianJobService {

    /**
     * 创建职务管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createJob(@Valid JobSaveReqVO createReqVO);

    /**
     * 更新职务管理
     *
     * @param updateReqVO 更新信息
     */
    void updateJob(@Valid JobSaveReqVO updateReqVO);

    /**
     * 删除职务管理
     *
     * @param id 编号
     */
    void deleteJob(Long id);

    /**
     * 获得职务管理
     *
     * @param id 编号
     * @return 职务管理
     */
    XinlianJobDO getJob(Long id);

    /**
     * 获得职务管理分页
     *
     * @param pageReqVO 分页查询
     * @return 职务管理分页
     */
    PageResult<XinlianJobDO> getJobPage(JobPageReqVO pageReqVO);

    List<XinlianJobDO> getJobList();
}