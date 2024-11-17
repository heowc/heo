package dev.heowc.heo.core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.utils.ParserCollectionStrategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ParserConfig {

    @Bean
    ParserConfiguration parserConfiguration() {
        final ParserConfiguration configuration = new ParserConfiguration();
        configuration.setLanguageLevel(LanguageLevel.JAVA_17);
        return configuration;
    }

    @Bean
    ParserCollectionStrategy parserCollectionStrategy(ParserConfiguration parserConfiguration) {
        return new ParserCollectionStrategy(parserConfiguration);
    }

    @Bean
    JavaParser javaParser(ParserConfiguration parserConfiguration) {
        return new JavaParser(parserConfiguration);
    }
}
