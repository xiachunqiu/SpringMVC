package com.x.y.mongodb.dao;

import com.x.y.mongodb.domain.TodayTaskRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITodayTaskRecordRepository extends MongoRepository<TodayTaskRecord, String> {
    List<TodayTaskRecord> getByChildIdAndStatusAndDate(String childId, String status, String date);

    int countByChildIdAndDate(String childId, String date);

    List<TodayTaskRecord> getByChildIdAndDateAndResId(String childId, String date, String resId);
}