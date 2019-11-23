package com.amg.githubstat.web.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amg.githubstat.constants.ResponseMessages;
import com.amg.githubstat.domain.GithubStatResponse;
import com.amg.githubstat.domain.PullStatistics;
import com.amg.githubstat.service.IStatisticsService;
import com.amg.githubstat.util.ResponseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller class that holds the api information of github statistics.
 *
 */
@RestController
@Validated
@RequestMapping("/api/v1/github/stat")
@Api(tags = "Github Statistics", value = "Helps view commit statistics from github")
public class GithubStatisticsController {

	@Autowired
	IStatisticsService statisticsService;

	@ApiOperation(value = "Retrieve Commit Statistics")
	@GetMapping(path = "/{owner}/{repo}/activeday")
	public GithubStatResponse<Entry<String, Integer>> getCommitStats(
			@NotBlank @PathVariable(name = "owner", required = false) String owner,
			@NotBlank @PathVariable(name = "repo", required = false) String repo,
			@RequestParam(required = false) @Min(value = 1, message = "{range.validation}") @Max(value = 52, message = "{range.validation}") Integer week)
			throws Exception {
		PullStatistics pullStatistics = PullStatistics.builder().owner(owner).repo(repo).range(week).build();
		return ResponseUtil.buildSuccessResponse(ResponseMessages.STAT_RETRIEVAL_SUCCESS,
				statisticsService.getActiveDay(pullStatistics));
	}

	@ApiOperation(value = "Retrieve Average Commit Statistics")
	@GetMapping(path = "/{owner}/{repo}/weeklyaverage")
	public GithubStatResponse<Map<String, Integer>> getCommitStatAverage(
			@NotBlank @PathVariable(name = "owner", required = false) String owner,
                                                   @NotBlank @PathVariable(name = "repo", required = false) String repo,
                                                   @RequestParam(required = false)
                                                   @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE,
                                                           message = "{sort.validation}") String sort) throws Exception {
		PullStatistics pullStatistics = PullStatistics.builder().owner(owner).repo(repo).sort(sort).build();
		return ResponseUtil.buildSuccessResponse(ResponseMessages.STAT_RETRIEVAL_SUCCESS,
				statisticsService.getWeeklyCommitStatistics(pullStatistics));
	}

}
