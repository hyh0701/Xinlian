package cn.iocoder.yudao.module.xinlian.controller.app.org.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Administrator
 */
@Schema(description = "用户APP - 指定组织和职位 Request VO")
@Data
public class AppOrgJobMemberReqVO {

    /**
     * 当前的组织节点
     */
    @Schema(description = "组织ID", example = "2")
    private Long orgId;

    @Schema(description = "职位ID")
    private Long jobId;

}