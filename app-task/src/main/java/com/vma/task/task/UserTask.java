package com.vma.task.task;

import cn.hutool.core.date.DateUtil;
import com.vma.task.annotion.TaskTag;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器
 *
 * @author huang
 * @create 2018-09-30 10:16
 **/
@Component
public class UserTask {

    /**
     * task
     */
    @TaskTag(describe = "更新数据")
    @Scheduled(cron = "0/5 * * * * ? ")
    public void task() {
        System.out.println("定时任务-----------:" + DateUtil.now());
    }


}
