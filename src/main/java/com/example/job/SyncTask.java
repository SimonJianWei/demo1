package com.example.job;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/27/11:19
 * @Description:定时任务
 */
@Component
public class SyncTask {
    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void task1() {
        System.out.println(" fixedDelay 的间隔是前次任务的结束与下次任务的开始");
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void task2() {
        System.out.println(" fixedRate 每次任务结束后会从任务编排表中找下一次该执行的任务，" +
                "判断是否到时机执行。fixedRate 的任务某次执行时间再长也不会造成两次任务实例同时执行，" +
                "除非用了 @Async 注解。 fixedDelay 总是前一次任务完成后，延时固定长度然后执行一次任务");
    }


}
