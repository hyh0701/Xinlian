package cn.iocoder.yudao.module.xinlian.service.job;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.job.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.job.XinlianJobDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.job.XinlianJobMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.springframework.context.annotation.Import;

import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link XinlianJobServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(XinlianJobServiceImpl.class)
public class XinlianXinlianJobServiceImplTest extends BaseDbUnitTest {

    @Resource
    private XinlianJobServiceImpl jobService;

    @Resource
    private XinlianJobMapper xinlianJobMapper;

    @Test
    public void testCreateJob_success() {
        // 准备参数
        JobSaveReqVO createReqVO = randomPojo(JobSaveReqVO.class).setId(null);

        // 调用
        Long jobId = jobService.createJob(createReqVO);
        // 断言
        assertNotNull(jobId);
        // 校验记录的属性是否正确
        XinlianJobDO job = xinlianJobMapper.selectById(jobId);
        assertPojoEquals(createReqVO, job, "id");
    }

    @Test
    public void testUpdateJob_success() {
        // mock 数据
        XinlianJobDO dbJob = randomPojo(XinlianJobDO.class);
        xinlianJobMapper.insert(dbJob);// @Sql: 先插入出一条存在的数据
        // 准备参数
        JobSaveReqVO updateReqVO = randomPojo(JobSaveReqVO.class, o -> {
            o.setId(dbJob.getId()); // 设置更新的 ID
        });

        // 调用
        jobService.updateJob(updateReqVO);
        // 校验是否更新正确
        XinlianJobDO job = xinlianJobMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, job);
    }

    @Test
    public void testUpdateJob_notExists() {
        // 准备参数
        JobSaveReqVO updateReqVO = randomPojo(JobSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> jobService.updateJob(updateReqVO), JOB_NOT_EXISTS);
    }

    @Test
    public void testDeleteJob_success() {
        // mock 数据
        XinlianJobDO dbJob = randomPojo(XinlianJobDO.class);
        xinlianJobMapper.insert(dbJob);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbJob.getId();

        // 调用
        jobService.deleteJob(id);
       // 校验数据不存在了
       assertNull(xinlianJobMapper.selectById(id));
    }

    @Test
    public void testDeleteJob_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> jobService.deleteJob(id), JOB_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetJobPage() {
       // mock 数据
       XinlianJobDO dbJob = randomPojo(XinlianJobDO.class, o -> { // 等会查询到
           o.setIsEnable(null);
           o.setCreateTime(null);
           o.setName(null);
       });
       xinlianJobMapper.insert(dbJob);
       // 测试 isEnable 不匹配
       xinlianJobMapper.insert(cloneIgnoreId(dbJob, o -> o.setIsEnable(null)));
       // 测试 createTime 不匹配
       xinlianJobMapper.insert(cloneIgnoreId(dbJob, o -> o.setCreateTime(null)));
       // 测试 name 不匹配
       xinlianJobMapper.insert(cloneIgnoreId(dbJob, o -> o.setName(null)));
       // 准备参数
       JobPageReqVO reqVO = new JobPageReqVO();
       reqVO.setIsEnable(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setName(null);

       // 调用
       PageResult<XinlianJobDO> pageResult = jobService.getJobPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbJob, pageResult.getList().get(0));
    }

}