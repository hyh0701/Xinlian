package cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动报名 DO
 *
 * @author 芋道源码
 */
@TableName("xinlian_activity_appoint")
@KeySequence("xinlian_activity_appoint_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAppointDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员名字
     */
    private String memberName;
    /**
     * 报名人
     */
    private String contact;
    /**
     * 报名预留电话
     */
    private String telephone;
    /**
     * 报名预留身份证号
     */
    private String idCard;
    /**
     * 报名人数
     */
    private Integer applyNum;
    /**
     * 报名状态
     *
     * 枚举 {@link TODO reserve_states 对应的类}
     */
    private Short state;
    /**
     * 支付状态
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean payState;

    /**
     * 备注信息
     */
    private String remark;
    /**
     * 职称
     */
    private String office;

}