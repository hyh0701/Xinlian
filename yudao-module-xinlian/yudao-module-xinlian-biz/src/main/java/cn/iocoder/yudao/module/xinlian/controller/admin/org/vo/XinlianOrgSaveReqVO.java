package cn.iocoder.yudao.module.xinlian.controller.admin.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 组织架构管理新增/修改 Request VO")
@Data
public class XinlianOrgSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4932")
    private Long id;

    @Schema(description = "组织名字", example = "李四")
    private String name;

    @Schema(description = "是否叶子节点")
    private Boolean isLeaf;

    @Schema(description = "上级节点", example = "11331")
    private Long parentId;

    @Schema(description = "logo图片地址")
    private String logo;

    @Schema(description = "职位列表,逗号分割")
    private String jobs;

    @Schema(description = "是否可用")
    private Boolean isEnable;

    @Schema(description = "备注信息", example = "你猜")
    private String remark;

}