package com.amg.githubstat.util;

import com.amg.githubstat.config.MessageConfig;
import com.amg.githubstat.domain.ErrorResponse;
import com.amg.githubstat.domain.GithubStatResponse;
import com.amg.githubstat.domain.ValidationErrorResponse;
import com.amg.githubstat.exception.GitHubStatErrorCode;
import com.amg.githubstat.exception.GithubStatException;
import org.springframework.util.StringUtils;

/**
 * Utility to build response objects.
 * 
 */
public class ResponseUtil {

  private ResponseUtil() {
  }

  /**
   * Method to build success response
   *
   * @param message
   *          success message
   * @param data
   *          data
   * @param params
   *          params for the message
   * @param <T>
   *          type of the data
   * @return success response
   */
  public static <T> GithubStatResponse<T> buildSuccessResponse(String message, T data, String... params) {
    String messageToSend = getMessage(message, params);
    return new GithubStatResponse<>(messageToSend, data);
  }

  private static String getMessage(String message, String... params) {
    String messageToSend = MessageConfig.getSuccessMessages().getProperty(message);
    if (!StringUtils.isEmpty(messageToSend)) {
      return String.format(messageToSend, params);
    }
    return message;
  }

  public static GithubStatResponse buildFailureResponse(GitHubStatErrorCode code, String... params) {
    return new GithubStatResponse<>(new GithubStatException(code, params));
  }

  /**
   * Method to build error response
   *
   * @param ex
   *          OMException
   * @return failure response
   */
  public static GithubStatResponse buildFailureResponse(GithubStatException ex) {
    return new GithubStatResponse<>(ex);
  }

  /**
   * Method to build failure response
   *
   * @param error
   *          error response
   * @return failure response
   */
  public static GithubStatResponse buildFailureResponse(ErrorResponse error) {
    return new GithubStatResponse<>(error);
  }

  /**
   * Method to build failure response
   *
   * @param validationError
   *          validation error
   * @return failure response
   */
  public static GithubStatResponse buildFailureResponse(ValidationErrorResponse validationError) {
    return new GithubStatResponse<>(validationError);
  }
}
