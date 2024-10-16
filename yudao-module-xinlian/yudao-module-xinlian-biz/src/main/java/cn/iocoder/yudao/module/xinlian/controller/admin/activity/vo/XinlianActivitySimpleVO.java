package cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class XinlianActivitySimpleVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19762")
    private Long id;

    @Schema(description = "活动名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "活动名字不能为空")
    private String activityName;
}
