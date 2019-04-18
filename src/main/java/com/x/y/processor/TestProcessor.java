package com.x.y.processor;

import com.x.y.controller.BaseController;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Log4j2
public class TestProcessor extends BaseController implements IProcessor, Job {
    private static TestProcessor testProcesser;

    @PostConstruct
    public void init() {
        testProcesser = this;
    }

    @Override
    public void execute(JobExecutionContext arg0) {
        process();
    }

    @Override
    public void process() {
        log.info("testProcesser定时任务执行:" + testProcesser.getCommonService().getClass().getName());
    }
}