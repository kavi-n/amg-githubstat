package com.amg.githubstat.domain;

import java.io.Serializable;

import com.amg.githubstat.exception.GitHubStatErrorCode;
import com.amg.githubstat.exception.GithubStatException;
import com.amg.githubstat.util.ErrorMessageResolver;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that holds the format of error response.
 *
 */
@NoArgsConstructor
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 3704404758984756217L;

	@Getter
	@Setter
	@JsonProperty("Message")
	private String errorMessage;

	@Getter
	@Setter
	@JsonProperty("Code")
	private String errorCode;

	public ErrorResponse(GithubStatException e) {
		this.errorMessage = e.getError().getErrorMessage();
		this.errorCode = e.getError().getErrorCode();
	}

	public ErrorResponse(GitHubStatErrorCode errorCode, String... params) {
		this.errorMessage = ErrorMessageResolver.resolveErrorMessage(errorCode, params);
		this.errorCode = errorCode.name();
	}
}
