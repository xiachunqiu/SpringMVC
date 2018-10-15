package com.x.y.controller;

import com.x.y.common.Constants;
import com.x.y.common.ReturnValueType;
import com.x.y.common.Rtn;
import com.x.y.common.ViewExcel;
import com.x.y.domain.User;
import com.x.y.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/index")
@Log4j2
public class IndexController extends BaseController {
    @RequestMapping(value = "indexHome", method = RequestMethod.GET)
    public ModelAndView indexHome(HttpServletRequest request) {
        long c = super.getCommonMongodbService().getTodayTaskRecordRepository().count();
        super.getCommonMongodbService().getCommonMongodbDao().save("");
        System.out.println(c);
        User user = currentUser();
        if (user == null) {
            ModelMap modelMap = new ModelMap();
            return new ModelAndView("/login", modelMap);
        } else {
            ModelMap modelMap = new ModelMap();
            request.getSession().setAttribute("user", user);
            return new ModelAndView("/index", modelMap);
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Rtn login(String username, String password, HttpServletRequest request) {
        Rtn rtn = new Rtn(ReturnValueType.success, "登录成功");
        try {
            Assert.isTrue(StringUtils.isNotNull(username) && StringUtils.isNotNull(password), "用户名或密码不能为空");
            request.getSession().setAttribute(Constants.USER_SESSION_KEY, new User());
        } catch (Exception e) {
            rtn.setValue(ReturnValueType.fail);
            rtn.setDes(e.getMessage());
        }
        return rtn;
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public ModelAndView export() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<User> list = new ArrayList<>();
            User user = new User();
            user.setRealName("张三");
            user.setAddress("故宫");
            list.add(user);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_hhmmss");
            String time = df.format(new Date());
            ViewExcel.ExcelViewModel viewMode = new ViewExcel.ExcelViewModel().filename("列表_" + time);
            viewMode.addSheet("列表", list, (idx, u) -> {
                User info = (User) u;
                return new String[]{idx + 1 + "", info.getRealName(), info.getAddress()};
            }).titles(new String[]{"序号", "姓名", "地址"});
            map.put(ViewExcel.MODEL_ATTR_NAME, viewMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView(new ViewExcel(), map);
    }
}