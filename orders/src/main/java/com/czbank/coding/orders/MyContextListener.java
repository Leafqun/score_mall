package com.czbank.coding.orders;

import com.czbank.coding.orders.config.SSHConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @program: xiaoyu
 * @description:
 * @author: Leafqun
 * @date: 2018-07-16 18-38-22
 **/

@Slf4j
@WebListener
@Component
public class MyContextListener implements ServletContextListener {

    @Resource
    private SSHConnection conexionssh;

    public MyContextListener() {
        super();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContex初始化");
        conexionssh.connect();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContex初始化");
        conexionssh.close();
    }
}
