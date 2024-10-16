package cn.iocoder.yudao.module.xinlian.controller.admin.news.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 新闻新增/修改 Request VO")
@Data
public class NewsSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6450")
    private Long id;

    @Schema(description = "新闻类别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "新闻类别不能为空")
    private Set<Long> cateIds;

    @Schema(description = "新闻标签")
    private Set<String> tags;

    @Schema(description = "新闻标题")
    private String title;

    @Schema(description = "新闻概述")
    private String introduction;

    @Schema(description = "新闻主图")
    private String slide;

    @Schema(description = "新闻内容")
    private String content;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否可用")
    private Boolean isEnable;

    @Schema(description = "允许评论")
    private Boolean isComment;

    @Schema(description = "是否置顶")
    private Boolean isTop;

    @Schema(description = "新闻来源")
    private String source;

    @Schema(description = "备注信息", example = "你说的对")
    private String remark;

}