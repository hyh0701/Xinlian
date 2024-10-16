package cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 咨询记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdvicePageReqVO extends PageParam {

    @Schema(description = "问题简介")
    private String adviceTitle;

    @Schema(description = "咨询类型", example = "2")
    private String adviceType;


    @Schema(description = "咨询人ID", example = "22")
    private String memberId;

    @Schema(description = "咨询人名字", example = "王五")
    private String memberName;

    @Schema(description = "回复人名字", example = "王五")
    private String replyUserName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}