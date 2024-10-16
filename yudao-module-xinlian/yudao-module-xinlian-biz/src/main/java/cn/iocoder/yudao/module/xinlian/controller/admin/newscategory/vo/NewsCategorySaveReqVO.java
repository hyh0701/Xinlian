package cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 新闻目录新增/修改 Request VO")
@Data
public class NewsCategorySaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4640")
    private Long id;

    @Schema(description = "别名", example = "李四")
    private String aliasName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否启用不能为空")
    private Boolean isEnable;

    @Schema(description = "父ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27983")
    @NotNull(message = "父ID不能为空")
    private Long parentId;

    @Schema(description = "分类名字", example = "王五")
    private String name;

}