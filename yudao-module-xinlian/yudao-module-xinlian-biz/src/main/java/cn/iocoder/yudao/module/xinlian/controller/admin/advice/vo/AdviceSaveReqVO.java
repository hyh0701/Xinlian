package cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 咨询记录新增/修改 Request VO")
@Data
public class AdviceSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5731")
    private Long id;

    @Schema(description = "问题简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "问题简介不能为空")
    private String adviceTitle;

    @Schema(description = "咨询类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "咨询类型不能为空")
    private String adviceType;

    @Schema(description = "咨询人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16105")
    @NotNull(message = "咨询人ID不能为空")
    private Long memberId;

    @Schema(description = "咨询人名字", example = "王五")
    private String memberName;

    @Schema(description = "咨询内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "咨询内容不能为空")
    private String adviceContent;

    @Schema(description = "咨询附件")
    private String adviceFiles;
    @Schema(description = "回复人ID")
    private Long replyUserId;
    @Schema(description = "回复人姓名")
    private String replyUserName;

    @Schema(description = "回复时间")
    private LocalDateTime replyTime;

    @Schema(description = "咨询状态0未处理1已处理3关闭", example = "2")
    private Short status;

    @Schema(description = "回复内容", example = "随便")
    private String remark;

}