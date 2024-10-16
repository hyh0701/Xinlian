package cn.iocoder.yudao.module.xinlian.dal.dataobject.advice;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 咨询记录 DO
 *
 * @author 平台用户
 */
@TableName("xinlian_advice")
@KeySequence("xinlian_advice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdviceDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 问题简介
     */
    private String adviceTitle;
    /**
     * 咨询类型
     *
     * 枚举 {@link TODO advice_type 对应的类}
     */
    private String adviceType;
    /**
     * 咨询人ID
     */
    private Long memberId;
    /**
     * 咨询人名字
     */
    private String memberName;
    /**
     * 咨询内容
     */
    private String adviceContent;
    /**
     * 咨询附件
     */
    private String adviceFiles;
    /**
     * 回复人ID
     */
    private Long replyUserId;
    /**
     * 回复人名字
     */
    private String replyUserName;
    /**
     * 回复时间
     */
    private LocalDateTime replyTime;
    /**
     * 咨询状态0未处理1已处理3关闭
     *
     * 枚举 {@link TODO advice_status 对应的类}
     */
    private Short status;
    /**
     * 备注信息
     */
    private String remark;

}