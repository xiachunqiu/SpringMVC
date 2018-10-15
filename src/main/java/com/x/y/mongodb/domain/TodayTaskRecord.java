package com.x.y.mongodb.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Document
public class TodayTaskRecord implements Serializable {
    @Id
    private String id;
    @Indexed
    private String childId;
    // 状态 0:未完成 1:已完成
    private String status;
    private String date;
    private String resId;
    private Integer resType;
    private Integer mouldType;
    // 优先级
    private String priority;
    private String resName;
    private String resDes;
    private Boolean canCreatReservation;
    // 按钮集合
    private List<Map<String, Object>> buttonList;
}