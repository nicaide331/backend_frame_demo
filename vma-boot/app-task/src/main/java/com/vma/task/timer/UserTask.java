package com.vma.task.timer;

import cn.hutool.core.date.DateUtil;
import com.vma.task.annotion.VmaTask;
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
     * 定时器任务  需要使用@VmaTask来标记
     * task
     */
    @VmaTask(describe = "更新数据")
    @Scheduled(cron = "0/5 * * * * ? ")
    public void task() {
        System.out.println("定时任务-----------:" + DateUtil.now());
    }


}
