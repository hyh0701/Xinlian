package cn.iocoder.yudao.module.xinlian.dal.dataobject.news;

import cn.iocoder.yudao.framework.mybatis.core.type.JsonLongSetTypeHandler;
import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 新闻 DO
 *
 * @author 芋道源码
 */
@TableName(value = "xinlian_news", autoResultMap = true)
@KeySequence("xinlian_news_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class NewsDO extends TenantBaseDO {

    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 新闻类别数组
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> cateIds;

    /**
     * 新闻标签
     *
     * 枚举 {@link TODO news_tag 对应的类}
     */
    private String tags;
    /**
     * 新闻标题
     */
    private String title;
    /**
     * 新闻概述
     */
    private String introduction;
    /**
     * 新闻主图
     */
    private String slide;
    /**
     * 新闻内容
     */
    private String content;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否可用
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean isEnable;
    /**
     * 允许评论
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean isComment;
    /**
     * 是否置顶
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean isTop;
    /**
     * 新闻来源
     */
    private String source;
    /**
     * 备注信息
     */
    private String remark;

}