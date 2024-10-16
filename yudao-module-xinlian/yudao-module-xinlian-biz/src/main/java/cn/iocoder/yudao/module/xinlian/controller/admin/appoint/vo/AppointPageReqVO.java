package cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 绩效计分分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppointPageReqVO extends PageParam {

    @Schema(description = "会员ID", example = "28845")
    private Long memberId;

    @Schema(description = "考核自描述")
    private String appointIntro;

    @Schema(description = "考核时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] appointDate;

    @Schema(description = "考核分数")
    private BigDecimal appointScore;
    /**
     * 系统用户
     */
    private Long userId;

    @Schema(description = "考核附件")
    private String appointArticle;

    @Schema(description = "考核季度")
    private String appointPlan;

    @Schema(description = "审核状态")
    private Short state;

    @Schema(description = "备注信息", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}