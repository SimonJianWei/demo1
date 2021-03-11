package com.example.job;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/11/27/11:13
 * @Description:定时任务
 */
@Component
public class ScheduledTasks implements SchedulingConfigurer {
    @Autowired
//    private MqDemo mqdemo;

    private static ExecutorService espoolDB = Executors.newFixedThreadPool(2);

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addFixedDelayTask(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("定时任务-1启动");
            }
        }, 1000 * 60 * 60 * 24 * 365 * 10);
        //设置定时任务线程池
//        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
    }
}
