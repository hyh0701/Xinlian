package cn.iocoder.yudao.module.xinlian.controller.admin.appoint.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 绩效计分 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppointRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22009")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "会员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28845")
    @ExcelProperty("会员ID")
    private Long memberId;
    /**
     * 系统用户
     */
    private Long userId;
    @Schema(description = "考核自描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("考核自描述")
    private String appointIntro;

    @Schema(description = "考核时间")
    @ExcelProperty("考核时间")
    private LocalDateTime appointDate;

    @Schema(description = "考核分数")
    @ExcelProperty("考核分数")
    private BigDecimal appointScore;

    @Schema(description = "考核附件")
    @ExcelProperty("考核附件")
    private String appointArticle;

    @Schema(description = "考核季度", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "考核季度", converter = DictConvert.class)
    @DictFormat("appoint_plan") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String appointPlan;

    @Schema(description = "审核状态")
    @ExcelProperty(value = "审核状态", converter = DictConvert.class)
    @DictFormat("appoint_state") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Short state;

    @Schema(description = "备注信息", example = "你说的对")
    @ExcelProperty("备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}