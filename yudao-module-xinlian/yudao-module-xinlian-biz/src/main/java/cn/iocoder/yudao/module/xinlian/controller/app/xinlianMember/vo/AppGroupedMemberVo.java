package cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo;

import cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember.XinlianMemberDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 */
@Schema(description = "APP - 新联会员分组 AppGroupedMemberVo")
@Data
public class AppGroupedMemberVo {
    private Short memberType;
    private String memberTypeName;
    private List<XinlianMemberDO> members;

    public AppGroupedMemberVo(Short memberType, String memberTypeName, List<XinlianMemberDO> members) {
        this.memberType = memberType;
        this.memberTypeName = memberTypeName;
        this.members = members;
    }
}
