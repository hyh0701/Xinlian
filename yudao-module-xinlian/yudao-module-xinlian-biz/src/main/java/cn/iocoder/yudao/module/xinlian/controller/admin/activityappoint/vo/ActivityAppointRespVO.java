package cn.iocoder.yudao.module.xinlian.controller.admin.activityappoint.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 活动报名 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ActivityAppointRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7513")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "活动ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15716")
    @ExcelProperty("活动ID")
    private Long activityId;

    @Schema(description = "会员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25301")
    @ExcelProperty("会员ID")
    private Long memberId;

    @Schema(description = "会员名字", example = "张三")
    @ExcelProperty("会员名字")
    private String memberName;

    @Schema(description = "报名人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("报名人")
    private String contact;

    @Schema(description = "报名预留电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("报名预留电话")
    private String telephone;

    @Schema(description = "报名预留身份证号")
    @ExcelProperty("报名预留身份证号")
    private String idCard;

    @Schema(description = "报名人数")
    @ExcelProperty("报名人数")
    private Integer applyNum;

    @Schema(description = "报名状态")
    @ExcelProperty(value = "报名状态", converter = DictConvert.class)
    @DictFormat("reserve_states") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Short state;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "职称")
    @ExcelProperty("职称")
    private String office;
}