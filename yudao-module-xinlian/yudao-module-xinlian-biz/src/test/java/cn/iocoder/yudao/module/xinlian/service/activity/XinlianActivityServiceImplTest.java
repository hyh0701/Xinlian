package cn.iocoder.yudao.module.xinlian.service.activity;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.activity.XinlianActivityMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link XinlianActivityServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(XinlianActivityServiceImpl.class)
public class XinlianActivityServiceImplTest extends BaseDbUnitTest {

    @Resource
    private XinlianActivityServiceImpl activityService;

    @Resource
    private XinlianActivityMapper activityMapper;

    @Test
    public void testCreateActivity_success() {
        // 准备参数
        XinlianActivitySaveReqVO createReqVO = randomPojo(XinlianActivitySaveReqVO.class).setId(null);

        // 调用
        Long activityId = activityService.createActivity(createReqVO);
        // 断言
        assertNotNull(activityId);
        // 校验记录的属性是否正确
        XinlianActivityDO activity = activityMapper.selectById(activityId);
        assertPojoEquals(createReqVO, activity, "id");
    }

    @Test
    public void testUpdateActivity_success() {
        // mock 数据
        XinlianActivityDO dbActivity = randomPojo(XinlianActivityDO.class);
        activityMapper.insert(dbActivity);// @Sql: 先插入出一条存在的数据
        // 准备参数
        XinlianActivitySaveReqVO updateReqVO = randomPojo(XinlianActivitySaveReqVO.class, o -> {
            o.setId(dbActivity.getId()); // 设置更新的 ID
        });

        // 调用
        activityService.updateActivity(updateReqVO);
        // 校验是否更新正确
        XinlianActivityDO activity = activityMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, activity);
    }

    @Test
    public void testUpdateActivity_notExists() {
        // 准备参数
        XinlianActivitySaveReqVO updateReqVO = randomPojo(XinlianActivitySaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> activityService.updateActivity(updateReqVO), ACTIVITY_NOT_EXISTS);
    }

    @Test
    public void testDeleteActivity_success() {
        // mock 数据
        XinlianActivityDO dbActivity = randomPojo(XinlianActivityDO.class);
        activityMapper.insert(dbActivity);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbActivity.getId();

        // 调用
        activityService.deleteActivity(id);
       // 校验数据不存在了
       assertNull(activityMapper.selectById(id));
    }

    @Test
    public void testDeleteActivity_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> activityService.deleteActivity(id), ACTIVITY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetActivityPage() {
       // mock 数据
       XinlianActivityDO dbActivity = randomPojo(XinlianActivityDO.class, o -> { // 等会查询到
           o.setActivityName(null);
           o.setSponsor(null);
           o.setActivityType(null);
           o.setStartTime(null);
           o.setContact(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       activityMapper.insert(dbActivity);
       // 测试 activityName 不匹配
       activityMapper.insert(cloneIgnoreId(dbActivity, o -> o.setActivityName(null)));
       // 测试 sponsor 不匹配
       activityMapper.insert(cloneIgnoreId(dbActivity, o -> o.setSponsor(null)));
       // 测试 activityType 不匹配
       activityMapper.insert(cloneIgnoreId(dbActivity, o -> o.setActivityType(null)));
       // 测试 startTime 不匹配
       activityMapper.insert(cloneIgnoreId(dbActivity, o -> o.setStartTime(null)));
       // 测试 contact 不匹配
       activityMapper.insert(cloneIgnoreId(dbActivity, o -> o.setContact(null)));
       // 测试 remark 不匹配
       activityMapper.insert(cloneIgnoreId(dbActivity, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       activityMapper.insert(cloneIgnoreId(dbActivity, o -> o.setCreateTime(null)));
       // 准备参数
       XinlianActivityPageReqVO reqVO = new XinlianActivityPageReqVO();
       reqVO.setActivityName(null);
       reqVO.setSponsor(null);
       reqVO.setActivityType(null);
       reqVO.setStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setContact(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<XinlianActivityDO> pageResult = activityService.getActivityPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbActivity, pageResult.getList().get(0));
    }

}