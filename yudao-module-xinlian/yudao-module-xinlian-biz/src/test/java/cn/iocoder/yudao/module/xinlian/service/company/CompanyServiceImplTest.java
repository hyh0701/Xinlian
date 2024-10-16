package cn.iocoder.yudao.module.xinlian.service.company;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.xinlian.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.module.xinlian.dal.mysql.company.CompanyMapper;
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
 * {@link CompanyServiceImpl} 的单元测试类
 *
 * @author 平台用户
 */
@Import(CompanyServiceImpl.class)
public class CompanyServiceImplTest extends BaseDbUnitTest {

    @Resource
    private CompanyServiceImpl companyService;

    @Resource
    private CompanyMapper companyMapper;

    @Test
    public void testCreateCompany_success() {
        // 准备参数
        CompanySaveReqVO createReqVO = randomPojo(CompanySaveReqVO.class).setId(null);

        // 调用
        Long companyId = companyService.createCompany(createReqVO);
        // 断言
        assertNotNull(companyId);
        // 校验记录的属性是否正确
        CompanyDO company = companyMapper.selectById(companyId);
        assertPojoEquals(createReqVO, company, "id");
    }

    @Test
    public void testUpdateCompany_success() {
        // mock 数据
        CompanyDO dbCompany = randomPojo(CompanyDO.class);
        companyMapper.insert(dbCompany);// @Sql: 先插入出一条存在的数据
        // 准备参数
        CompanySaveReqVO updateReqVO = randomPojo(CompanySaveReqVO.class, o -> {
            o.setId(dbCompany.getId()); // 设置更新的 ID
        });

        // 调用
        companyService.updateCompany(updateReqVO);
        // 校验是否更新正确
        CompanyDO company = companyMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, company);
    }

    @Test
    public void testUpdateCompany_notExists() {
        // 准备参数
        CompanySaveReqVO updateReqVO = randomPojo(CompanySaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> companyService.updateCompany(updateReqVO), COMPANY_NOT_EXISTS);
    }

    @Test
    public void testDeleteCompany_success() {
        // mock 数据
        CompanyDO dbCompany = randomPojo(CompanyDO.class);
        companyMapper.insert(dbCompany);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbCompany.getId();

        // 调用
        companyService.deleteCompany(id);
       // 校验数据不存在了
       assertNull(companyMapper.selectById(id));
    }

    @Test
    public void testDeleteCompany_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> companyService.deleteCompany(id), COMPANY_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetCompanyPage() {
       // mock 数据
       CompanyDO dbCompany = randomPojo(CompanyDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setOrgId(null);
           o.setCreateTime(null);
       });
       companyMapper.insert(dbCompany);
       // 测试 name 不匹配
       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setName(null)));
       // 测试 orgId 不匹配
       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setOrgId(null)));
       // 测试 createTime 不匹配
       companyMapper.insert(cloneIgnoreId(dbCompany, o -> o.setCreateTime(null)));
       // 准备参数
       CompanyPageReqVO reqVO = new CompanyPageReqVO();
       reqVO.setName(null);
       reqVO.setOrgId(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<CompanyDO> pageResult = companyService.getCompanyPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbCompany, pageResult.getList().get(0));
    }

}