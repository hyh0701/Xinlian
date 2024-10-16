package cn.iocoder.yudao.module.xinlian.dal.dataobject.company;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 企业会员 DO
 *
 * @author 平台用户
 */
@TableName("xinlian_company")
@KeySequence("xinlian_company_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDO extends BaseDO {

    /**
     * 序号
     */
    @TableId
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 别名
     */
    private String aliasName;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 会员简介
     */
    private String introduction;
    /**
     * 会员介绍图片
     */
    private String banners;
    /**
     * 主组织ID
     */
    private Long orgId;
    /**
     * 工作地点
     */
    private String workLocation;
    /**
     * 联系人
     */
    private String contacter;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 备注信息
     */
    private String remark;

}