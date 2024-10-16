package cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 活动记录新增/修改 Request VO")
@Data
public class XinlianActivitySaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19762")
    private Long id;

    @Schema(description = "活动名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "活动名字不能为空")
    private String activityName;

    @Schema(description = "活动简介")
    private String activityDesc;

    @Schema(description = "报名费用")
    private BigDecimal amount;

    @Schema(description = "活动发起人")
    private String sponsor;

    @Schema(description = "活动限制人数")
    private Integer limitNum;

    @Schema(description = "活动类型", example = "1")
    private Short activityType;

    @Schema(description = "活动开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "活动开始时间不能为空")
    private LocalDateTime startTime;

    @Schema(description = "活动结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "活动结束时间不能为空")
    private LocalDateTime endTime;

    @Schema(description = "活动主题图片")
    private String banner;

    @Schema(description = "活动联系人")
    private String contact;

    @Schema(description = "联系电话")
    private String telephone;

    @Schema(description = "活动详情")
    private String activityDetail;

    @Schema(description = "活动现场")
    private String activitySite;

    @Schema(description = "活动地点")
    private String activityLocation;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

    @Schema(description = "非会员是否允许报名")
    private Boolean isLimit;
}