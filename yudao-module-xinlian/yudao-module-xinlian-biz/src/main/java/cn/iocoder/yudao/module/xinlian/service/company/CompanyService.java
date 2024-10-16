package cn.iocoder.yudao.module.xinlian.service.company;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.company.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.company.CompanyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 企业会员 Service 接口
 *
 * @author 平台用户
 */
public interface CompanyService {

    /**
     * 创建企业会员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCompany(@Valid CompanySaveReqVO createReqVO);

    /**
     * 更新企业会员
     *
     * @param updateReqVO 更新信息
     */
    void updateCompany(@Valid CompanySaveReqVO updateReqVO);

    /**
     * 删除企业会员
     *
     * @param id 编号
     */
    void deleteCompany(Long id);

    /**
     * 获得企业会员
     *
     * @param id 编号
     * @return 企业会员
     */
    CompanyDO getCompany(Long id);

    /**
     * 获得企业会员分页
     *
     * @param pageReqVO 分页查询
     * @return 企业会员分页
     */
    PageResult<CompanyDO> getCompanyPage(CompanyPageReqVO pageReqVO);

}