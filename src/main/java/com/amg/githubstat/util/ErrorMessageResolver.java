package com.amg.githubstat.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import com.amg.githubstat.exception.GitHubStatErrorCode;

import lombok.extern.slf4j.Slf4j;

/**
 * Resolver class that converts error code to equivalent configured message.
 *
 */
@Slf4j
public class ErrorMessageResolver {

	public static String resolveErrorMessage(GitHubStatErrorCode code, String[] params) {
		if (code == null) {
			return null;
		}
		try {
			Properties errorMessages = PropertiesLoaderUtils
					.loadProperties(new ClassPathResource("errorMessage_en.properties"));
			String message = errorMessages.getProperty(code.name());
			if (StringUtils.isEmpty(message)) {
				return null;
			}
			return String.format(message, params);
		} catch (IOException e) {
			log.error("Error resolving message ", e);
			return null;
		}
	}
}
