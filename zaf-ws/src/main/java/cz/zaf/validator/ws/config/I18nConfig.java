package cz.zaf.validator.ws.config;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import cz.zaf.common.MessageProvider;
import cz.zaf.common.MessageProviders;

@Configuration
public class I18nConfig {
	
	static public String MESSAGES_WEB = "messages_web";
	
    @Bean
    public LocaleResolver localeResolver() {
        // Uses a cookie to remember user’s selected language
        CookieLocaleResolver resolver = new CookieLocaleResolver("ZAF_LANGUAGE");
        resolver.setDefaultLocale(MessageProviders.detectLocale());
        resolver.setCookieMaxAge(Duration.ofDays(30));
        return resolver;
    }
    
    @Bean
    public MessageSource messageSource() {
        // Custom bridge to MessageProvider
        return new AbstractMessageSource() {
            @Override
            protected MessageFormat resolveCode(@NonNull String code, @NonNull Locale locale) {
            	MessageProvider provider = MessageProviders.getProvider(MESSAGES_WEB, locale);
                String msg = provider.get(code);
                return new MessageFormat(msg, locale);
            }
        };
    }    
}
