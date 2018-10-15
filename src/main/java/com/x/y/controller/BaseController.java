package com.x.y.controller;


import com.x.y.common.Constants;
import com.x.y.domain.User;
import com.x.y.mongodb.service.CommonMongodbService;
import com.x.y.service.RedisService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.x.y.service.CommonService;

import javax.servlet.http.HttpServletRequest;

@Service
@Getter
@Setter
public class BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CommonMongodbService commonMongodbService;

    User currentUser() {
        Object object = request.getSession().getAttribute(Constants.USER_SESSION_KEY);
        return object == null ? null : (User) object;
    }
}