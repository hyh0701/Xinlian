package cn.iocoder.yudao.module.xinlian.service.org;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.org.XinlianOrgMapper;
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
 * {@link XinlianOrgServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(XinlianOrgServiceImpl.class)
public class XinlianOrgServiceImplTest extends BaseDbUnitTest {

    @Resource
    private XinlianOrgServiceImpl orgService;

    @Resource
    private XinlianOrgMapper orgMapper;

    @Test
    public void testCreateOrg_success() {
        // 准备参数
        XinlianOrgSaveReqVO createReqVO = randomPojo(XinlianOrgSaveReqVO.class).setId(null);

        // 调用
        Long orgId = orgService.createOrg(createReqVO);
        // 断言
        assertNotNull(orgId);
        // 校验记录的属性是否正确
        XinlianOrgDO org = orgMapper.selectById(orgId);
        assertPojoEquals(createReqVO, org, "id");
    }

    @Test
    public void testUpdateOrg_success() {
        // mock 数据
        XinlianOrgDO dbOrg = randomPojo(XinlianOrgDO.class);
        orgMapper.insert(dbOrg);// @Sql: 先插入出一条存在的数据
        // 准备参数
        XinlianOrgSaveReqVO updateReqVO = randomPojo(XinlianOrgSaveReqVO.class, o -> {
            o.setId(dbOrg.getId()); // 设置更新的 ID
        });

        // 调用
        orgService.updateOrg(updateReqVO);
        // 校验是否更新正确
        XinlianOrgDO org = orgMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, org);
    }

    @Test
    public void testUpdateOrg_notExists() {
        // 准备参数
        XinlianOrgSaveReqVO updateReqVO = randomPojo(XinlianOrgSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> orgService.updateOrg(updateReqVO), ORG_NOT_EXISTS);
    }

    @Test
    public void testDeleteOrg_success() {
        // mock 数据
        XinlianOrgDO dbOrg = randomPojo(XinlianOrgDO.class);
        orgMapper.insert(dbOrg);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOrg.getId();

        // 调用
        orgService.deleteOrg(id);
       // 校验数据不存在了
       assertNull(orgMapper.selectById(id));
    }

    @Test
    public void testDeleteOrg_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> orgService.deleteOrg(id), ORG_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrgList() {
       // mock 数据
       XinlianOrgDO dbOrg = randomPojo(XinlianOrgDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setIsEnable(null);
           o.setRemark(null);
           o.setCreateTime(null);
       });
       orgMapper.insert(dbOrg);
       // 测试 name 不匹配
       orgMapper.insert(cloneIgnoreId(dbOrg, o -> o.setName(null)));
       // 测试 isEnable 不匹配
       orgMapper.insert(cloneIgnoreId(dbOrg, o -> o.setIsEnable(null)));
       // 测试 remark 不匹配
       orgMapper.insert(cloneIgnoreId(dbOrg, o -> o.setRemark(null)));
       // 测试 createTime 不匹配
       orgMapper.insert(cloneIgnoreId(dbOrg, o -> o.setCreateTime(null)));
       // 准备参数
       XinlianOrgListReqVO reqVO = new XinlianOrgListReqVO();
       reqVO.setName(null);
       reqVO.setIsEnable(null);
       reqVO.setRemark(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<XinlianOrgDO> list = orgService.getOrgList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbOrg, list.get(0));
    }

}