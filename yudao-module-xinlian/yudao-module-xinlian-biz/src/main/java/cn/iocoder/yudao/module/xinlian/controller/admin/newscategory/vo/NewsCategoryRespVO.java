package cn.iocoder.yudao.module.xinlian.controller.admin.newscategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 新闻目录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class NewsCategoryRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4640")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "别名", example = "李四")
    @ExcelProperty("别名")
    private String aliasName;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "备注信息", example = "随便")
    @ExcelProperty("备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否启用", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isEnable;

    @Schema(description = "父ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27983")
    @ExcelProperty("父ID")
    private Long parentId;

    @Schema(description = "分类名字", example = "王五")
    @ExcelProperty("分类名字")
    private String name;

}