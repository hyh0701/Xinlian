package cn.iocoder.yudao.module.xinlian.dal.dataobject.newscategory;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 新闻目录 DO
 *
 * @author 芋道源码
 */
@TableName("xinlian_news_category")
@KeySequence("xinlian_news_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsCategoryDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 别名
     */
    private String aliasName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 是否启用
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean isEnable;
    /**
     * 父ID
     */
    private Long parentId;
    /**
     * 分类名字
     */
    private String name;

}