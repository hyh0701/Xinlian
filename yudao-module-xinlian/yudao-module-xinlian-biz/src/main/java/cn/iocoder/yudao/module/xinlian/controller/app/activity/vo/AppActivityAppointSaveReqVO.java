package cn.iocoder.yudao.module.xinlian.controller.app.activity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "APP - 活动报名新增/修改 Request VO")
@Data
public class AppActivityAppointSaveReqVO {

    @Schema(description = "活动ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15716")
    @NotNull(message = "活动ID不能为空")
    private Long activityId;

    @Schema(description = "报名人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "报名人不能为空")
    private String contact;

    @Schema(description = "报名预留电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "报名预留电话不能为空")
    private String telephone;

    @Schema(description = "报名预留身份证号")
    private String idCard;

    @Schema(description = "报名人数")
    private Integer applyNum;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

}