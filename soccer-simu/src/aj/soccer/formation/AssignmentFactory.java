package aj.soccer.formation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import aj.soccer.data.Constraints;
import aj.soccer.data.Player;
import aj.soccer.data.Position;

public class AssignmentFactory {

	/**
	 * Selects players from the given list to fill all of the positions in the formation.
	 * <p/>Restriction 1: All active players must be selected.
	 * <br/>Restriction 2: Any pre-specified position must be respected.
	 * <p/>Note: The player data are not changed, e.g. selected players are not made active.
	 * However, the constraints will likely be modified.
	 * @param counter - The constraints on the capacity of each position.
	 * @param players - The list of all potential players.
	 * @return A map assigning each selected player to a position in the formation.
	 */
	public static Map<Player, Position> assignPlayerPositions(Constraints<Position> counter, List<Player> players) {
		// Assign active players with unambiguous positions.
		List<Player> activePlayers = new ArrayList<>();   // Ambiguous, pre-selected players.
		List<Player> inactivePlayers = new ArrayList<>(); // Currently unselected players.
		Map<Player, Position> assignments = assignUnambiguousActivePositions(counter, players, activePlayers, inactivePlayers);
		// Calculate how many assignments still need to be made.
		final int numRemaining = counter.totalCapacity();
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

	private static Map<Player, Position> assignUnambiguousActivePositions(
			Constraints<Position> counter, List<Player> players, 
			List<Player> activePlayers, List<Player> inactivePlayers)
			{
		Map<Player, Position> assignments = new HashMap<>();
		for (Player player : players) {
			if (player.isActive()) {
				Position position = getUnambiguousPosition(player);
				if (position == null) {
					activePlayers.add(player); // Defer assignment.
				} else {
					if (!counter.decrement(position)) return null; // Failure.
					assignments.put(player, position);
				}
			} else {
				inactivePlayers.add(player); // Defer assignment.
			}
		}
		return assignments; // Success.
	}

	private static Position getUnambiguousPosition(Player player) {
		Position position = player.getPosition();
		if (position == null && player.getAllowedPositions().size() > 1)
			position = player.getAllowedPositions().get(0);
		return position;
	}

	// Disambiguate all unassigned, active players.
	private static boolean assignAmbigousActivePositions(
			Map<Player, Position> assignments,
			Constraints<Position> counter, List<Player> activePlayers,
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
				if (!counter.decrement(position))
					throw new IllegalStateException("Could not take a non-filled position");
				// Move forward to next player.
				if (++pos >= length) return true; // Success.
				player = activePlayers.get(pos);
			} else {
				// Backtrack to previous player.
				if (--pos < 0) return false; // Failure.
				player = activePlayers.get(pos);
				if (!counter.increment(position))
					throw new IllegalStateException("Could not give back a position");
			}
		}
	}

	// Finds the next position in the player's list that is feasible - but does not take it.
	private static Position disambiguatePosition(
			Constraints<Position> counter,
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
			if (counter.capacity(position) > 0)
				return position;
		}
		return null;
	}

	// Assign the given number of inactive players to the remaining positions.
	private static boolean assignInactivePositions(
			Map<Player, Position> assignments, 
			Constraints<Position> counter,
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
				if (!counter.decrement(position))
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
			if (!counter.increment(position))
				throw new IllegalStateException("Could not give back a position");
		}
		// Assign positions to selected inactive players.
		for (int assignedPos : queue) {
			player = inactivePlayers.get(assignedPos);
			assignments.put(player, localAssignments.get(player));
		}
		return true;
	}

}
