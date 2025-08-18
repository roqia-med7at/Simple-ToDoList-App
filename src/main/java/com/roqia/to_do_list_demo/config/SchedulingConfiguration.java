package com.roqia.to_do_list_demo.config;

import com.roqia.to_do_list_demo.service.TaskService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {

    private TaskService taskService;

    public SchedulingConfiguration(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 35 23 * * ?")
    public void monitor_daily_tasks(){
       taskService.markTasksAsOverDue();
    }
}
