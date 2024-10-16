package cn.iocoder.yudao.module.xinlian.controller.app.xinlianMember.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "APP - 新联会员 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppXinlianMemberRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "16896")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "会员名字", example = "芋艿")
    @ExcelProperty("会员名字")
    private String name;

    @Schema(description = "会员昵称", example = "芋艿")
    @ExcelProperty("会员昵称")
    private String nickname;

    @Schema(description = "性别")
    @ExcelProperty(value = "性别", converter = DictConvert.class)
    @DictFormat("system_user_sex") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Short sex;

    @Schema(description = "头像")
    @ExcelProperty("头像")
    private String avatar;

    @Schema(description = "自定义头像")
    @ExcelProperty("自定义头像")
    private String customAvatar;

    @Schema(description = "主要头衔", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("主要头衔")
    private String mainTitle;
}
