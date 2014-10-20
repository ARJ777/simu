import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class Matches {

	private static final String MATCHES_RESOURCE = "resource/matches/";
	private static final String MATCHES_EXTENSION = ".matches";
	
	private static final int MAX_NUM_TRIES = 1000;
	
	public final League league;
	public final int numTeams;
	public final int[][] wins, losses, draws, matches;
	public int currentRound = 1;
	
	public Matches(League league, int[][] wins, int[][] losses, int[][] draws) {
		this.league = league;
		numTeams = league.numTeams;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
		matches = new int[numTeams][numTeams];
		calculateMatches();
	}
	
	public Matches(League league, boolean restart) throws IOException {
		this.league = league;
		numTeams = league.numTeams;
		wins = new int[numTeams][numTeams];
		losses = new int[numTeams][numTeams];
		draws = new int[numTeams][numTeams];
		if (!restart) loadGames();
		matches = new int[numTeams][numTeams];
		calculateMatches();
	}
	
	private void loadGames() throws IOException {
		File file = new File(MATCHES_RESOURCE+league.name+MATCHES_EXTENSION);
		if (!file.exists()) return; // Reset competition.
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = bufferedReader.readLine();
		String[] dims = line.split(",");
		int numRows = Integer.valueOf(dims[0]);
		int numCols = Integer.valueOf(dims[1]);
		if (numRows != numTeams || numCols != numTeams) return; // Reset competition.
		int row = -1;
		while ((line = bufferedReader.readLine()) != null)
		{
			line = line.trim();
			if (line.isEmpty() || line.startsWith("#")) continue;
			row++;
			String[] gamesPerTeam = line.split(",");
			for (int col = 0; col < numTeams; col++) {
				String[] teamGames = gamesPerTeam[col].split("/");
				wins[row][col] = Integer.valueOf(teamGames[0]);
				losses[row][col] = Integer.valueOf(teamGames[1]);
				draws[row][col] = Integer.valueOf(teamGames[2]);
			}
		}
		bufferedReader.close();
	}

	private void calculateMatches() {
		int maxNumMatches = 0;
		for (int row = 0; row < numTeams; row++) {
			int numMatches = 0;
			for (int col = 0; col < numTeams; col++) {
				int _numMatches = matches[row][col] = wins[row][col] + losses[row][col] + draws[row][col];
				numMatches += _numMatches;
			}
			if (numMatches > maxNumMatches) maxNumMatches = numMatches;
		}
		currentRound = maxNumMatches + 1;
	}

	public int[] pairTeams(int maxNumRematches) {
		int numTries = 0;
		loop:
		while (numTries++ < MAX_NUM_TRIES) {
			int[] selection = league.pairTeams();
			{
				int index1 = -1;
				for (int index2 : selection) {
					index1++;
					if (index2 < 0) continue; // Did not play.
					if (matches[index1][index2] > maxNumRematches) break loop;
				}
				return selection;
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		League league = new League("world");
		System.out.printf("Loaded league: %s\n", league.name);
		Matches matches = new Matches(league, false);
		int row = -1;
		for (Team team : league.teams) {
			row++;
			System.out.printf(" + %s:", team.name);
			int col = -1;
			for (Team team2 : league.teams) {
				col++;
				System.out.printf(" %s/%d/%d/%d", team2.name, matches.wins[row][col], matches.losses[row][col], matches.draws[row][col]);
			}
			System.out.printf("\n");
		}
		System.out.printf("Current round to be played: %s\n", matches.currentRound);
		int[] selection = matches.pairTeams(0);
		if (selection == null) {
			System.out.printf("Could not select new matches\n");
		} else {
			Set<Integer> done = new HashSet<Integer>();
			int team1 = -1;
			for (Integer team2 : selection) {
				team1++;
				if (done.contains(team1)) continue;
				done.add(team1);
				if (team2 < 0) {
					System.out.printf("\"%s\" HAS A BYE\n", league.getTeam(team1).name);
					continue;
				}
				if (done.contains(team2)) continue;
				System.out.printf("\"%s\" versus \"%s\"\n", league.getTeam(team1).name, league.getTeam(team2).name);
				done.add(team2);
			}
		}
	}

}
