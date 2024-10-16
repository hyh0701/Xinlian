package cn.iocoder.yudao.module.xinlian.service.org;

import cn.iocoder.yudao.module.xinlian.controller.app.org.vo.AppXinlianOrgListReqVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.org.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.org.XinlianOrgDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.org.XinlianOrgMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 组织架构管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class XinlianOrgServiceImpl implements XinlianOrgService {

    @Resource
    private XinlianOrgMapper orgMapper;

    @Override
    public Long createOrg(XinlianOrgSaveReqVO createReqVO) {
        // 校验上级节点的有效性
        validateParentOrg(null, createReqVO.getParentId());
        // 校验组织名字的唯一性
        validateOrgNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        XinlianOrgDO org = BeanUtils.toBean(createReqVO, XinlianOrgDO.class);
        orgMapper.insert(org);
        // 返回
        return org.getId();
    }

    @Override
    public void updateOrg(XinlianOrgSaveReqVO updateReqVO) {
        // 校验存在
        validateOrgExists(updateReqVO.getId());
        // 校验上级节点的有效性
        validateParentOrg(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验组织名字的唯一性
        validateOrgNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        XinlianOrgDO updateObj = BeanUtils.toBean(updateReqVO, XinlianOrgDO.class);
        orgMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrg(Long id) {
        // 校验存在
        validateOrgExists(id);
        // 校验是否有子组织架构管理
        if (orgMapper.selectCountByParentId(id) > 0) {
            throw exception(ORG_EXITS_CHILDREN);
        }
        // 删除
        orgMapper.deleteById(id);
    }

    private void validateOrgExists(Long id) {
        if (orgMapper.selectById(id) == null) {
            throw exception(ORG_NOT_EXISTS);
        }
    }

    private void validateParentOrg(Long id, Long parentId) {
        if (parentId == null || XinlianOrgDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父组织架构管理
        if (Objects.equals(id, parentId)) {
            throw exception(ORG_PARENT_ERROR);
        }
        // 2. 父组织架构管理不存在
        XinlianOrgDO parentOrg = orgMapper.selectById(parentId);
        if (parentOrg == null) {
            throw exception(ORG_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父组织架构管理，如果父组织架构管理是自己的子组织架构管理，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentOrg.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(ORG_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父组织架构管理
            if (parentId == null || XinlianOrgDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentOrg = orgMapper.selectById(parentId);
            if (parentOrg == null) {
                break;
            }
        }
    }

    private void validateOrgNameUnique(Long id, Long parentId, String name) {
        XinlianOrgDO org = orgMapper.selectByParentIdAndName(parentId, name);
        if (org == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的组织架构管理
        if (id == null) {
            throw exception(ORG_NAME_DUPLICATE);
        }
        if (!Objects.equals(org.getId(), id)) {
            throw exception(ORG_NAME_DUPLICATE);
        }
    }

    @Override
    public XinlianOrgDO getOrg(Long id) {
        return orgMapper.selectById(id);
    }

    @Override
    public List<XinlianOrgDO> getOrgList(XinlianOrgListReqVO listReqVO) {
        return orgMapper.selectList(listReqVO);
    }

    /**
     * 获得组织架构管理列表
     *
     * @return 组织架构管理列表
     */
    @Override
    public List<XinlianOrgDO> getAppOrgList() {
        return orgMapper.selectApptList();
    }

    /**
     * 获得组织架构管理列表
     *
     * @return 组织架构管理列表
     */
    @Override
    public List<XinlianOrgDO> getOneAppOrgList() {
        return orgMapper.selectOrgListByParentId(0L);
    }

    /**
     * 获得二级组织架构管理列表
     *
     * @return 二级组织架构管理列表
     */
    @Override
    public List<XinlianOrgDO> getTwoAppOrgList() {
        return orgMapper.selectTwoApptList();
    }

    /**
     * 根据父ID获取组织管理列表
     * @param parentId 父ID
     * @return 组织架构管理列表
     */
    @Override
    public List<XinlianOrgDO> getAppOrgListByParentId(Long parentId) {
        return orgMapper.selectOrgListByParentId(parentId);
    }


    @Override
    public List<XinlianOrgDO> getOrgList() {
        return orgMapper.selectList();
    }

}