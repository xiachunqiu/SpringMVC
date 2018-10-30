package com.x.y.controller;


import com.x.y.common.Constants;
import com.x.y.common.Pager;
import com.x.y.domain.User;
import com.x.y.mongodb.service.CommonMongodbService;
import com.x.y.processor.IProcessor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.x.y.service.CommonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Getter
@Setter
public class BaseController {
    @Autowired
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CommonMongodbService commonMongodbService;

    User getCurrentUser(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(Constants.USER_SESSION_KEY);
        return object == null ? null : (User) object;
    }

    static IProcessor getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (IProcessor) class1.newInstance();
    }

    User getUserByName(String username) {
        User user = new User();
        user.setUsername(username);
        List<User> userList = commonService.getListByObj(user, new Pager(1, 1));
        return userList.size() > 0 ? userList.get(0) : null;
    }
}