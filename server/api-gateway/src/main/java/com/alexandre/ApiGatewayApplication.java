package com.alexandre;

import com.alexandre.record.FrontEndProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FrontEndProperties.class)
public class ApiGatewayApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
