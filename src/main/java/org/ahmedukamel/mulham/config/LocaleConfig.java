package org.ahmedukamel.mulham.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleConfig {
    @Bean
    public AcceptHeaderLocaleResolver acceptHeaderLocaleResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(List.of(new Locale("en"), new Locale("ar")));
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
}