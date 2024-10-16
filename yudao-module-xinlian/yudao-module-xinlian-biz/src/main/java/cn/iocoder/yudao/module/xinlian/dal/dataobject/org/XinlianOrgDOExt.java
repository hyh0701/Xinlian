package cn.iocoder.yudao.module.xinlian.dal.dataobject.org;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo.AppXinlianMemberRespVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberExtDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * 组织架构管理 DO
 *
 * @author 芋道源码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XinlianOrgDOExt extends XinlianOrgDO {
    private List<XinlianMemberDO> members;
    // Constructor
    public XinlianOrgDOExt(XinlianOrgDO org,List<XinlianMemberDO> memberList) {
        super();
        this.setId(org.getId());
        this.setName(org.getName());
        this.setIsLeaf(org.getIsLeaf());
        this.setIsEnable(org.getIsEnable());
        this.setJobs(org.getJobs());
        this.setLogo(org.getLogo());
        this.setParentId(org.getParentId());
        this.setRemark(org.getRemark());
        this.setCreateTime(org.getCreateTime());
        this.members = memberList;
    }
}