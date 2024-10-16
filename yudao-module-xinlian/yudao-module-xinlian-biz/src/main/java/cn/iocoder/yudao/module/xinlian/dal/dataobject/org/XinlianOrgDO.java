package cn.iocoder.yudao.module.xinlian.dal.dataobject.org;

import cn.iocoder.yudao.framework.mybatis.core.type.JsonLongSetTypeHandler;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 组织架构管理 DO
 *
 * @author 芋道源码
 */
@TableName(value = "xinlian_org", autoResultMap = true)
@KeySequence("xinlian_org_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XinlianOrgDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 组织名字
     */
    private String name;
    /**
     * 是否叶子节点
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean isLeaf;
    /**
     * 上级节点
     */
    private Long parentId;
    /**
     * logo图片地址
     */
    private String logo;
    /**
     * 职位列表,逗号分割
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> jobs;
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

}