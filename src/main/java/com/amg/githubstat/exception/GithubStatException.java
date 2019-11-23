package com.amg.githubstat.exception;

import com.amg.githubstat.domain.ErrorResponse;

import lombok.Getter;

/**
 * Application specific custom exception class.
 *
 */
public class GithubStatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Getter
	private final ErrorResponse error;

	@Getter
	private final GitHubStatErrorCode errorCode;

	public GithubStatException(GitHubStatErrorCode errorCode, String... params) {
		this.error = new ErrorResponse(errorCode, params);
		this.errorCode = errorCode;
	}

}
