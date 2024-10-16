package cn.iocoder.yudao.module.xinlian.service.xinlianMember;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.infra.dal.dataobject.config.ConfigDO;
import cn.iocoder.yudao.module.infra.service.config.ConfigService;
import cn.iocoder.yudao.module.member.api.user.MemberUserApi;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import cn.iocoder.yudao.module.system.service.user.AdminUserService;
import cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo.AppXinlianMemberSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberExtDO;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo.*;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.xinlian.dal.mysql.xinlianMember.XinlianMemberMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_MOBILE_EXISTS;
import static cn.iocoder.yudao.module.xinlian.enums.ErrorCodeConstants.*;

/**
 * 新联会员 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class XinlianMemberServiceImpl implements XinlianMemberService {

    @Resource
    private XinlianMemberMapper memberMapper;

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    AdminUserService adminUserService;
    @Resource
    private ConfigService configService;

    @Override
    public Long createMember(XinlianMemberSaveReqVO createReqVO) {

        // 插入
        XinlianMemberDO member = BeanUtils.toBean(createReqVO, XinlianMemberDO.class);
        if (member.getPermitLogin()){
            Long userId = createSystemUser(createReqVO);
            if (userId < 0L){
                throw exception(Failed_Create_System_User);
            }
            member.setUserId(userId);
        }
        if(validateMobileUnique(member.getTelephone())){
            throw exception(MEMBER_Mobile_HAS_EXISTS);
        }
        memberMapper.insert(member);
        // 返回
        return member.getId();
    }

    @Override
    public boolean updateMember(XinlianMemberSaveReqVO updateReqVO) {
        // 校验存在
        validateMemberExists(updateReqVO.getId());
        // 更新
        XinlianMemberDO updateObj = BeanUtils.toBean(updateReqVO, XinlianMemberDO.class);
        if (updateObj.getPermitLogin() && StringUtils.isNotEmpty(updateObj.getTelephone())){
            AdminUserDO adminUserDO = adminUserService.getUserByMobile(updateObj.getTelephone());
            if (adminUserDO == null){
                Long userId = createSystemUser(updateReqVO);
                if (userId < 0L){
                    throw exception(Failed_Create_System_User);
                }
                updateObj.setUserId(userId);
            }
            else{
                if (StringUtils.isNotEmpty(updateReqVO.getPassword())){
                    //如果设置了密码,则更新系统密码
                    adminUserService.updateUserPassword(adminUserDO.getId(),updateReqVO.getPassword());
                }
            }
        }
        memberMapper.updateById(updateObj);
        return true;
    }

    @Override
    public boolean updateMember(AppXinlianMemberSaveReqVO updateReqVO) {
        // 校验存在
        validateMemberExists(updateReqVO.getId());
        // 更新
        XinlianMemberDO updateObj = BeanUtils.toBean(updateReqVO, XinlianMemberDO.class);
        memberMapper.updateById(updateObj);
        return true;
    }

    @Override
    public void deleteMember(Long id) {
        // 校验存在
        validateMemberExists(id);
        // 删除
        memberMapper.deleteById(id);
    }

    @Override
    public void batchDeleteMembers(List<Long> ids) {
        // 验证每个ID是否存在，可以优化为批量查询
        ids.forEach(this::validateMemberExists);

        // 执行批量删除
        memberMapper.deleteBatchIds(ids);
    }

    @Override
    public void batchApproveMembers(List<Long> ids) {
        // 更新状态为已审批
        UpdateWrapper<XinlianMemberDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids); // 匹配ID列表中的ID
        updateWrapper.set("state", 2); // 设置状态为2（已审批）

        memberMapper.update(null, updateWrapper);
    }

    private void validateMemberExists(Long id) {
        if (memberMapper.selectById(id) == null) {
            throw exception(MEMBER_NOT_EXISTS);
        }
    }

    @Override
    public XinlianMemberDO getMember(Long id) {
        return memberMapper.selectById(id);
    }

    /**
     * 获得新联会员
     *
     * @param memberId 编号
     * @return 新联会员
     */
    @Override
    public XinlianMemberDO getMemberByMemberId(Long memberId) {
        return memberMapper.selectByMemberUserId(memberId);
    }

    @Override
    public PageResult<XinlianMemberDO> getMemberPage(XinlianMemberPageReqVO pageReqVO) {
        return memberMapper.selectPage(pageReqVO);
    }

    /**
     * 根据组织ID获取获得新联会员
     *
     * @param ids 分页查询
     * @param containSecondOrg 是否包含副组织
     * @return 新联会员分页
     */
    @Override
    public List<XinlianMemberDO> getMemberListByOrgIds(Set<Long> ids,boolean containSecondOrg) {
        return memberMapper.selectMembersByOrgIds(ids,true);
    }

    /**
     * 创建新联会员（小程序触发新建用户时）
     *
     * @param userId 系统用户ID
     * @return void
     */
    @Override
    public void createMemberByRegister(Long userId) {
        MemberUserRespDTO memberUserRespDTO = memberUserApi.getUser(userId);
        if (memberUserRespDTO != null){
            XinlianMemberDO xinlianMemberDO = memberMapper.selectByMemberUserId(userId);
            if (xinlianMemberDO == null){
                //如果为空则创建
                xinlianMemberDO = new XinlianMemberDO();
                xinlianMemberDO.setMemberUserId(userId);
                xinlianMemberDO.setAvatar(memberUserRespDTO.getAvatar());
                xinlianMemberDO.setNickname(memberUserRespDTO.getNickname());
                xinlianMemberDO.setName(memberUserRespDTO.getNickname());
                xinlianMemberDO.setTelephone(memberUserRespDTO.getMobile());
                memberMapper.insert(xinlianMemberDO);
            }
            else{
                if (StringUtils.isNotEmpty(xinlianMemberDO.getTelephone()) &&
                !xinlianMemberDO.getTelephone().equals(memberUserRespDTO.getMobile())){
                    //如果这个手机号码不存在存在,也插入
                    xinlianMemberDO = new XinlianMemberDO();
                    xinlianMemberDO.setMemberUserId(userId);
                    xinlianMemberDO.setAvatar(memberUserRespDTO.getAvatar());
                    xinlianMemberDO.setNickname(memberUserRespDTO.getNickname());
                    xinlianMemberDO.setName(memberUserRespDTO.getNickname());
                    xinlianMemberDO.setTelephone(memberUserRespDTO.getMobile());
                    memberMapper.insert(xinlianMemberDO);
                }
                else{
                    //如果不为空则更新
                    xinlianMemberDO.setMemberUserId(userId);
                    xinlianMemberDO.setAvatar(memberUserRespDTO.getAvatar());
                    xinlianMemberDO.setNickname(memberUserRespDTO.getNickname());
                    xinlianMemberDO.setName(memberUserRespDTO.getNickname());
                    xinlianMemberDO.setTelephone(memberUserRespDTO.getMobile());
                    memberMapper.updateById(xinlianMemberDO);
                }

            }
        }
    }

    /**
     * 查看联系方式
     *
     * @return 新联会员
     */
    @Override
    public List<XinlianMemberExtDO> getContact() {
        ConfigDO configDO = configService.getConfigByKey("xinlian_contact");
        if (configDO == null){
            throw exception(MEMBER_NOT_EXISTS_CONTACT);
        }
        Set<Long> idList = Arrays.stream(configDO.getValue().split(","))
                .map(Long::valueOf).collect(Collectors.toSet());

        return memberMapper.selectByMemberIdList(idList,false);
    }

    @Override
    public List<XinlianMemberDO> getMapMarker() {
        List<Short> list = Arrays.asList(Short.valueOf((short)2),Short.valueOf((short) 3),Short.valueOf((short)4));

        return memberMapper.selectMemberByMemberTypes(list);
    }

    private Long createSystemUser(XinlianMemberSaveReqVO xinlianMember){
        if (StringUtils.isEmpty(xinlianMember.getPassword())){
            throw exception(MEMBER_MUST_BE_PASSWORD);
        }
        ConfigDO configDO = configService.getConfigByKey("appoint_dept");
        Long deptId = Long.valueOf(configDO.getValue());
        UserSaveReqVO adminUser = new UserSaveReqVO();
        adminUser.setDeptId(deptId);
        Set<Long> postIds = new HashSet<>();
        configDO = configService.getConfigByKey("default_post");
        Long postId = Long.valueOf(configDO.getValue());
        postIds.add(postId);
        adminUser.setPostIds(postIds);
        adminUser.setUsername(xinlianMember.getTelephone());
        adminUser.setNickname(xinlianMember.getNickname());
        adminUser.setPassword(xinlianMember.getPassword());
        adminUser.setAvatar(xinlianMember.getAvatar());
        adminUser.setMobile(xinlianMember.getTelephone());
        adminUser.setSex(Integer.valueOf(xinlianMember.getSex()));
        adminUser.setEmail(xinlianMember.getEmail());
        adminUser.setPassword(xinlianMember.getPassword());
        Long id = adminUserService.createUser(adminUser);
        return id;
    }

    private boolean validateMobileUnique(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        XinlianMemberPageReqVO req = new XinlianMemberPageReqVO();
        req.setTelephone(mobile);
        PageResult<XinlianMemberDO> pageResult = memberMapper.selectPage(req);
        if (pageResult == null || pageResult.getTotal() == 0 || CollectionUtils.isEmpty(pageResult.getList())) {
            return false;
        }
        return true;
    }

}