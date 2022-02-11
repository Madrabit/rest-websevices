package ru.madrabit.restwebsevices;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private final MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-internationalized")
    public String helloWorldInternationalized(@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
    }
}
