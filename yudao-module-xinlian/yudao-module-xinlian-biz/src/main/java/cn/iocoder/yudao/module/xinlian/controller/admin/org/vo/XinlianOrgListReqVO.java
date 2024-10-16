package cn.iocoder.yudao.module.xinlian.controller.admin.org.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 组织架构管理列表 Request VO")
@Data
public class XinlianOrgListReqVO {

    @Schema(description = "组织名字", example = "李四")
    private String name;
    /**
     * 上级节点
     */
    @Schema(description = "上级节点", example = "2")
    private Long parentId;

    @Schema(description = "是否可用")
    private Boolean isEnable;

    @Schema(description = "备注信息", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}