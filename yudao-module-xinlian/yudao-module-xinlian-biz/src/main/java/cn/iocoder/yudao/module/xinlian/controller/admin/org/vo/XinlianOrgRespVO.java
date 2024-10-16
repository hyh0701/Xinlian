package cn.iocoder.yudao.module.xinlian.controller.admin.org.vo;

import cn.iocoder.yudao.module.xinlian.dal.dataobject.job.XinlianJobDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 组织架构管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class XinlianOrgRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4932")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "组织名字", example = "李四")
    @ExcelProperty("组织名字")
    private String name;

    @Schema(description = "是否叶子节点")
    @ExcelProperty(value = "是否叶子节点", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isLeaf;

    @Schema(description = "上级节点", example = "11331")
    @ExcelProperty("上级节点")
    private Long parentId;

    @Schema(description = "logo图片地址")
    @ExcelProperty("logo图片地址")
    private String logo;

    @Schema(description = "职位列表,逗号分割")
    @ExcelProperty("职位列表,逗号分割")
    private Set<Long> jobs;

    @Schema(description = "是否可用")
    @ExcelProperty(value = "是否可用", converter = DictConvert.class)
    @DictFormat("infra_boolean_string") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isEnable;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "职位列表")
    @ExcelProperty("职位列表")
    private List<XinlianJobDO> jobList;

}