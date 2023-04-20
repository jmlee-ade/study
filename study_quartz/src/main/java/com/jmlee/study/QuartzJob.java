package com.jmlee.study;

import com.jmlee.study.domain.posts.Posts;
import com.jmlee.study.domain.posts.PostsRepository;
import com.jmlee.study.domain.posts.PostsSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jmlee
 */
@Slf4j
@Component
@PersistJobDataAfterExecution // Job이 동작 중에 jobDataMap 변경 시 선언 필요
@DisallowConcurrentExecution
public class QuartzJob implements Job {

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
      log.info("Quatz Job Executed");

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        log.info("dataMap data : {}", dataMap.get("date"));
        log.info("dataMap executeCount : {}", dataMap.get("executeCount"));

        int cnt = (int) dataMap.get("executeCount");
        dataMap.put("executeCount", ++cnt);

        PostsSaveRequestDto post = new PostsSaveRequestDto();
        post.setAuthor("jmlee");
        post.setContent("test");
        post.setTitle("test");
        postsRepository.save(post.toEntity()).getId();
    }
}
