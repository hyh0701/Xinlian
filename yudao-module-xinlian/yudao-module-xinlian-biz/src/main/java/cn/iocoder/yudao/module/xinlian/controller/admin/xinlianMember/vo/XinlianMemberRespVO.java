package cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 新联会员 Response VO")
@Data
@ExcelIgnoreUnannotated
public class XinlianMemberRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16896")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "会员名字", example = "芋艿")
    @ExcelProperty("会员名字")
    private String name;

    @Schema(description = "会员N年龄", example = "芋艿")
    @ExcelProperty("会员年龄")
    private Integer age;

    @Schema(description = "会员昵称", example = "芋艿")
    @ExcelProperty("会员昵称")
    private String nickname;

    @Schema(description = "性别")
    @ExcelProperty(value = "性别", converter = DictConvert.class)
    @DictFormat("system_user_sex") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Short sex;

    @Schema(description = "头像")
    @ExcelProperty("头像")
    private String avatar;

    @Schema(description = "允许登录")
    @ExcelProperty(value = "允许登录", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean permitLogin;

    @Schema(description = "自定义头像")
    @ExcelProperty("自定义头像")
    private String customAvatar;

    @Schema(description = "管理用户ID")
    private Long userId;
    @Schema(description = "会员用户ID")
    /**
     * sss
     */
    private Long memberUserId;

    @Schema(description = "小程序ID", example = "9266")
    @ExcelProperty("小程序ID")
    private String openid;

    @Schema(description = "公众号openID", example = "30610")
    @ExcelProperty("公众号openID")
    private String wxopenid;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String telephone;

    @Schema(description = "工作电话")
    @ExcelProperty("工作电话")
    private String workPhone;

    @Schema(description = "工作地点")
    @ExcelProperty("工作地点")
    private String workLocation;

    @Schema(description = "主要头衔", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("主要头衔")
    private String mainTitle;

    @Schema(description = "次要头衔")
    @ExcelProperty("次要头衔")
    private String secondTitle;

    @Schema(description = "公司名字")
    @ExcelProperty("公司名字")
    private String company;

    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;


    @Schema(description = "经度")
    @ExcelProperty("经度")
    private String longitude;

    @Schema(description = "纬度")
    @ExcelProperty("纬度")
    private String latitude;

    @Schema(description = "主组织ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25118")
    @ExcelProperty("主组织ID")
    private Long mainOrgId;

    @Schema(description = "次组织ID")
    @ExcelProperty("次组织ID")
    private Long secondOrgId;

    @Schema(description = "职位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26787")
    @ExcelProperty("职位ID")
    private Long mainJobId;

    @Schema(description = "次职位ID")
    @ExcelProperty("次职位ID")
    private Long secondJobId;

    @Schema(description = "会员简介")
    @ExcelProperty("会员简介")
    private String introduction;

    @Schema(description = "会员类型")
    @ExcelProperty(value = "会员类型", converter = DictConvert.class)
    @DictFormat("member_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Short memberType;

    @Schema(description = "会员地址")
    @ExcelProperty("会员地址")
    private String memberLocation;

    @Schema(description = "会员介绍图片")
    @ExcelProperty("会员介绍图片")
    private String memberBanner;

    @Schema(description = "会员详情文章")
    @ExcelProperty("会员详情文章")
    private String memberDetail;

    @Schema(description = "审核状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "审核状态", converter = DictConvert.class)
    @DictFormat("member_states") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer state;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否启用", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isEnable;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "备注信息", example = "随便")
    @ExcelProperty("备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;



}