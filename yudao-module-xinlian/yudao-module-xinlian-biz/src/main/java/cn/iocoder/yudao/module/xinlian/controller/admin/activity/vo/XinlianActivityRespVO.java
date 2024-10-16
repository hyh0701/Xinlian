package cn.iocoder.yudao.module.xinlian.controller.admin.activity.vo;

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

@Schema(description = "管理后台 - 活动记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class XinlianActivityRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19762")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "活动名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("活动名字")
    private String activityName;

    @Schema(description = "活动简介")
    @ExcelProperty("活动简介")
    private String activityDesc;

    @Schema(description = "报名费用")
    @ExcelProperty("报名费用")
    private BigDecimal amount;

    @Schema(description = "活动发起人")
    @ExcelProperty("活动发起人")
    private String sponsor;

    @Schema(description = "活动限制人数")
    @ExcelProperty("活动限制人数")
    private Integer limitNum;

    @Schema(description = "活动类型", example = "1")
    @ExcelProperty(value = "活动类型", converter = DictConvert.class)
    @DictFormat("activity_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Short activityType;

    @Schema(description = "活动开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("活动开始时间")
    private LocalDateTime startTime;

    @Schema(description = "活动结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("活动结束时间")
    private LocalDateTime endTime;

    @Schema(description = "活动主题图片")
    @ExcelProperty("活动主题图片")
    private String banner;

    @Schema(description = "活动联系人")
    @ExcelProperty("活动联系人")
    private String contact;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String telephone;

    @Schema(description = "活动详情")
    @ExcelProperty("活动详情")
    private String activityDetail;

    @Schema(description = "活动现场")
    @ExcelProperty("活动现场")
    private String activitySite;

    @Schema(description = "活动地点")
    @ExcelProperty("活动地点")
    private String activityLocation;

    @Schema(description = "备注信息", example = "随便")
    @ExcelProperty("备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    @Schema(description = "非会员是否允许报名")
    private Boolean isLimit;
}