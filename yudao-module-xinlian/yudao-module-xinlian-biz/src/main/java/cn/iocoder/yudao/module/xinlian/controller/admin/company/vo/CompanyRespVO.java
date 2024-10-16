package cn.iocoder.yudao.module.xinlian.controller.admin.company.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 企业会员 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CompanyRespVO {

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18932")
    @ExcelProperty("序号")
    private Long id;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("名字")
    private String name;

    @Schema(description = "别名", example = "张三")
    @ExcelProperty("别名")
    private String aliasName;

    @Schema(description = "纬度")
    @ExcelProperty("纬度")
    private String latitude;

    @Schema(description = "经度")
    @ExcelProperty("经度")
    private String longitude;

    @Schema(description = "会员简介", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("会员简介")
    private String introduction;

    @Schema(description = "会员介绍图片")
    @ExcelProperty("会员介绍图片")
    private String banners;

    @Schema(description = "主组织ID", example = "23226")
    @ExcelProperty("主组织ID")
    private Long orgId;

    @Schema(description = "工作地点")
    @ExcelProperty("工作地点")
    private String workLocation;

    @Schema(description = "联系人")
    @ExcelProperty("联系人")
    private String contacter;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String telephone;

    @Schema(description = "备注信息", example = "你说的对")
    @ExcelProperty("备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}