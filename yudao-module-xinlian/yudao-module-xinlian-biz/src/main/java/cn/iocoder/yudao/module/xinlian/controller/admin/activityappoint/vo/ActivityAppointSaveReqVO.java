package cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 活动报名新增/修改 Request VO")
@Data
public class ActivityAppointSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7513")
    private Long id;

    @Schema(description = "活动ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15716")
    @NotNull(message = "活动ID不能为空")
    private Long activityId;

    @Schema(description = "会员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25301")
    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    @Schema(description = "会员名字", example = "张三")
    private String memberName;

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

    @Schema(description = "报名状态")
    private Short state;

    @Schema(description = "支付状态")
    private Boolean payState;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

    @Schema(description = "职称")
    private String office;

}