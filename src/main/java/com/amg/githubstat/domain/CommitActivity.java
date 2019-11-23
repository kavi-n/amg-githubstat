/**
 * 
 */
package com.amg.githubstat.domain;

import java.util.List;

import lombok.Data;

/**
 * Domain that maps to the git statistics API response.
 *
 */
@Data
public class CommitActivity {

	private int total;

	private long week;

	private List<Integer> days;

}
