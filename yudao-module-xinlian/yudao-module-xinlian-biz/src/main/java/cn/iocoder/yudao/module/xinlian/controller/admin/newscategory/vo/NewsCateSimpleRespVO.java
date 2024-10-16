package cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 目录信息的精简 Response VO")
@Data
public class NewsCateSimpleRespVO {

    @Schema(description = "目录序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("目录序号")
    private Long id;

    @Schema(description = "目录名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "小土豆")
    @ExcelProperty("目录名称")
    private String name;

}
