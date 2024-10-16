package cn.iocoder.yudao.module.xinlian.service.advice;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.xinlian.controller.admin.advice.vo.*;
import cn.iocoder.yudao.module.xinlian.controller.app.advice.vo.AppAdviceSaveReqVO;
import cn.iocoder.yudao.module.xinlian.dal.dataobject.advice.AdviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 咨询记录 Service 接口
 *
 * @author 平台用户
 */
public interface AdviceService {

    /**
     * 创建咨询记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAdvice(@Valid AdviceSaveReqVO createReqVO);

    /**
     * 创建咨询记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAppAdvice(@Valid AppAdviceSaveReqVO createReqVO);
    /**
     * 更新咨询记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAdvice(@Valid AdviceSaveReqVO updateReqVO);

    /**
     * 删除咨询记录
     *
     * @param id 编号
     */
    void deleteAdvice(Long id);

    /**
     * 获得咨询记录
     *
     * @param id 编号
     * @return 咨询记录
     */
    AdviceDO getAdvice(Long id);

    /**
     * 获得咨询记录分页
     *
     * @param pageReqVO 分页查询
     * @return 咨询记录分页
     */
    PageResult<AdviceDO> getAdvicePage(AdvicePageReqVO pageReqVO);

    /**
     * 获得咨询记录分页
     *
     * @param pageReqVO 分页查询
     * @return 咨询记录分页
     */
    PageResult<AdviceDO> getAppAdvicePage(AdvicePageReqVO pageReqVO);

}