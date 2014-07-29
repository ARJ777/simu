package aj.soccer.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AssignmentFactory {

	private static class Constraints {

		public Constraints(Formation formation) {
			// TODO Auto-generated constructor stub
		}

		public boolean take(Position position) {
			// TODO Auto-generated method stub
			return false;
		}

		public int numAvailable() {
			// TODO Auto-generated method stub
			return 0;
		}

		public void give(Position position) {
			// TODO Auto-generated method stub

		}

		public boolean isFull(Position position) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	/**
	 * Selects players from the given list to fill all of the positions in the formation.
	 * <p/>Restriction 1: All active players must be selected.
	 * <br/>Restriction 2: Any pre-specified position must be respected.
	 * <p/>Note: The player data are not changed, e.g. selected players are not made active.
	 * 
	 * @param players - The list of all potential players.
	 * @return A map assigning each selected player to a position in the formation.
	 */
	public Map<Player, Position> assignPlayerPositions(Formation formation, List<Player> players) {
		Constraints counter = new Constraints(formation);
		// Assign active players with unambiguous positions.
		List<Player> activePlayers = new ArrayList<>();   // Ambiguous, pre-selected players.
		List<Player> inactivePlayers = new ArrayList<>(); // Currently unselected players.
		Map<Player, Position> assignments = assignUnambiguousActivePositions(counter, players, activePlayers, inactivePlayers);
		// Calculate how many assignments still need to be made.
		final int numRemaining = counter.numAvailable();
		final int numActiveRemaining = activePlayers.size();
		final int numInactiveNeeded = numRemaining - numActiveRemaining;
		if (numInactiveNeeded < 0 || numInactiveNeeded > inactivePlayers.size()) return null; // Failure.
		if (numRemaining == 0) return assignments;
		// Resolve ambiguity.
		int startPos = 0;
		while (true) {
			// Assign active players with ambiguous positions.
			if (numActiveRemaining > 0 &&
					!assignAmbigousActivePositions(assignments, counter, activePlayers, startPos))
				return null; // Failure.
			// Assign inactive players.
			if (numInactiveNeeded == 0
					|| assignInactivePositions(assignments, counter, inactivePlayers, numInactiveNeeded))
				return assignments; // Success.
			// Backtrack to last ambiguous, active player.
			if (numActiveRemaining == 0) return null; // Failure.
			startPos = numActiveRemaining - 1;
		}
	}

	private Map<Player, Position> assignUnambiguousActivePositions(
			Constraints counter, List<Player> players, 
			List<Player> activePlayers, List<Player> inactivePlayers)
			{
		Map<Player, Position> assignments = new HashMap<>();
		for (Player player : players) {
			if (player.isActive()) {
				Position position = getUnambiguousPosition(player);
				if (position == null) {
					activePlayers.add(player); // Defer assignment.
				} else {
					if (!counter.take(position)) return null; // Failure.
					assignments.put(player, position);
				}
			} else {
				inactivePlayers.add(player); // Defer assignment.
			}
		}
		return assignments; // Success.
			}

	private Position getUnambiguousPosition(Player player) {
		Position position = player.getPosition();
		if (position == null && player.getAllowedPositions().size() > 1)
			position = player.getAllowedPositions().get(0);
		return position;
	}

	// Disambiguate all unassigned, active players.
	private boolean assignAmbigousActivePositions(
			Map<Player, Position> assignments,
			Constraints counter, List<Player> activePlayers,
			final int lastPos) 
	{
		final int length = activePlayers.size();
		int pos = lastPos;
		Player player = activePlayers.get(pos);
		while (true) {
			Position position = disambiguatePosition(
					counter, player.getAllowedPositions(), assignments.get(player));
			assignments.put(player, position);
			if (position != null) {
				if (!counter.take(position))
					throw new IllegalStateException("Could not take a non-filled position");
				// Move forward to next player.
				if (++pos >= length) return true; // Success.
				player = activePlayers.get(pos);
			} else {
				// Backtrack to previous player.
				if (--pos < 0) return false; // Failure.
				player = activePlayers.get(pos);
				counter.give(assignments.get(player));
			}
		}
	}

	// Finds the next position in the player's list that is feasible - but does not take it.
	private Position disambiguatePosition(
			Constraints counter,
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
			if (!counter.isFull(position))
				return position;
		}
		return null;
	}

	// Assign the given number of inactive players to the remaining positions.
	private boolean assignInactivePositions(
			Map<Player, Position> assignments, Constraints counter,
			List<Player> inactivePlayers, final int numPlayers) {
		Map<Player, Position> localAssignments = new HashMap<>();
		LinkedList<Integer> queue = new LinkedList<>();
		final int totPlayers = inactivePlayers.size();
		int pos = 0;
		Player player = inactivePlayers.get(pos);
		Position lastPosition = localAssignments.get(player);
		while (true) {
			// Try current player.
			Position position = disambiguatePosition(
					counter, player.getAllowedPositions(), lastPosition);
			if (position != null) {
				if (!counter.take(position))
					throw new IllegalStateException("Could not take a non-filled position");
				localAssignments.put(player, position);
				queue.addLast(pos);
				if (queue.size() >= numPlayers) break; // Success.
			} else if (lastPosition != null) {
				localAssignments.remove(player);
			}
			// Try going to next player.
			if (++pos < totPlayers) {
				player = inactivePlayers.get(pos);
				lastPosition = localAssignments.get(player);
				continue; 
			}
			// Backtrack to last assigned player.
			if (queue.isEmpty()) return false; // Failure.
			pos = queue.removeLast();
			player = inactivePlayers.get(pos);
			lastPosition = localAssignments.get(player);
			counter.give(lastPosition);
		}
		// Assign positions to selected inactive players.
		for (int assignedPos : queue) {
			player = inactivePlayers.get(assignedPos);
			assignments.put(player, localAssignments.get(player));
		}
		return true;
	}

}
