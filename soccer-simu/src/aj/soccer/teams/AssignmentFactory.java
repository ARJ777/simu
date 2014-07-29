package aj.soccer.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AssignmentFactory {

	private static class Counter {

		public Counter(Formation formation) {
			// TODO Auto-generated constructor stub
		}

		public boolean take(Position position) {
			// TODO Auto-generated method stub
			return false;
		}

		public int total() {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	/**
	 * Selects players from the given list to fill all of the positions in the formation.
	 * <p/>Restriction 1: All active players must be selected.
	 * <br/>Restriction 2: Any pre-specified position must be respected.
	 * <p/>Note: The player data are not changed, e.g. selected players are not made active.
	 * 
	 * @param players - The list of all potential players.
	 * @return A map from player to position for each selected player.
	 */
	public Map<Player, Position> assignPlayerPositions(Formation formation, List<Player> players) {
		// Counts of unfilled slots for each position.
		Counter counter = new Counter(formation);
		// Ambiguous, pre-selected players.
		List<Player> activePlayers = new ArrayList<>();
		// Unselected players.
		List<Player> inactivePlayers = new ArrayList<>();
		Map<Player, Position> assignedPlayers = assignUnambiguousActivePositions(counter, players, activePlayers, inactivePlayers);
		// Calculate how many assignments still need to be made.
		final int numActive = activePlayers.size();
		final int numInactive = counter.total() - numActive;
		if (numActive == 0 && numInactive == 0) return assignedPlayers;
		// Resolve ambiguity.
		int startPos = 0;
		while (true) {
			if (numActive > 0 &&
					!assignAmbigousActivePositions(assignedPlayers, counter, activePlayers, startPos))
				return null; // Failure.
			if (numInactive == 0
					|| assignInactivePositions(assignedPlayers, counter, inactivePlayers, numInactive))
				return assignedPlayers; // Success.
			// Backtrack to last ambiguous, active player.
			if (numActive == 0) return null; // Failure.
			startPos = numActive - 1;
		}
	}

	private Map<Player, Position> assignUnambiguousActivePositions(
			Counter counter, List<Player> players, 
			List<Player> activePlayers, List<Player> inactivePlayers)
			{
		Map<Player, Position> assignedPlayers = new HashMap<>();
		for (Player player : players) {
			if (player.isActive()) {
				Position position = getUnambiguousPosition(player);
				if (position == null) {
					activePlayers.add(player); // Defer assignment.
				} else {
					if (!counter.take(position)) return null; // Failure.
					assignedPlayers.put(player, position);
				}
			} else {
				inactivePlayers.add(player); // Defer assignment.
			}
		}
		return assignedPlayers; // Success.
			}

	private Position getUnambiguousPosition(Player player) {
		Position position = player.getPosition();
		if (position == null && player.getAllowedPositions().size() > 1)
			position = player.getAllowedPositions().get(0);
		return position;
	}

	// Disambiguate all unassigned, active players.
	private boolean assignAmbigousActivePositions(
			Map<Player, Position> assignedPlayers,
			Counter counter, List<Player> activePlayers,
			int lastPos) 
	{
		final int length = activePlayers.size();
		int pos = lastPos;
		while (pos < length) {
			Player player = activePlayers.get(pos);
			Position position = disambiguatePosition(
					counter, player.getAllowedPositions(), assignedPlayers.get(player));
			assignedPlayers.put(player, position);
			if (position != null) {
				// Move forward to next player.
				pos++;
			} else {
				// Backtrack to previous player.
				pos--;
				if (pos < 0) return false; // Failure.
			}
		}
		return true;
	}

	// Finds the next position in the player's list that is feasible.
	private Position disambiguatePosition(
			Counter counter,
			List<Position> allowedPositions, Position lastPosition) 
	{
		Iterator<Position> iter = allowedPositions.iterator();
		if (lastPosition != null) {
			// Fast-forward to that position.
			while (iter.hasNext()) {
				Position aPosition = iter.next();
				if (aPosition == lastPosition) break;
			}
		}
		// Find next feasible position.
		while (iter.hasNext()) {
			Position position = iter.next();
			if (counter.take(position))
				return position;
		}
		return null;
	}

	// Assign the given number of inactive players to the remaining positions.
	private boolean assignInactivePositions(
			Map<Player, Position> assignedPlayers, Counter counter,
			List<Player> inactivePlayers, int numInactive) {
		// TODO Auto-generated method stub
		return false;
	}

}
