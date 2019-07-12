package com.czbank.coding.good.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: xiaoyu
 * @description:
 * @author: Leafqun
 * @date: 2018-07-16 19-37-15
 **/

@Component
@ConfigurationProperties(prefix = "spring.ssh")
@Data
public class SSH {

    private String host;

    private String user;

    private String pwd;

    private int port;

    private int localPort;

    private String remoteHost;

    private int remotePort;

}
