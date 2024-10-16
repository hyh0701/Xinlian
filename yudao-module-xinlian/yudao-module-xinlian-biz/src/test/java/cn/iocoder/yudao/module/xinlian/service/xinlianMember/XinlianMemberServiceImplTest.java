package cn.iocoder.yudao.module.xinlian.service.xinlianMember;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.xinlianMember.XinlianMemberMapper;
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
 * {@link XinlianMemberServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(XinlianMemberServiceImpl.class)
public class XinlianMemberServiceImplTest extends BaseDbUnitTest {

    @Resource
    private XinlianMemberServiceImpl memberService;

    @Resource
    private XinlianMemberMapper memberMapper;

    @Test
    public void testCreateMember_success() {
        // 准备参数
        XinlianMemberSaveReqVO createReqVO = randomPojo(XinlianMemberSaveReqVO.class).setId(null);

        // 调用
        Long memberId = memberService.createMember(createReqVO);
        // 断言
        assertNotNull(memberId);
        // 校验记录的属性是否正确
        XinlianMemberDO member = memberMapper.selectById(memberId);
        assertPojoEquals(createReqVO, member, "id");
    }

    @Test
    public void testUpdateMember_success() {
        // mock 数据
        XinlianMemberDO dbMember = randomPojo(XinlianMemberDO.class);
        memberMapper.insert(dbMember);// @Sql: 先插入出一条存在的数据
        // 准备参数
        XinlianMemberSaveReqVO updateReqVO = randomPojo(XinlianMemberSaveReqVO.class, o -> {
            o.setId(dbMember.getId()); // 设置更新的 ID
        });

        // 调用
        memberService.updateMember(updateReqVO);
        // 校验是否更新正确
        XinlianMemberDO member = memberMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, member);
    }

    @Test
    public void testUpdateMember_notExists() {
        // 准备参数
        XinlianMemberSaveReqVO updateReqVO = randomPojo(XinlianMemberSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> memberService.updateMember(updateReqVO), MEMBER_NOT_EXISTS);
    }

    @Test
    public void testDeleteMember_success() {
        // mock 数据
        XinlianMemberDO dbMember = randomPojo(XinlianMemberDO.class);
        memberMapper.insert(dbMember);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbMember.getId();

        // 调用
        memberService.deleteMember(id);
       // 校验数据不存在了
       assertNull(memberMapper.selectById(id));
    }

    @Test
    public void testDeleteMember_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> memberService.deleteMember(id), MEMBER_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMemberPage() {
       // mock 数据
       XinlianMemberDO dbMember = randomPojo(XinlianMemberDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setSex(null);
           o.setAvatar(null);
           o.setPermitLogin(null);
           o.setTelephone(null);
           o.setMemberType(null);
           o.setMainTitle(null);
           o.setMainOrgId(null);
           o.setMainJobId(null);
           o.setState(null);
           o.setIsEnable(null);
           o.setCreateTime(null);
       });
       memberMapper.insert(dbMember);
       // 测试 name 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setName(null)));
       // 测试 sex 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setSex(null)));
       // 测试 avatar 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setAvatar(null)));
       // 测试 permitLogin 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setPermitLogin(null)));
       // 测试 telephone 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setTelephone(null)));
       // 测试 memberType 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setMemberType(null)));
       // 测试 mainTitle 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setMainTitle(null)));
       // 测试 mainOrgId 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setMainOrgId(null)));
       // 测试 mainJobId 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setMainJobId(null)));
       // 测试 state 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setState(null)));
       // 测试 isEnable 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setIsEnable(null)));
       // 测试 createTime 不匹配
       memberMapper.insert(cloneIgnoreId(dbMember, o -> o.setCreateTime(null)));
       // 准备参数
       XinlianMemberPageReqVO reqVO = new XinlianMemberPageReqVO();
       reqVO.setName(null);
       reqVO.setSex(null);
       reqVO.setAvatar(null);
       reqVO.setPermitLogin(null);
       reqVO.setTelephone(null);
       reqVO.setMemberType(null);
       reqVO.setMainTitle(null);
       reqVO.setMainOrgId(null);
       reqVO.setMainJobId(null);
       reqVO.setState(null);
       reqVO.setIsEnable(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<XinlianMemberDO> pageResult = memberService.getMemberPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbMember, pageResult.getList().get(0));
    }

}