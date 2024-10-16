package cn.iocoder.yudao.module.xinlian.service.org;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.*;
import cn.iocoder.yudao.module.xinlian.controller.app.org.vo.AppXinlianOrgListReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 组织架构管理 Service 接口
 *
 * @author 芋道源码
 */
public interface XinlianOrgService {

    /**
     * 创建组织架构管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrg(@Valid XinlianOrgSaveReqVO createReqVO);

    /**
     * 更新组织架构管理
     *
     * @param updateReqVO 更新信息
     */
    void updateOrg(@Valid XinlianOrgSaveReqVO updateReqVO);

    /**
     * 删除组织架构管理
     *
     * @param id 编号
     */
    void deleteOrg(Long id);

    /**
     * 获得组织架构管理
     *
     * @param id 编号
     * @return 组织架构管理
     */
    XinlianOrgDO getOrg(Long id);

    /**
     * 获得组织架构管理列表
     *
     * @param listReqVO 查询条件
     * @return 组织架构管理列表
     */
    List<XinlianOrgDO> getOrgList(XinlianOrgListReqVO listReqVO);

    /**
     * 获得组织架构管理列表
     *
     * @return 组织架构管理列表
     */
    List<XinlianOrgDO> getAppOrgList();

    /**
     * 获得一级组织架构管理列表
     *
     * @return 一级组织架构管理列表
     */
    List<XinlianOrgDO> getOneAppOrgList();
    /**
     * 获得二级组织架构管理列表
     *
     * @return 二级组织架构管理列表
     */
    List<XinlianOrgDO> getTwoAppOrgList();
    /**
     * 根据父ID获取组织管理列表
     * @param parentId 父ID
     * @return 组织架构管理列表
     */
    List<XinlianOrgDO> getAppOrgListByParentId(Long parentId);

    List<XinlianOrgDO> getOrgList();
}