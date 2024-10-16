package cn.iocoder.yudao.module.xinlian.controller.admin.job.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 获取职位id和name Response VO")
@Data
public class XinlianJobSimpleRespVo {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名字
     */
    private String name;


}
