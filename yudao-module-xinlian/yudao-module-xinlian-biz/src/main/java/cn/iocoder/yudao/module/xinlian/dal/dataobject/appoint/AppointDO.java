package cn.iocoder.yudao.module.xinlian.dal.dataobject.appoint;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 绩效计分 DO
 *
 * @author 芋道源码
 */
@TableName("xinlian_appoint")
@KeySequence("xinlian_appoint_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 系统用户
     */
    private Long userId;
    /**
     * 考核自描述
     */
    private String appointIntro;
    /**
     * 考核时间
     */
    private LocalDateTime appointDate;
    /**
     * 考核分数
     */
    private BigDecimal appointScore;
    /**
     * 考核附件
     */
    private String appointArticle;
    /**
     * 考核季度
     *
     * 枚举 {@link TODO appoint_plan 对应的类}
     */
    private String appointPlan;
    /**
     * 审核状态
     *
     * 枚举 {@link TODO appoint_state 对应的类}
     */
    private Short state;
    /**
     * 备注信息
     */
    private String remark;

}