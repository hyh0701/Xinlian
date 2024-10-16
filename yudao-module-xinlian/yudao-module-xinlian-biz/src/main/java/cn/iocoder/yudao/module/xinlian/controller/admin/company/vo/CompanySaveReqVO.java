package cn.iocoder.yudao.module.xinlian.controller.admin.company.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 企业会员新增/修改 Request VO")
@Data
public class CompanySaveReqVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18932")
    private Long id;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "名字不能为空")
    private String name;

    @Schema(description = "别名", example = "张三")
    private String aliasName;

    @Schema(description = "纬度")
    private String latitude;

    @Schema(description = "经度")
    private String longitude;

    @Schema(description = "会员简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "会员简介不能为空")
    private String introduction;

    @Schema(description = "会员介绍图片")
    private String banners;

    @Schema(description = "主组织ID", example = "23226")
    private Long orgId;

    @Schema(description = "工作地点")
    private String workLocation;

    @Schema(description = "联系人")
    private String contacter;

    @Schema(description = "联系电话")
    private String telephone;

    @Schema(description = "备注信息", example = "你说的对")
    private String remark;

}