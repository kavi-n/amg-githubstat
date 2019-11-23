package com.amg.githubstat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.repository.init.ResourceReader;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Message configuration to interpolate success, failure and validation messages
 * from corresponding properties file.
 *
 */
@Configuration
@Slf4j
public class MessageConfig implements WebMvcConfigurer {

  @Value("${success.file:messages}")
  private String successMessageFile;

  private static Properties successMessages;

  @Value("${validation.file:messages}")
  private String validationMessageFile;

  @Bean
  @Primary
  @Override
  public LocalValidatorFactoryBean getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setCommonMessages(getValidationMessages(validationMessageFile));
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Autowired
  public void loadSuccessMessages() {
    Properties successMessagesToSet = getProperties(successMessageFile);
    setSuccessMessages(successMessagesToSet);
  }

  public static Properties getSuccessMessages() {
    return successMessages;
  }

  public static void setSuccessMessages(Properties successMessagesToSet) {
    successMessages = successMessagesToSet;
  }

  private static Properties getValidationMessages(String validationMessageFile) {
    return getProperties(validationMessageFile);
  }

  private static Properties getProperties(String file) {
    Properties vailidationMessages = new Properties();
    try (InputStream inputStream = ResourceReader.class.getResourceAsStream("/" + file)) {
      vailidationMessages.load(inputStream);
    } catch (IOException e) {
      log.error("Exception occured while loading the property file: {}", file, e);
    }
    return vailidationMessages;
  }
}
