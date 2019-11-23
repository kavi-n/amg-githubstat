package com.amg.githubstat.domain;

import com.amg.githubstat.constants.AMGConstants;

import lombok.Builder;
import lombok.Data;

/**
 * Domain class that holds all the possible inputs of the exposed APIs.
 *
 */
@Data
@Builder
public class PullStatistics {

	private String repo;

	private String owner;

	private int range;

	private String sort;

	public static class PullStatisticsBuilder {

        private int range = AMGConstants.NUMBER_OF_WEEKS_IN_YEAR;

        private String sort;

		public PullStatisticsBuilder range(Integer range) {
			if (range == null) {
				this.range = AMGConstants.NUMBER_OF_WEEKS_IN_YEAR;
			} else {
				this.range = range;
			}
			return this;
		}

		public PullStatisticsBuilder sort(String sort) {
			if (sort == null) {
				this.sort = AMGConstants.SORT_DESC;
			} else {
				this.sort = sort;
			}
			return this;
		}
	}
}
