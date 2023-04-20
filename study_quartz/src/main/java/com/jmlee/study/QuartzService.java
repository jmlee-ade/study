package com.jmlee.study;

import com.jmlee.study.listener.QuartzJobListener;
import com.jmlee.study.listener.QuartzTriggerListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jmlee
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzService {
    private final Scheduler scheduler;

    @PostConstruct
    public void init() {
        try {
            scheduler.clear();
            scheduler.getListenerManager().addJobListener(new QuartzJobListener());
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());
            Map paramsMap = new HashMap<>();
            paramsMap.put("executeCount", 1);
            paramsMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            addJob(QuartzJob.class, "QuartzJob", "QuartzJob", paramsMap, "0/5 * * * * ?");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public <T extends Job> void addJob(Class<? extends Job> job, String name, String desc, Map paramsMap, String cron) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job, name, desc, paramsMap);
        Trigger trigger = buildCronTrigger(cron);
        if(scheduler.checkExists(jobDetail.getKey())) scheduler.deleteJob(jobDetail.getKey());
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public <T extends Job> JobDetail buildJobDetail(Class<? extends Job> job, String name, String desc, Map paramsMap) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(paramsMap);

        return JobBuilder.newJob(job).withIdentity(name).withDescription(desc).usingJobData(jobDataMap).build();
    }

    private Trigger buildCronTrigger(String cronExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                .build();
    }
}
