package cn.iocoder.yudao.module.xinlian.controller.admin.xinlianMember.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 新联会员分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class XinlianMemberPageReqVO extends PageParam {

    @Schema(description = "会员名字", example = "芋艿")
    private String name;

    @Schema(description = "性别")
    private Short sex;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "允许登录")
    private Boolean permitLogin;

    @Schema(description = "联系电话")
    private String telephone;

    @Schema(description = "会员类型", example = "1")
    private Short memberType;

    @Schema(description = "主要头衔")
    private String mainTitle;

    @Schema(description = "主组织ID", example = "25118")
    //private Long mainOrgId;
    private List<Long> mainOrgId;

    @Schema(description = "职位ID", example = "26787")
    //private Long mainJobId;
    private List<Long> mainJobId;

    @Schema(description = "审核状态")
    private Integer state;

    @Schema(description = "是否启用")
    private Boolean isEnable;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}