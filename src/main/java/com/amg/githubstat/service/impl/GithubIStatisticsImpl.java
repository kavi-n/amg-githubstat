package com.amg.githubstat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amg.githubstat.constants.AMGConstants;
import com.amg.githubstat.domain.CommitActivity;
import com.amg.githubstat.domain.PullStatistics;
import com.amg.githubstat.service.IStatisticsService;
import com.amg.githubstat.util.GithubStatisticsHelper;
import com.amg.githubstat.util.RestInvoker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation class for statistics service.
 *
 */
@Service
public class GithubIStatisticsImpl implements IStatisticsService {

	@Autowired
	RestInvoker restInvoker;

	@Autowired
	ObjectMapper mapper;

	@Value("${git.url.base}")
	String gitBaseUrl;

	@Value("${git.url.statistics.commit}")
	String gitCommitStatsPath;

	@Override
	public Entry<String, Integer> getActiveDay(PullStatistics pullStatistics) throws Exception {
		Entry<String, Integer> activeDayOfTheWeek = GithubStatisticsHelper
				.getActiveDayOfTheWeek(getDayWiseWeeklyAverage(pullStatistics));
		if (activeDayOfTheWeek.getValue() == 0) {
			// Handle error
		}
		return activeDayOfTheWeek;
	}

	@Override
	public Map<String, Integer> getWeeklyCommitStatistics(PullStatistics pullStatistics) throws Exception {
		return GithubStatisticsHelper.sortMap(pullStatistics.getSort(), getDayWiseWeeklyAverage(pullStatistics));
	}

	private Map<String, Integer> getDayWiseWeeklyAverage(PullStatistics pullStatistics) throws Exception {
		// 1. Invoke git statistics
		String gitResponse = restInvoker.get(buildGitStatisticsUrl(pullStatistics));
		List<CommitActivity> commitActivities = mapper.readValue(gitResponse,
				new TypeReference<ArrayList<CommitActivity>>() {
				});
		// 2. Get commit activity of last 'n' weeks
		List<CommitActivity> requestedRangeOfCommitActivities = commitActivities.subList(
				commitActivities.size() - Math.min(commitActivities.size(), pullStatistics.getRange()),
				commitActivities.size());
		// 3. Create a map that holds day of the week as key and list of commits on that
		// day over the given weeks as a list
		Map<String, List<Integer>> dayToCommitMap = GithubStatisticsHelper
				.createDayToCommitMap(requestedRangeOfCommitActivities);
		return GithubStatisticsHelper.calculateAverage(dayToCommitMap);
	}

	private String buildGitStatisticsUrl(PullStatistics pullStatistics) {
		return new StringBuilder().append(gitBaseUrl).append(pullStatistics.getOwner())
				.append(AMGConstants.URL_SEPARATOR).append(pullStatistics.getRepo()).append(gitCommitStatsPath)
				.toString();
	}
}
