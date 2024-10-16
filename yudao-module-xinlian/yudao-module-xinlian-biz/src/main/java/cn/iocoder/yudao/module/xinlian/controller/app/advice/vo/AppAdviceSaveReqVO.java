package cn.iocoder.yudao.module.xinlian.controller.app.advice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "用户APP - 咨询记录新增/修改 Request VO")
@Data
public class AppAdviceSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5731")
    private Long id;

    @Schema(description = "问题简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "问题简介不能为空")
    private String adviceTitle;

    @Schema(description = "咨询类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "咨询类型不能为空")
    private String adviceType;

    @Schema(description = "咨询内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "咨询内容不能为空")
    private String adviceContent;

    @Schema(description = "咨询附件")
    private String adviceFiles;


    @Schema(description = "备注信息", example = "随便")
    private String remark;

}