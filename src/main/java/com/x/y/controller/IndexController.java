package com.x.y.controller;

import com.x.y.common.Constants;
import com.x.y.common.ReturnValueType;
import com.x.y.common.Rtn;
import com.x.y.common.ViewExcel;
import com.x.y.domain.User;
import com.x.y.sdk.gt.GtConfig;
import com.x.y.sdk.gt.GtLib;
import com.x.y.utils.MD5Utils;
import com.x.y.utils.XMemCachedUtils;
import com.x.y.utils.QRCodeUtils;
import com.x.y.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/index")
@Log4j2
public class IndexController extends BaseController {
    @Qualifier("schedulerFactoryBeans")
    @Autowired
    private Scheduler scheduler;

    @RequestMapping(value = "indexHome", method = RequestMethod.GET)
    public ModelAndView indexHome(HttpServletRequest request) {
        return new ModelAndView(super.getCurrentUser(request) == null ? "/login" : "/index");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Rtn login(String username, String password, HttpServletRequest request) {
        Rtn rtn = new Rtn(ReturnValueType.success, "登录成功");
        try {
            Assert.isTrue(StringUtils.isNotNull(username) && StringUtils.isNotNull(password), "用户名或密码不能为空");
            String key = StringUtils.getIpAddress(request) + "." + username;
            Object errorCountCache = XMemCachedUtils.get(key);
            int errorCount = errorCountCache == null ? 0 : (int) errorCountCache;
            Assert.isTrue(errorCount < 5, "您的账号已锁定，请稍候再试！");
            validatorCode(request);
            User user = super.getUserByName(username);
            Assert.isTrue(user != null && MD5Utils.encryptByMD5(password).equals(user.getPassword()), getLoginErrorDes(key, errorCount));
            request.getSession().setAttribute(Constants.USER_SESSION_KEY, user);
        } catch (Exception e) {
            log.error(e);
            rtn.setValue(ReturnValueType.fail);
            rtn.setDes(e.getMessage());
        }
        return rtn;
    }

    private void validatorCode(HttpServletRequest request) {
        String clientId = StringUtils.getParameter(request, "client-Id", "");
        Assert.isTrue(StringUtils.isNotNull(clientId), "client-Id is null");
        GtLib gtSdk = new GtLib(GtConfig.getGtId(), GtConfig.getGtKey(), GtConfig.isNewFailBack());
        String challenge = request.getParameter(GtLib.fn_gt_challenge);
        String validate = request.getParameter(GtLib.fn_gt_validate);
        String secCode = request.getParameter(GtLib.fn_gt_sec_code);
        Object gtServerStatus = XMemCachedUtils.get(clientId + gtSdk.gtServerStatusSessionKey);
        Assert.isTrue(gtServerStatus != null, "验证码已过期，请刷新页面后重试！");
        int gtServerStatusCode = (int) gtServerStatus;
        Object clientIdCacheObject = XMemCachedUtils.get(clientId);
        Assert.isTrue(clientIdCacheObject != null, "验证码已过期，请刷新页面后重试！");
        String clientIdCache = clientIdCacheObject.toString();
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", clientIdCache);
        param.put("client_type", "web");
        param.put("ip_address", StringUtils.getIpAddress(request).split(",")[0]);
        int gtResult = gtServerStatusCode == 1 ? gtSdk.enhancedValidateRequest(challenge, validate, secCode, param)
                : gtSdk.failBackValidateRequest(challenge, validate, secCode);
        Assert.isTrue(gtResult == 1, "验证码已过期，请刷新页面后重试！");
    }

    private String getLoginErrorDes(String key, int errorCount) {
        String des = "用户名密码不符";
        XMemCachedUtils.set(key, 300, ++errorCount);
        int leftCount = 5 - errorCount;
        des += leftCount > 0 ? "，您还可以尝试" + leftCount + "次，之后将锁定五分钟" : "，您的账号已锁定，请稍候再试！";
        return des;
    }

    @RequestMapping("/gtRegister")
    public void start(HttpServletRequest request, HttpServletResponse response) {
        GtLib gtSdk = new GtLib(GtConfig.getGtId(), GtConfig.getGtKey(), GtConfig.isNewFailBack());
        String clientId = StringUtils.getParameter(request, "client-Id", "");
        if (StringUtils.isNotNull(clientId)) {
            HashMap<String, String> param = new HashMap<>();
            param.put("user_id", clientId);
            param.put("client_type", "web");
            param.put("ip_address", StringUtils.getIpAddress(request));
            int gtServerStatus = gtSdk.preProcess(param);
            XMemCachedUtils.set(clientId + gtSdk.gtServerStatusSessionKey, 120, gtServerStatus);
            XMemCachedUtils.set(clientId, 120, clientId);
            String resStr = gtSdk.getResponseStr();
            try {
                PrintWriter out = response.getWriter();
                out.println(resStr);
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    @RequestMapping(value = "/getQRCode", method = RequestMethod.GET)
    public void getQRCode(HttpServletResponse response) {
        try {
            response.setContentType("image/png");
            QRCodeUtils.writeToStream("123", response.getOutputStream());
        } catch (Exception e) {
            log.error("获取二维码失败！", e);
            throw new RuntimeException("显示图片出错", e);
        }
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
            map.put(Constants.EXCEL_VIEW_MODEL_KEY, viewMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView(new ViewExcel(), map);
    }

    @RequestMapping(value = "addJob", method = RequestMethod.GET)
    public Rtn addJob() {
        Rtn rtn = new Rtn(ReturnValueType.success, "成功");
        try {
            String jobClassName = "com.x.y.processor.TestProcessor";
            String jobName = jobClassName.substring(jobClassName.lastIndexOf(".") + 1);
            String triggerName = jobName + "Trigger";
            String cronExpression = "0 0/1 * * * ? *";
            String description = "测试定时任务每分钟执行";
            String jobGroupName = Scheduler.DEFAULT_GROUP;
            scheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobName, jobGroupName)
                    .withDescription(description).build();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(URLDecoder.decode(cronExpression, "utf-8")));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error(e);
            rtn.setValue(ReturnValueType.fail);
            rtn.setDes(e.getMessage());
        }
        return rtn;
    }
}