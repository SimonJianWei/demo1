package com.example;

import com.example.util.ProUtil;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableScheduling
@SpringBootApplication
@EnableRabbit
@EnableAsync
//@ComponentScan(basePackages = { "com.example.config"})
@ImportResource(locations = "classpath:/applicationContext-rabbit.xml")
public class DemoApplication {


    public static void main(String[] args) {
        ProUtil.context= SpringApplication.run(DemoApplication.class, args);
    }

    /***
     * spring schedule模式是单线程执行
     * 如果的第一个任务出现问题，就会导致后面任务出现阻塞
     * 故自定义线程
     * @return
     */
    //自定义Schedule线程池
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //线程池大小
        taskScheduler.setPoolSize(10);
        //线程名字前缀
        taskScheduler.setThreadNamePrefix("springboot-task");
        return taskScheduler;
    }

}