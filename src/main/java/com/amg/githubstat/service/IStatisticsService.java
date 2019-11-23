package com.amg.githubstat.service;

import java.util.Map;
import java.util.Map.Entry;

import com.amg.githubstat.domain.PullStatistics;

/**
 * Service interface for accessing statistics service.
 *
 */
public interface IStatisticsService {

	/**
	 * Method that holds the business logic to get active day of a week over the
	 * year.
	 * 
	 * @param pullStatistics the pull statistics input
	 * @return the active day entry
	 * @throws Exception
	 */
	Entry<String, Integer> getActiveDay(PullStatistics pullStatistics) throws Exception;

	/**
	 * Method that holds the business logic to calculate the weekly statistics.
	 * 
	 * @param pullStatistics the pull statistics input
	 * @return the weekly statistics
	 * @throws Exception
	 */
	Map<String, Integer> getWeeklyCommitStatistics(PullStatistics pullStatistics) throws Exception;
}
