package aj.soccer.teams;

import java.util.Collections;
import java.util.List;

/*package-private*/ class TeamImpl implements Team {

	private final String teamName;
	private final List<Player> players;

	/*package-private*/ public TeamImpl(String teamName, List<Player> players) {
		this.teamName = teamName;
		this.players = Collections.unmodifiableList(players);

	}

	@Override
	public String getName() {
		return teamName;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public String toString() {
		return teamName;
	}

}
