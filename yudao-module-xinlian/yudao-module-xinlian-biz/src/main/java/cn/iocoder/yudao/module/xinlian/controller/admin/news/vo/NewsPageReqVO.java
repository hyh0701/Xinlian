package cn.iocoder.yudao.module.xinlian.controller.admin.news.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 新闻分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewsPageReqVO extends PageParam {

    @Schema(description = "新闻类别", example = "1096")
    private Long[] cateIds;

    @Schema(description = "新闻标题")
    private String title;

    @Schema(description = "新闻概述")
    private String introduction;

    @Schema(description = "是否可用")
    private Boolean isEnable;

    @Schema(description = "新闻来源")
    private String source;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}