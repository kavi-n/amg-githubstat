package com.amg.githubstat.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amg.githubstat.domain.GithubStatResponse;
import com.amg.githubstat.domain.ValidationErrorResponse;
import com.amg.githubstat.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller advicer to handle all exceptions in one class.
 *
 */
@ControllerAdvice
@RestController
@Slf4j
public class GithubStatResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler({ GithubStatException.class })
	public final ResponseEntity<GithubStatResponse> handleGithubStatException(GithubStatException ex) {
		return new ResponseEntity<>(ResponseUtil.buildFailureResponse(ex), ex.getErrorCode().getHttpStatus());
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler({ Exception.class })
	public final ResponseEntity<GithubStatResponse> handleGenericException(Exception e) {
		log.error("General exception occurred", e);
		return new ResponseEntity<>(ResponseUtil.buildFailureResponse(GitHubStatErrorCode.GH_EC_1001),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ ValidationException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(ValidationException ex) {
		ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getMessage());
		validationErrorResponse.setErrorCode(GitHubStatErrorCode.GH_EC_1001.name());
		validationErrorResponse.setErrorMessages(errorMessages);
		return new ResponseEntity<>(ResponseUtil.buildFailureResponse(validationErrorResponse), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
		List<String> errorMessages = new ArrayList<>();

		ex.getBindingResult().getFieldErrors().forEach(
				error -> errorMessages.add(StringUtils.capitalize(error.getField()) + " " + error.getDefaultMessage()));

		validationErrorResponse.setErrorCode(GitHubStatErrorCode.GH_EC_1002.name());
		validationErrorResponse.setErrorMessages(errorMessages);
		return new ResponseEntity<>(ResponseUtil.buildFailureResponse(validationErrorResponse), HttpStatus.BAD_REQUEST);
	}
}
