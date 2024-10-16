package cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author Administrator
 */
@Schema(description = "管理后台 - 活动报名分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ActivityAppointPageReqVO extends PageParam {

    @Schema(description = "活动ID", example = "15716")
    private Long activityId;

    @Schema(description = "会员名字", example = "张三")
    private String memberName;
    @Schema(description = "会员ID", example = "23")
    private Long memberId;

    @Schema(description = "报名人")
    private String contact;

    @Schema(description = "报名预留电话")
    private String telephone;

    @Schema(description = "报名状态")
    private Short state;

    @Schema(description = "支付状态")
    private Boolean payState;

    @Schema(description = "职称")
    private String office;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}