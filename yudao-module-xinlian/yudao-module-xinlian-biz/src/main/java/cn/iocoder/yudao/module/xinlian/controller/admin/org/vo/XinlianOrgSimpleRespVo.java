package cn.iocoder.yudao.module.xinlian.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 获取组织id和name Response VO")
@Data
public class XinlianOrgSimpleRespVo {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4932")
    private Long id;

    @Schema(description = "组织名字", example = "李四")
    private String name;
}
