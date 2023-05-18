package com.vibhuti.avkaash.autoconfig;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.vibhuti.avkaash.*")
@ComponentScan(basePackages = {"com.vibhuti.avkaash.*"})
@EntityScan("com.vibhuti.avkaash.*")
@PropertySource("classpath:application.properties")
public class AutoConfig {
}
