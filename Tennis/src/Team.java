import java.util.HashMap;
import java.util.Map;


public class Team {

	public final String teamName;
	private final Map<String, Player> players = new HashMap<String,Player>();

	public Team(String teamName) {
		this.teamName = teamName;
	}

	public void put(String playerName, Player player) {
		players.put(playerName, player);
	}

	public Player get(String playerName) {
		return players.get(playerName);
	}
	
}
