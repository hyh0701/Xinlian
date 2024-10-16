package cn.iocoder.yudao.module.xinlian.dal.dataobject.job;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 职务管理 DO
 *
 * @author 芋道源码
 */
@TableName("xinlian_job")
@KeySequence("xinlian_job_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XinlianJobDO extends BaseDO {

    /**
     * 别名
     */
    private String aliasName;
    /**
     * 是否可用
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean isEnable;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名字
     */
    private String name;

}