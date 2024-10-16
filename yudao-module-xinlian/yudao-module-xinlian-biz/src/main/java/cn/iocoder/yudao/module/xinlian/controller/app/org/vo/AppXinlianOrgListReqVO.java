package cn.iocoder.yudao.module.xinlian.controller.app.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "App - 组织架构管理列表 Request VO")
@Data
public class AppXinlianOrgListReqVO {

    /**
     * 当前的组织节点
     */
    @Schema(description = "当前的组织节点", example = "2")
    private Long id;

    @Schema(description = "是否可用")
    private Boolean isEnable;

}