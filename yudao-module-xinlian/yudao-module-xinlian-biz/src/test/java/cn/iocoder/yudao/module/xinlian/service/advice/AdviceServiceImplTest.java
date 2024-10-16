package cn.iocoder.yudao.module.xinlian.service.advice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.advice.AdviceDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.advice.AdviceMapper;
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
 * {@link AdviceServiceImpl} 的单元测试类
 *
 * @author 平台用户
 */
@Import(AdviceServiceImpl.class)
public class AdviceServiceImplTest extends BaseDbUnitTest {

    @Resource
    private AdviceServiceImpl adviceService;

    @Resource
    private AdviceMapper adviceMapper;

    @Test
    public void testCreateAdvice_success() {
        // 准备参数
        AdviceSaveReqVO createReqVO = randomPojo(AdviceSaveReqVO.class).setId(null);

        // 调用
        Long adviceId = adviceService.createAdvice(createReqVO);
        // 断言
        assertNotNull(adviceId);
        // 校验记录的属性是否正确
        AdviceDO advice = adviceMapper.selectById(adviceId);
        assertPojoEquals(createReqVO, advice, "id");
    }

    @Test
    public void testUpdateAdvice_success() {
        // mock 数据
        AdviceDO dbAdvice = randomPojo(AdviceDO.class);
        adviceMapper.insert(dbAdvice);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AdviceSaveReqVO updateReqVO = randomPojo(AdviceSaveReqVO.class, o -> {
            o.setId(dbAdvice.getId()); // 设置更新的 ID
        });

        // 调用
        adviceService.updateAdvice(updateReqVO);
        // 校验是否更新正确
        AdviceDO advice = adviceMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, advice);
    }

    @Test
    public void testUpdateAdvice_notExists() {
        // 准备参数
        AdviceSaveReqVO updateReqVO = randomPojo(AdviceSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> adviceService.updateAdvice(updateReqVO), ADVICE_NOT_EXISTS);
    }

    @Test
    public void testDeleteAdvice_success() {
        // mock 数据
        AdviceDO dbAdvice = randomPojo(AdviceDO.class);
        adviceMapper.insert(dbAdvice);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbAdvice.getId();

        // 调用
        adviceService.deleteAdvice(id);
       // 校验数据不存在了
       assertNull(adviceMapper.selectById(id));
    }

    @Test
    public void testDeleteAdvice_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> adviceService.deleteAdvice(id), ADVICE_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetAdvicePage() {
       // mock 数据
       AdviceDO dbAdvice = randomPojo(AdviceDO.class, o -> { // 等会查询到
           o.setAdviceTitle(null);
           o.setAdviceType(null);
           o.setMemberName(null);
           o.setReplyUserName(null);
           o.setCreateTime(null);
       });
       adviceMapper.insert(dbAdvice);
       // 测试 adviceTitle 不匹配
       adviceMapper.insert(cloneIgnoreId(dbAdvice, o -> o.setAdviceTitle(null)));
       // 测试 adviceType 不匹配
       adviceMapper.insert(cloneIgnoreId(dbAdvice, o -> o.setAdviceType(null)));
       // 测试 memberName 不匹配
       adviceMapper.insert(cloneIgnoreId(dbAdvice, o -> o.setMemberName(null)));
       // 测试 replyUserName 不匹配
       adviceMapper.insert(cloneIgnoreId(dbAdvice, o -> o.setReplyUserName(null)));
       // 测试 createTime 不匹配
       adviceMapper.insert(cloneIgnoreId(dbAdvice, o -> o.setCreateTime(null)));
       // 准备参数
       AdvicePageReqVO reqVO = new AdvicePageReqVO();
       reqVO.setAdviceTitle(null);
       reqVO.setAdviceType(null);
       reqVO.setMemberName(null);
       reqVO.setReplyUserName(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<AdviceDO> pageResult = adviceService.getAdvicePage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbAdvice, pageResult.getList().get(0));
    }

}