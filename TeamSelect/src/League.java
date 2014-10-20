import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class League {

	private static final String LEAGUE_RESOURCE = "resource/leagues/";
	private static final String TEAMS_EXTENSION = ".teams";
	
	public final String name;
	public final List<Team> teams;
	public final int numTeams;
	
	public League(String name, List<Team> teams) {
		this.name = name;
		this.teams = teams;
		numTeams = teams.size();
	}
	
	public League(String name) throws IOException {
		this.name = name;
		teams = new ArrayList<Team>();
		loadTeams(name);
		numTeams = teams.size();
	}

	private void loadTeams(String name) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(LEAGUE_RESOURCE+name+TEAMS_EXTENSION));
		String line;
		while ((line = bufferedReader.readLine()) != null)
		{
			line = line.trim();
			if (line.isEmpty() || line.startsWith("#")) continue;
			teams.add(new Team(line));
		}
		bufferedReader.close();
	}
	
	private static final Random generator = new Random();

	public int[] pairTeams() {
		int[] versus = new int[numTeams];
		for (int i = 0; i < numTeams; i++) versus[i] = -1;
		List<Integer> notDone = new ArrayList<Integer>();
		for (int i = 0; i < numTeams; i++) notDone.add(i);
		while (notDone.size() > 1) {
			int i = generator.nextInt(notDone.size());
			int index1 = notDone.remove(i);
			int j = generator.nextInt(notDone.size());
			int index2 = notDone.remove(j);
			versus[index1] = index2;
			versus[index2] = index1;
		}
		return versus;
	}

	public static void main(String[] args) throws IOException {
		League league = new League("world");
		System.out.printf("Loaded league: %s\n", league.name);
		for (Team team : league.teams) {
			System.out.printf(" + %s:", team.name);
			for (int strength : team.strengths)
				System.out.printf(" %d", strength);
			System.out.printf("\n");
		}
	}

	public Team getTeam(int index) {
		return teams.get(index);
	}
	
}
