package cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 活动记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class XinlianActivityPageReqVO extends PageParam {

    @Schema(description = "活动名字", example = "芋艿")
    private String activityName;

    @Schema(description = "活动发起人")
    private String sponsor;

    @Schema(description = "活动类型", example = "1")
    private Short activityType;

    @Schema(description = "活动开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "活动联系人")
    private String contact;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "非会员是否允许报名")
    private Boolean isLimit;
}