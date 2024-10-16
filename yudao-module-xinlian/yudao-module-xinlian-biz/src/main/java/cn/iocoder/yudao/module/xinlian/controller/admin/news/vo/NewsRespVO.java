package cn.iocoder.yudao.module.xinlian.controller.admin.news.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 新闻 Response VO")
@Data
@ExcelIgnoreUnannotated
public class NewsRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6450")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "新闻类别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1096")
    @ExcelProperty("新闻类别")
    private Set<Long> cateIds;

    @Schema(description = "新闻标签")
    @ExcelProperty(value = "新闻标签", converter = DictConvert.class)
    @DictFormat("news_tag") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Set<String> tags;

    @Schema(description = "新闻标题")
    @ExcelProperty("新闻标题")
    private String title;

    @Schema(description = "新闻概述")
    @ExcelProperty("新闻概述")
    private String introduction;

    @Schema(description = "新闻主图")
    @ExcelProperty("新闻主图")
    private String slide;

    @Schema(description = "新闻内容")
    @ExcelProperty("新闻内容")
    private String content;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "是否可用")
    @ExcelProperty(value = "是否可用", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isEnable;

    @Schema(description = "允许评论")
    @ExcelProperty(value = "允许评论", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isComment;

    @Schema(description = "是否置顶")
    @ExcelProperty(value = "是否置顶", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isTop;

    @Schema(description = "新闻来源")
    @ExcelProperty("新闻来源")
    private String source;

    @Schema(description = "备注信息", example = "你说的对")
    @ExcelProperty("备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
    @Schema(description = "目录名称", example = "你说的对")
    @ExcelProperty("目录名称")
    private String cateName;

}