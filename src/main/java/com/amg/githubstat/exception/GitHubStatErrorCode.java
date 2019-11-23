package com.amg.githubstat.exception;

import org.springframework.http.HttpStatus;

/**
 * Enumerator that holds all error codes of the application and equivalent http status.
 *
 */
public enum GitHubStatErrorCode {

	GH_EC_1001(HttpStatus.INTERNAL_SERVER_ERROR), 
	GH_EC_1002(HttpStatus.BAD_REQUEST),
	GH_EC_1003(HttpStatus.BAD_REQUEST),
	GH_EC_1004(HttpStatus.SERVICE_UNAVAILABLE);

	private final HttpStatus httpStatus;

	GitHubStatErrorCode(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
