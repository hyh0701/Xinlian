package cn.iocoder.yudao.module.xinlian.mq.consumer;

import cn.iocoder.yudao.module.member.message.user.MemberUserCreateMessage;
import cn.iocoder.yudao.module.xinlian.service.xinlianMember.XinlianMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户移动端注册时，创建新联会员，基 {@link MemberUserCreateMessage} 消息
 *
 * @author owen
 */
@Component
@Slf4j
public class XinlianMemberByRegisterConsumer {

    @Resource
    private XinlianMemberService xinlianMemberService;

    @EventListener
    @Async // Spring Event 默认在 Producer 发送的线程，通过 @Async 实现异步
    public void onMessage(MemberUserCreateMessage message) {
        log.info("XinlianMemberByRegisterConsumer [onMessage][消息内容({})]", message);
        xinlianMemberService.createMemberByRegister(message.getUserId());
    }
}
