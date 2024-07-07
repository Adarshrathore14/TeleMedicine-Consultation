package com.telemedicine.registration.configurations;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Random;
@Configuration
public class BeanConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Random random(){
        return new Random();
    }

}
