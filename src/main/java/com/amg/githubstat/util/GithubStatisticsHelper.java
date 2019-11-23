package com.amg.githubstat.util;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.amg.githubstat.constants.AMGConstants;
import com.amg.githubstat.domain.CommitActivity;

/**
 * Helper class that holds utility methods used for statistics calculation.
 *
 */
public class GithubStatisticsHelper {

	private GithubStatisticsHelper() {
	}

	/**
	 * Method to create a map that holds days of the week as key and list of commits
	 * on that day over the given weeks.
	 * 
	 * @param commitActivities the commit activities response from git
	 * @return the resultant map
	 */
	public static Map<String, List<Integer>> createDayToCommitMap(List<CommitActivity> commitActivities) {
		Map<String, List<Integer>> dayToCommitMap = new HashMap<>();
		commitActivities
				.forEach(commitActivity -> IntStream.range(0, commitActivity.getDays().size()).forEach(index -> {
					// Git API response groups commits starting Sunday, which has enum value as 7 in
					// DayOfWeek
					String day = DayOfWeek.of(index == 0 ? 7 : index).name();
					dayToCommitMap.putIfAbsent(day, new ArrayList<Integer>());
					dayToCommitMap.get(day).add(commitActivity.getDays().get(index));
				}));
		return dayToCommitMap;
	}

	/**
	 * Method to calculate the average of list of integers against each key in the
	 * given map.
	 * 
	 * @param dayToCommitMap the input map
	 * @return the resultant map
	 */
	public static Map<String, Integer> calculateAverage(Map<String, List<Integer>> dayToCommitMap) {
		Map<String, Integer> daywiseAverageMap = new HashMap<>();
		dayToCommitMap.forEach((day, numberOfCommits) -> daywiseAverageMap.put(day,
				(int) Math.round(numberOfCommits.stream().mapToInt(val -> val).average().orElse(0.0))));
		return daywiseAverageMap;
	}

	/**
	 * Method to sort the map based on the value.
	 * 
	 * @param sortOrder         the sort order
	 * @param daywiseAverageMap the input map
	 * @return the sorted map
	 */
	public static Map<String, Integer> sortMap(String sortOrder, Map<String, Integer> daywiseAverageMap) {
		// Sort ascending by default
		Comparator<Entry<String, Integer>> comparator = Map.Entry.comparingByValue();
		if (AMGConstants.SORT_DESC.equalsIgnoreCase(sortOrder)) {
			// Sort descending comparator
			comparator = Map.Entry.<String, Integer>comparingByValue().reversed();
		}
		return daywiseAverageMap.entrySet().stream().sorted(comparator)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	/**
	 * Method to get the active day of the map.
	 * 
	 * @param daywiseAverageMap the input map
	 * @return the result entry that holds active day result
	 */
	public static Entry<String, Integer> getActiveDayOfTheWeek(Map<String, Integer> daywiseAverageMap) {
		return sortMap(AMGConstants.SORT_DESC, daywiseAverageMap).entrySet().stream().findFirst().get();
	}
}
