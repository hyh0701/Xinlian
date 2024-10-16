package cn.iocoder.yudao.module.xinlian.service.job;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import cn.iocoder.yudao.module.xinlian.controller.admin.job.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.job.XinlianJobDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.job.XinlianJobMapper;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 职务管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class XinlianJobServiceImpl implements XinlianJobService {

    @Resource
    private XinlianJobMapper xinlianJobMapper;

    @Override
    public Long createJob(JobSaveReqVO createReqVO) {
        // 插入
        XinlianJobDO job = BeanUtils.toBean(createReqVO, XinlianJobDO.class);
        xinlianJobMapper.insert(job);
        // 返回
        return job.getId();
    }

    @Override
    public void updateJob(JobSaveReqVO updateReqVO) {
        // 校验存在
        validateJobExists(updateReqVO.getId());
        // 更新
        XinlianJobDO updateObj = BeanUtils.toBean(updateReqVO, XinlianJobDO.class);
        xinlianJobMapper.updateById(updateObj);
    }

    @Override
    public void deleteJob(Long id) {
        // 校验存在
        validateJobExists(id);
        // 删除
        xinlianJobMapper.deleteById(id);
    }

    private void validateJobExists(Long id) {
        if (xinlianJobMapper.selectById(id) == null) {
            throw exception(JOB_NOT_EXISTS);
        }
    }

    @Override
    public XinlianJobDO getJob(Long id) {
        return xinlianJobMapper.selectById(id);
    }

    @Override
    public PageResult<XinlianJobDO> getJobPage(JobPageReqVO pageReqVO) {
        return xinlianJobMapper.selectPage(pageReqVO);
    }

    @Override
    public List<XinlianJobDO> getJobList(){
        return xinlianJobMapper.getJobList();
    }
}