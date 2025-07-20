package com.alexandre;


import com.alexandre.records.JwtConverterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConverterProperties.class)
public class DeliveryServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(DeliveryServiceApplication.class, args);
    }
}
