package cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 新联会员新增/修改 Request VO")
@Data
public class AppXinlianMemberSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16896")
    private Long id;

    @Schema(description = "会员名字", example = "芋艿")
    private String name;

    @Schema(description = "会员昵称", example = "芋艿")
    private String nickname;

    @Schema(description = "性别")
    private Short sex;


    @Schema(description = "自定义头像")
    private String customAvatar;


    @Schema(description = "联系电话")
    private String telephone;

    @Schema(description = "工作电话")
    private String workPhone;

    @Schema(description = "工作地点")
    private String workLocation;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "公司名字")
    private String company;


    @Schema(description = "会员简介")
    private String introduction;

    @Schema(description = "会员地址")
    private String memberLocation;



    @Schema(description = "密码", example = "abs")
    private String password;
}