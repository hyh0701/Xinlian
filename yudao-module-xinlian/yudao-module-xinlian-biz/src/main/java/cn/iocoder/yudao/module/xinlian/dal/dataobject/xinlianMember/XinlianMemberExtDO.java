package cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 新联会员 DO
 *
 * @author 芋道源码
 */

@Data
public class XinlianMemberExtDO extends XinlianMemberDO {

    private String mainOrgName;

}