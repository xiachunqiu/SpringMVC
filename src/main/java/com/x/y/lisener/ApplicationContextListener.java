package com.x.y.lisener;

import com.x.y.utils.MyWebApplicationContextUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Log4j2
@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        log.info("spring上下文赋值");
        MyWebApplicationContextUtils.setWebApplicationContext(ctx);
    }
}