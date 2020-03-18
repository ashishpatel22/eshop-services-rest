package com.akp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aashish Patel
 */
@SpringBootApplication
@Configuration
public class EshopServicesRestApplication {

    private static final Logger logger = LoggerFactory.getLogger(EshopServicesRestApplication.class);

    public static void main(String[] args) {
        logger.info("EshopServicesRestApplication starting..................", EshopServicesRestApplication.class.getSimpleName());
        SpringApplication.run(EshopServicesRestApplication.class, args);
    }
}
