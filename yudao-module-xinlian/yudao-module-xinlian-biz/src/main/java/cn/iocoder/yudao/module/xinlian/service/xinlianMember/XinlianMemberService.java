package cn.iocoder.yudao.module.xinlian.service.xinlianMember;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.*;
import cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo.AppXinlianMemberSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberExtDO;

/**
 * 新联会员 Service 接口
 *
 * @author 芋道源码
 */
public interface XinlianMemberService {

    /**
     * 创建新联会员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMember(@Valid XinlianMemberSaveReqVO createReqVO);

    /**
     * 更新新联会员
     *
     * @param updateReqVO 更新信息
     */
    boolean updateMember(@Valid XinlianMemberSaveReqVO updateReqVO);

    /**
     * 更新新联会员
     *
     * @param updateReqVO 更新信息
     */
    boolean updateMember(@Valid AppXinlianMemberSaveReqVO updateReqVO);

    /**
     * 删除新联会员
     *
     * @param id 编号
     */
    void deleteMember(Long id);


    /**
     * 批量删除新联会员
     *
     * @param ids
     */
    void batchDeleteMembers(List<Long> ids);

    /**
     * 批量审批新联会员
     *
     * @param ids
     */
    void batchApproveMembers(List<Long> ids);

    /**
     * 获得新联会员
     *
     * @param id 编号
     * @return 新联会员
     */
    XinlianMemberDO getMember(Long id);

    /**
     * 获得新联会员
     *
     * @param memberId 编号
     * @return 新联会员
     */
    XinlianMemberDO getMemberByMemberId(Long memberId);

    /**
     * 获得新联会员分页
     *
     * @param pageReqVO 分页查询
     * @return 新联会员分页
     */
    PageResult<XinlianMemberDO> getMemberPage(XinlianMemberPageReqVO pageReqVO);

    /**
     * 根据组织ID获取获得新联会员
     *
     * @param ids 分页查询
     * @param containSecondOrg 是否包含副组织
     * @return 新联会员分页
     */
    List<XinlianMemberDO> getMemberListByOrgIds(Set<Long> ids,boolean containSecondOrg);

    /**
     * 创建新联会员（小程序触发新建用户时）
     *
     * @param userId 系统用户ID
     * @return void
     */
    void createMemberByRegister(Long userId);

    /**
     * 获取首页的联系我们的会员列表
     *
     * @return 新联会员
     */
    List<XinlianMemberExtDO> getContact();

    /**
     * 获取地图上的标签
     *
     * @return 新联会员
     */
    List<XinlianMemberDO> getMapMarker();
}