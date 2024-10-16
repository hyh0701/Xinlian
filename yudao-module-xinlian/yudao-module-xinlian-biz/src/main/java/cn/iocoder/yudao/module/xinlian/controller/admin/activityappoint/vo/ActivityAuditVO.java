package cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "管理后台 - 审批操作")
@Data
public class ActivityAuditVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7513")
    private Long id;

    @Schema(description = "报名人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "报名人不能为空")
    private String contact;

    @Schema(description = "报名预留电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "报名预留电话不能为空")
    private String telephone;

    @Schema(description = "报名预留身份证号")
    private String idCard;
    @Schema(description = "报名状态")
    private Short state;
}
