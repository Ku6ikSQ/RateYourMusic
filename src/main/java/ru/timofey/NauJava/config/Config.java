package ru.timofey.NauJava.config;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.timofey.NauJava.entities.Track;

@Configuration
public class Config {

    // Имя приложения из application.properties
    @Value("${app.name}")
    private String appName;

    // Версия приложения из application.properties
    @Value("${app.version}")
    private String appVersion;

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public List<Track> tracksContainer() {
        return new ArrayList<>();
    }

    // Этот метод сработает после создания бина Config
    @PostConstruct
    public void init() {
        System.out.println("Приложение: " + appName + ", версия: " + appVersion);
    }
}