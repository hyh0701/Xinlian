package cn.iocoder.yudao.module.xinlian.dal.dataobject.xinlianMember;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 新联会员 DO
 *
 * @author 芋道源码
 */
@TableName("xinlian_member")
@KeySequence("xinlian_member_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XinlianMemberDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 会员名字
     */
    private String name;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 性别
     *
     * 枚举 {@link TODO system_user_sex 对应的类}
     */
    private Short sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 系统用户ID
     */
    private Long userId;
    /**
     * 会员用户ID
     */
    private Long memberUserId;

    /**
     * 允许登录
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean permitLogin;
    /**
     * 自定义头像
     */
    private String customAvatar;
    /**
     * 小程序ID
     */
    private String openid;
    /**
     * 公众号openID
     */
    private String wxopenid;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 工作电话
     */
    private String workPhone;
    /**
     * 工作地点
     */
    private String workLocation;
    /**
     * 会员类型
     *
     * 枚举 {@link TODO member_type 对应的类}
     */
    private Short memberType;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 公司名字
     */
    private String company;
    /**
     * 主要头衔
     */
    private String mainTitle;
    /**
     * 次要头衔
     */
    private String secondTitle;
    /**
     * 会员简介
     */
    private String introduction;
    /**
     * 会员地址
     */
    private String memberLocation;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 会员介绍图片
     */
    private String memberBanner;
    /**
     * 会员详情文章
     */
    private String memberDetail;
    /**
     * 主组织ID
     */
    private Long mainOrgId;
    /**
     * 副组织ID
     */
    private Long secondOrgId;
    /**
     * 职位ID
     */
    private Long mainJobId;
    /**
     * 副职位ID
     */
    private Long secondJobId;
    /**
     * 审核状态
     *
     * 枚举 {@link TODO member_states 对应的类}
     */
    private Integer state;
    /**
     * 是否启用
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean isEnable;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注信息
     */
    private String remark;

}