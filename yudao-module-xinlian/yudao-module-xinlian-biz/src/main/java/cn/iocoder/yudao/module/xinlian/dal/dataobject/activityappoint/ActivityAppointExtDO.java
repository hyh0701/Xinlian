package cn.iocoder.yudao.module.xinlian.dal.dataobject.activityappoint;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.activity.XinlianActivityDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 活动报名 DO
 *
 * @author 芋道源码
 */
@Data
public class ActivityAppointExtDO extends ActivityAppointDO {
    /**
     * 活动详情
     */
    private XinlianActivityDO activity;

}