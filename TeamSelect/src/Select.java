import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Select {

	private static final int MAX_NUM_MATCHES = 1;
	private static final int MAX_NUM_TRIES = 1000;

	public static void main(String[] args) throws IOException {
		List<String> teams = Common.loadList("resource/teams.lst");
		int numTeams = teams.size();
		if (numTeams % 2 != 0) {
			teams.add("DID NOT PLAY");
			numTeams++;
		}
		//System.out.printf("DEBUG: loaded teams=%s\n", teams);
		//Map<String,Integer> teamIndex = Common.listToMap(teams);
		//System.out.printf("DEBUG: Team index=%s\n", teamIndex);
		int[][] previousMatches = Common.loadArray("resource/matches.dat");
		if (previousMatches == null || previousMatches.length != numTeams) 
			previousMatches = new int[numTeams][numTeams];
		//Common.displayArray("DEBUG: previous matches", previousMatches);
		int previousRound = countMatches(previousMatches);
		List<Integer> selection = null;
		int numTries = 0;
		boolean ok = false;
		while (numTries++ < MAX_NUM_TRIES) {
			selection = select(numTeams);
			ok = validate(selection, previousMatches);
			if (ok) break;
		}
		System.out.printf("Round %d:\n", previousRound+1);
		if (ok) {
			//System.out.printf("DEBUG: selection=%s\n", selection);
			Common.saveArray(previousMatches, "resource/matches.dat");
			displaySelection(selection, teams);
		} else {
			System.out.printf("Failed to select teams after %d tries!\n", numTries);
		}
	}

	private static int countMatches(int[][] previousMatches) {
		int max = 0;
		for (int[] ateam : previousMatches) {
			int sumMatches = 0;
			for (int numMatches : ateam) sumMatches += numMatches;
			max = Math.max(max, sumMatches);
		}
		return max;
	}

	private static void displaySelection(List<Integer> selection, List<String> teams) {
		Set<Integer> done = new HashSet<Integer>();
		int team1 = -1;
		for (Integer team2 : selection) {
			team1++;
			if (done.contains(team2)) continue;
			//System.out.printf("%d versus %d\n", team1, team2);
			System.out.printf("\"%s\" versus \"%s\"\n", teams.get(team1), teams.get(team2));
			done.add(team1); done.add(team2);
		}
	}

	private static boolean validate(List<Integer> selection, int[][] matches) {
		boolean ok = true;
		{
			int index1 = 0;
			for (int index2 : selection)
				if (++matches[index1++][index2] > MAX_NUM_MATCHES) ok = false;
		}
		if (!ok) {
			int index1 = 0;
			for (int index2 : selection) {
				matches[index1++][index2]--;
			}			
		}
		return ok;
	}

	private static final Random generator = new Random();

	private static List<Integer> select(int num) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < num; i++) list.add(-1);
		List<Integer> notDone = new ArrayList<Integer>();
		for (int i = 0; i < num; i++) notDone.add(i);
		while (notDone.size() > 0) {
			int i = generator.nextInt(notDone.size());
			int index1 = notDone.remove(i);
			int j = generator.nextInt(notDone.size());
			int index2 = notDone.remove(j);
			list.set(index1, index2);
			list.set(index2, index1);
		}
		return list;
	}

}
