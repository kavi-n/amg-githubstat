package com.amg.githubstat.util;

import java.net.URI;

import com.amg.githubstat.exception.GitHubStatErrorCode;
import com.amg.githubstat.exception.GithubStatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


import lombok.extern.slf4j.Slf4j;

/**
 * Class that holds all rest invoker methods based on request type.
 *
 */
@Component
@Slf4j
public class RestInvoker {

	public String get(String url) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(url);
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = restTemplate.getForEntity(uri, String.class);
		} catch (HttpClientErrorException exception) {
			log.error("Error while client - ", exception);
			if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw new GithubStatException(GitHubStatErrorCode.GH_EC_1003);
			}
		}catch (ResourceAccessException accessException){
			log.error("Error while access the endpoint - ",accessException);
			throw new GithubStatException(GitHubStatErrorCode.GH_EC_1004);
		}
		return responseEntity.getBody();
	}

}
