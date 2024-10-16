package cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 绩效计分新增/修改 Request VO")
@Data
public class AppointSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22009")
    private Long id;

    @Schema(description = "会员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28845")
    @NotNull(message = "会员ID不能为空")
    private Long memberId;
    /**
     * 系统用户
     */
    private Long userId;

    @Schema(description = "考核自描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "考核自描述不能为空")
    private String appointIntro;

    @Schema(description = "考核时间")
    private LocalDateTime appointDate;

    @Schema(description = "考核分数")
    private BigDecimal appointScore;

    @Schema(description = "考核附件")
    private String appointArticle;

    @Schema(description = "考核季度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "考核季度不能为空")
    private String appointPlan;

    @Schema(description = "审核状态")
    private Short state;

    @Schema(description = "备注信息", example = "你说的对")
    private String remark;

}