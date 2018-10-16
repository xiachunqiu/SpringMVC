package com.x.y.timer;

import com.x.y.mongodb.service.CommonMongodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimerTask;

@Component
public class TestTimer extends TimerTask {
    @Autowired
    private CommonMongodbService commonMongodbService;

    private static TestTimer testTimer;

    @PostConstruct
    public void init() {
        testTimer = this;
    }

    @Override
    public void run() {
        System.out.println("ssssssssssss" + testTimer.commonMongodbService.getTodayTaskRecordRepository().count());
    }
}