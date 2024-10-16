package cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 新联会员新增/修改 Request VO")
@Data
public class XinlianMemberSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16896")
    private Long id;

    @Schema(description = "会员名字", example = "芋艿")
    private String name;

    @Schema(description = "会员昵称", example = "芋艿")
    private String nickname;

    @Schema(description = "性别")
    private Short sex;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "允许登录")
    private Boolean permitLogin;

    @Schema(description = "自定义头像")
    private String customAvatar;

    @Schema(description = "小程序ID", example = "9266")
    private String openid;

    @Schema(description = "公众号openID", example = "30610")
    private String wxopenid;

    @Schema(description = "联系电话")
    private String telephone;

    @Schema(description = "工作电话")
    private String workPhone;

    @Schema(description = "管理用户ID")
    private Long userId;
    @Schema(description = "会员用户ID")
    private Long memberUserId;
    @Schema(description = "工作地点")
    private String workLocation;

    @Schema(description = "会员类型", example = "1")
    private Short memberType;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "公司名字")
    private String company;

    @Schema(description = "主要头衔", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "主要头衔不能为空")
    private String mainTitle;

    @Schema(description = "次要头衔")
    private String secondTitle;

    @Schema(description = "会员简介")
    private String introduction;

    @Schema(description = "会员地址")
    private String memberLocation;

    @Schema(description = "纬度")
    private String latitude;

    @Schema(description = "经度")
    private String longitude;

    @Schema(description = "会员介绍图片")
    private String memberBanner;

    @Schema(description = "会员详情文章")
    private String memberDetail;

    @Schema(description = "主组织ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25118")
    @NotNull(message = "主组织ID不能为空")
    private Long mainOrgId;

    @Schema(description = "副组织ID", example = "12524")
    private Long secondOrgId;

    @Schema(description = "职位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26787")
    @NotNull(message = "职位ID不能为空")
    private Long mainJobId;

    @Schema(description = "副职位ID", example = "15364")
    private Long secondJobId;

    @Schema(description = "审核状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审核状态不能为空")
    private Integer state;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否启用不能为空")
    private Boolean isEnable;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

    @Schema(description = "密码", example = "abs")
    private String password;
}