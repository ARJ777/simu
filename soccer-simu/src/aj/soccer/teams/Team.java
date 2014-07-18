package aj.soccer.teams;

import java.util.List;

/**
 * Describes a single team.
 */
public interface Team {

	/**
	 * Obtains the name of the team.
	 * 
	 * @return The team name.
	 */
	public String getName();

	/**
	 * Obtains the list of all potential players in a team.
	 * 
	 * @return The list of players.
	 */
	public List<Player> getPlayers();

}
