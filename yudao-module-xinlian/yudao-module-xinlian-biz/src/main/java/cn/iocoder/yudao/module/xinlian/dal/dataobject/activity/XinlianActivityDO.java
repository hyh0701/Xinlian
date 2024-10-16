package cn.iocoder.yudao.module.xinlian.dal.dataobject.activity;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 活动记录 DO
 *
 * @author 芋道源码
 */
@TableName("xinlian_activity")
@KeySequence("xinlian_activity_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XinlianActivityDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 活动名字
     */
    private String activityName;
    /**
     * 活动简介
     */
    private String activityDesc;
    /**
     * 报名费用
     */
    private BigDecimal amount;
    /**
     * 活动发起人
     */
    private String sponsor;
    /**
     * 活动限制人数
     */
    private Integer limitNum;
    /**
     * 活动类型
     *
     * 枚举 {@link TODO activity_type 对应的类}
     */
    private Short activityType;
    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;
    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;
    /**
     * 活动主题图片
     */
    private String banner;
    /**
     * 活动联系人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 活动详情
     */
    private String activityDetail;
    /**
     * 活动现场
     */
    private String activitySite;
    /**
     * 活动地点
     */
    private String activityLocation;
    /**
     * 备注信息
     */
    private String remark;

    /**
     * 非会员是否允许报名
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean isLimit;
}