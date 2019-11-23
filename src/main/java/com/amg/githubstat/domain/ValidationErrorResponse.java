package com.amg.githubstat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * A generic response format for any validation related errors.
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ValidationErrorResponse implements Serializable {

  private static final long serialVersionUID = 2477277897080284380L;

  @JsonProperty("Code")
  private String errorCode;

  @JsonProperty("Messages")
  private List<String> errorMessages;

}
