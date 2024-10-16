package cn.iocoder.yudao.module.xinlian.service.activityappoint;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint.ActivityAppointDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.activityappoint.ActivityAppointMapper;
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
 * {@link ActivityAppointServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(ActivityAppointServiceImpl.class)
public class ActivityAppointServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ActivityAppointServiceImpl activityAppointService;

    @Resource
    private ActivityAppointMapper activityAppointMapper;

    @Test
    public void testCreateActivityAppoint_success() {
        // 准备参数
        ActivityAppointSaveReqVO createReqVO = randomPojo(ActivityAppointSaveReqVO.class).setId(null);

        // 调用
        Long activityAppointId = activityAppointService.createActivityAppoint(createReqVO);
        // 断言
        assertNotNull(activityAppointId);
        // 校验记录的属性是否正确
        ActivityAppointDO activityAppoint = activityAppointMapper.selectById(activityAppointId);
        assertPojoEquals(createReqVO, activityAppoint, "id");
    }

    @Test
    public void testUpdateActivityAppoint_success() {
        // mock 数据
        ActivityAppointDO dbActivityAppoint = randomPojo(ActivityAppointDO.class);
        activityAppointMapper.insert(dbActivityAppoint);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ActivityAppointSaveReqVO updateReqVO = randomPojo(ActivityAppointSaveReqVO.class, o -> {
            o.setId(dbActivityAppoint.getId()); // 设置更新的 ID
        });

        // 调用
        activityAppointService.updateActivityAppoint(updateReqVO);
        // 校验是否更新正确
        ActivityAppointDO activityAppoint = activityAppointMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, activityAppoint);
    }

    @Test
    public void testUpdateActivityAppoint_notExists() {
        // 准备参数
        ActivityAppointSaveReqVO updateReqVO = randomPojo(ActivityAppointSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> activityAppointService.updateActivityAppoint(updateReqVO), ACTIVITY_APPOINT_NOT_EXISTS);
    }

    @Test
    public void testDeleteActivityAppoint_success() {
        // mock 数据
        ActivityAppointDO dbActivityAppoint = randomPojo(ActivityAppointDO.class);
        activityAppointMapper.insert(dbActivityAppoint);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbActivityAppoint.getId();

        // 调用
        activityAppointService.deleteActivityAppoint(id);
       // 校验数据不存在了
       assertNull(activityAppointMapper.selectById(id));
    }

    @Test
    public void testDeleteActivityAppoint_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> activityAppointService.deleteActivityAppoint(id), ACTIVITY_APPOINT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetActivityAppointPage() {
       // mock 数据
       ActivityAppointDO dbActivityAppoint = randomPojo(ActivityAppointDO.class, o -> { // 等会查询到
           o.setActivityId(null);
           o.setMemberName(null);
           o.setContact(null);
           o.setTelephone(null);
           o.setState(null);
           o.setPayState(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       activityAppointMapper.insert(dbActivityAppoint);
       // 测试 activityId 不匹配
       activityAppointMapper.insert(cloneIgnoreId(dbActivityAppoint, o -> o.setActivityId(null)));
       // 测试 memberName 不匹配
       activityAppointMapper.insert(cloneIgnoreId(dbActivityAppoint, o -> o.setMemberName(null)));
       // 测试 contact 不匹配
       activityAppointMapper.insert(cloneIgnoreId(dbActivityAppoint, o -> o.setContact(null)));
       // 测试 telephone 不匹配
       activityAppointMapper.insert(cloneIgnoreId(dbActivityAppoint, o -> o.setTelephone(null)));
       // 测试 state 不匹配
       activityAppointMapper.insert(cloneIgnoreId(dbActivityAppoint, o -> o.setState(null)));
       // 测试 payState 不匹配
       activityAppointMapper.insert(cloneIgnoreId(dbActivityAppoint, o -> o.setPayState(null)));
       // 测试 isLimit 不匹配
       // 测试 remark 不匹配
       activityAppointMapper.insert(cloneIgnoreId(dbActivityAppoint, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       activityAppointMapper.insert(cloneIgnoreId(dbActivityAppoint, o -> o.setCreateTime(null)));
       // 准备参数
       ActivityAppointPageReqVO reqVO = new ActivityAppointPageReqVO();
       reqVO.setActivityId(null);
       reqVO.setMemberName(null);
       reqVO.setContact(null);
       reqVO.setTelephone(null);
       reqVO.setState(null);
       reqVO.setPayState(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ActivityAppointDO> pageResult = activityAppointService.getActivityAppointPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbActivityAppoint, pageResult.getList().get(0));
    }

}