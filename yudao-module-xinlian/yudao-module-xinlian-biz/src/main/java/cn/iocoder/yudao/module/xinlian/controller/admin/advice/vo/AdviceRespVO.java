package cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 咨询记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AdviceRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5731")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "问题简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("问题简介")
    private String adviceTitle;

    @Schema(description = "咨询类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "咨询类型", converter = DictConvert.class)
    @DictFormat("advice_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String adviceType;

    @Schema(description = "咨询人名字", example = "王五")
    @ExcelProperty("咨询人名字")
    private String memberName;

    @Schema(description = "咨询人ID", example = "王五")
    @ExcelProperty("咨询人ID")
    private String memberId;

    @Schema(description = "咨询内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("咨询内容")
    private String adviceContent;

    @Schema(description = "回复人名字", example = "王五")
    @ExcelProperty("回复人名字")
    private String replyUserName;

    @Schema(description = "回复时间")
    @ExcelProperty("回复时间")
    private LocalDateTime replyTime;

    @Schema(description = "咨询状态0未处理1已处理3关闭", example = "2")
    @ExcelProperty(value = "咨询状态0未处理1已处理3关闭", converter = DictConvert.class)
    @DictFormat("advice_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Short status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    @Schema(description = "回复内容")
    @ExcelProperty("回复内容")
    private String remark;

    @Schema(description = "附件")
    @ExcelProperty("附件")
    private String adviceFiles;

}