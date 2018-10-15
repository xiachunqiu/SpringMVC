package com.x.y.mongodb.service;

import com.x.y.mongodb.dao.ITodayTaskRecordRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class CommonMongodbService {
    @Autowired
    private MongoOperations commonMongodbDao;
    @Autowired
    private ITodayTaskRecordRepository todayTaskRecordRepository;
}