package com.amg.githubstat.domain;

import com.amg.githubstat.exception.GithubStatException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A generic response format that will be used for Github Stat API
 * responses.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ApiModel
public class GithubStatResponse<T> implements Serializable {

  private static final long serialVersionUID = 7714348576591110710L;

  @ApiModelProperty(dataType = "boolean", value = "Indicates whether request is success or failure")
  private boolean success;

  @ApiModelProperty(dataType = "string", value = "Status message about the execution of the request")
  private String message;

  @ApiModelProperty(value = "Response data")
  private T data;

  @ApiModelProperty(value = "Execution errors if the request is not successful")
  private ErrorResponse error;

  @ApiModelProperty(value = "Validation errors if the given request payload do not match certain criteria")
  private ValidationErrorResponse validationError;

  public GithubStatResponse(String message, T data) {
    this.success = true;
    this.message = message;
    this.data = data;
  }

  public GithubStatResponse(GithubStatException ex) {
    this.success = false;
    this.error = new ErrorResponse(ex);
  }

  public GithubStatResponse(ErrorResponse error) {
    this.success = false;
    this.error = error;
  }

  public GithubStatResponse(ValidationErrorResponse validationError) {
    this.success = false;
    this.validationError = validationError;
  }
}
