package aj.soccer.team;

import java.util.Collections;
import java.util.List;

import aj.soccer.data.Formation;
import aj.soccer.data.Player;
import aj.soccer.data.Team;

/*package-private*/ class TeamImpl implements Team {

	private final String teamName;
	private final List<Player> players;
	private Formation formation;

	/*package-private*/ public TeamImpl(String teamName, List<Player> players, Formation formation) {
		this.teamName = teamName;
		this.setFormation(formation);
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

	@Override
	public Formation getFormation() {
		return formation;
	}

	@Override
	public void setFormation(Formation formation) {
		this.formation = formation;
	}

}
