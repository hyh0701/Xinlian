package cn.iocoder.yudao.module.xinlian.service.appoint;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.appoint.AppointDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.appoint.AppointMapper;
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
 * {@link AppointServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(AppointServiceImpl.class)
public class AppointServiceImplTest extends BaseDbUnitTest {

    @Resource
    private AppointServiceImpl appointService;

    @Resource
    private AppointMapper appointMapper;

    @Test
    public void testCreateAppoint_success() {
        // 准备参数
        AppointSaveReqVO createReqVO = randomPojo(AppointSaveReqVO.class).setId(null);

        // 调用
        Long appointId = appointService.createAppoint(createReqVO);
        // 断言
        assertNotNull(appointId);
        // 校验记录的属性是否正确
        AppointDO appoint = appointMapper.selectById(appointId);
        assertPojoEquals(createReqVO, appoint, "id");
    }

    @Test
    public void testUpdateAppoint_success() {
        // mock 数据
        AppointDO dbAppoint = randomPojo(AppointDO.class);
        appointMapper.insert(dbAppoint);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AppointSaveReqVO updateReqVO = randomPojo(AppointSaveReqVO.class, o -> {
            o.setId(dbAppoint.getId()); // 设置更新的 ID
        });

        // 调用
        appointService.updateAppoint(updateReqVO);
        // 校验是否更新正确
        AppointDO appoint = appointMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, appoint);
    }

    @Test
    public void testUpdateAppoint_notExists() {
        // 准备参数
        AppointSaveReqVO updateReqVO = randomPojo(AppointSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> appointService.updateAppoint(updateReqVO), APPOINT_NOT_EXISTS);
    }

    @Test
    public void testDeleteAppoint_success() {
        // mock 数据
        AppointDO dbAppoint = randomPojo(AppointDO.class);
        appointMapper.insert(dbAppoint);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbAppoint.getId();

        // 调用
        appointService.deleteAppoint(id);
       // 校验数据不存在了
       assertNull(appointMapper.selectById(id));
    }

    @Test
    public void testDeleteAppoint_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> appointService.deleteAppoint(id), APPOINT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetAppointPage() {
       // mock 数据
       AppointDO dbAppoint = randomPojo(AppointDO.class, o -> { // 等会查询到
           o.setMemberId(null);
           o.setAppointIntro(null);
           o.setAppointDate(null);
           o.setAppointScore(null);
           o.setAppointArticle(null);
           o.setAppointPlan(null);
           o.setState(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       appointMapper.insert(dbAppoint);
       // 测试 memberId 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setMemberId(null)));
       // 测试 appointIntro 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setAppointIntro(null)));
       // 测试 appointDate 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setAppointDate(null)));
       // 测试 appointScore 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setAppointScore(null)));
       // 测试 appointArticle 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setAppointArticle(null)));
       // 测试 appointPlan 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setAppointPlan(null)));
       // 测试 state 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setState(null)));
       // 测试 remark 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       appointMapper.insert(cloneIgnoreId(dbAppoint, o -> o.setCreateTime(null)));
       // 准备参数
       AppointPageReqVO reqVO = new AppointPageReqVO();
       reqVO.setMemberId(null);
       reqVO.setAppointIntro(null);
       reqVO.setAppointDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setAppointScore(null);
       reqVO.setAppointArticle(null);
       reqVO.setAppointPlan(null);
       reqVO.setState(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<AppointDO> pageResult = appointService.getAppointPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbAppoint, pageResult.getList().get(0));
    }

}