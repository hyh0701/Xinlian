package cn.iocoder.yudao.module.xinlian.controller.admin.job.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 职务管理新增/修改 Request VO")
@Data
public class JobSaveReqVO {

    @Schema(description = "别名", example = "李四")
    private String aliasName;

    @Schema(description = "是否可用")
    private Boolean isEnable;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24884")
    private Long id;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "名字不能为空")
    private String name;

}