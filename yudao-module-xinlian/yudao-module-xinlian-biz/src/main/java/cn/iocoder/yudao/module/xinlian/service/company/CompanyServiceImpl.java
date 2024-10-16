package cn.iocoder.yudao.module.xinlian.service.company;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.company.CompanyMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 企业会员 Service 实现类
 *
 * @author 平台用户
 */
@Service
@Validated
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public Long createCompany(CompanySaveReqVO createReqVO) {
        // 插入
        CompanyDO company = BeanUtils.toBean(createReqVO, CompanyDO.class);
        companyMapper.insert(company);
        // 返回
        return company.getId();
    }

    @Override
    public void updateCompany(CompanySaveReqVO updateReqVO) {
        // 校验存在
        validateCompanyExists(updateReqVO.getId());
        // 更新
        CompanyDO updateObj = BeanUtils.toBean(updateReqVO, CompanyDO.class);
        companyMapper.updateById(updateObj);
    }

    @Override
    public void deleteCompany(Long id) {
        // 校验存在
        validateCompanyExists(id);
        // 删除
        companyMapper.deleteById(id);
    }

    private void validateCompanyExists(Long id) {
        if (companyMapper.selectById(id) == null) {
            throw exception(COMPANY_NOT_EXISTS);
        }
    }

    @Override
    public CompanyDO getCompany(Long id) {
        return companyMapper.selectById(id);
    }

    @Override
    public PageResult<CompanyDO> getCompanyPage(CompanyPageReqVO pageReqVO) {
        return companyMapper.selectPage(pageReqVO);
    }

}