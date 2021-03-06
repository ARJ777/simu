package aj.soccer.formation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import aj.soccer.data.Constraints;
import aj.soccer.data.Coordinates;
import aj.soccer.data.Formation;
import aj.soccer.data.Player;
import aj.soccer.data.Position;

/**
 * Provides a means for loading formations from file.
 * <p/>Convention: The name of the file is the name of the formation
 * with hyphens between positions, with file extension ".dat".
 * The formation numbers specify the numbers of defenders, mid-fielders and forwards,
 * e.g. D-M-F.dat specifies 1 goal keeper, D defenders, M mid-fielders and F forwards.
 * <p/>Each line in the file, other than a blank or comment line, specifies
 * the position (e.g. G, D, M or F) and initial location (e.g. x and y coordinates) of
 * a player, with fields in that order and separated by commas.
 */
public class FormationFactory {

	private static final int NUM_POSITIONS = 11;
	private static final File FILE_ROOT = new File("formations");
	private static final String FILE_EXT = ".dat";
	private static final String COMMENT_MARKER = "#";
	private static final String FIELD_SEPARATOR = ",";
	private static final int POSITION_FIELD_INDEX = 0;
	private static final int X_FIELD_INDEX = 1;
	private static final int Y_FIELD_INDEX = 2;
	private static final FilenameFilter FILE_FILTER = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(FILE_EXT);
		}
	};

	private static Map<String, Formation> formations = null;

	private FormationFactory() {}

	private static String toFormationName(String fileName) {
		if (!fileName.endsWith(FILE_EXT))
			return null;
		return fileName.substring(0, fileName.length() - FILE_EXT.length());
	}

	private static String toFormationName(File formationFile) {
		return toFormationName(formationFile.getName());
	}

	/**
	 * Obtains a collection of defined formations.
	 * 
	 * @return The formation instances.
	 */
	public static Collection<Formation> getFormations() {
		loadFormations();
		return Collections.unmodifiableCollection(formations.values());
	}

	private static void loadFormations() {
		if (formations == null) {
			Map<String, Formation> _formations = new HashMap<>();
			for (File formationFile : FILE_ROOT.listFiles(FILE_FILTER)) {
				String formationName = toFormationName(formationFile);
				Formation formation = loadFormation(formationName, formationFile);
				_formations.put(formationName, formation);
			}
			formations = _formations;
		}
	}

	private static Formation loadFormation(String formationName, File formationFile) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(formationFile)))) {
			return parseFormation(formationName, br);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private static Formation parseFormation(String formationName, BufferedReader reader) {
		final Map<Position, List<Coordinates>> map = new HashMap<>(); 
		String line = null;
		while (true) {
			try {
				line = reader.readLine();
				if (line == null) break;
				line = line.trim();
				if (line.isEmpty() || line.startsWith(COMMENT_MARKER)) continue;
				parsePlayer(map, line);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		return new FormationImpl(map);
	}

	private static void parsePlayer(Map<Position, List<Coordinates>> map, String line) {
		String[] fields = line.split(FIELD_SEPARATOR);
		Position pos = Position.fromCode(fields[POSITION_FIELD_INDEX].charAt(0));
		List<Coordinates> list = map.get(pos);
		if (list == null) {
			list = new ArrayList<>();
			map.put(pos, list);
		}
		list.add(
				new CoordinatesImpl(
						Double.parseDouble(fields[X_FIELD_INDEX]),
						Double.parseDouble(fields[Y_FIELD_INDEX])
						)
				);
	}

	/**
	 * Refreshes the list of currently defined formations from the disk, e.g.
	 * if a new formation file has been added.
	 */
	public static void refreshFormations() {
		formations = null;
	}

	/**
	 * Obtains a formation by name.
	 * 
	 * @param formationName - The name of the formation, e.g. D-M-F.
	 * @return The formation, or a value of null if the formation is unknown.
	 */
	public static /*@Nullable*/ Formation getFormation(String formationName) {
		loadFormations();
		return formations.get(formationName);
	}

	/**
	 * Deselects all players from any on-field positions.
	 * 
	 * @param players - The list of players.
	 */
	public static void deselectPlayers(List<Player> players) {
		for (Player player : players) {
			player.setPosition(null);
			player.setLocation(null);
		}
	}

	/**
	 * Automatically selects a group of on-field players and assigns them to their 
	 * positions in the formation.
	 * <p/>Players who are already active will be reselected, and any previously
	 * assigned field positions (but not locations) will be honoured. 
	 * If necessary, additional players
	 * who are inactive but selectable will also be selected.  
	 * Players who are unselectable will never be selected. 
	 * 
	 * @param allPlayers - The list of all possible players.
	 * @param formation - The on-field formation.
	 * @return The list of selected players, or a value of null if
	 * some positions in the formation could not be filled. 
	 */
	public static /*@Nullable*/ List<Player> selectPlayers(
			List<Player> allPlayers, Formation formation) 
	{
		Map<Player, Position> selectedPlayers = 
				AssignmentFactory.assignPlayerPositions(
						getConstraints(formation), allPlayers);
		if (selectedPlayers == null) return null;
		Map<Position, List<Coordinates>> posLocations = getLocations(formation);
		List<Player> players = new LinkedList<>();
		for (Player player : allPlayers) {
			Position position = selectedPlayers.get(player);
			if (position == null) continue;
			player.setPosition(position);
			List<Coordinates> locations = posLocations.get(position);
			Coordinates location = locations.remove(0);
			player.setLocation(location);
			players.add(player);
		}
		return players;
	}

	private static Map<Position, List<Coordinates>> getLocations(Formation formation) {
		Map<Position, List<Coordinates>> map = new HashMap<>();
		List<Coordinates> goalKeeper = new LinkedList<>();
		goalKeeper.add(formation.getGoalKeeper());
		map.put(Position.GoalKeeper, goalKeeper);
		map.put(Position.Defender, new LinkedList<>(formation.getDefenders()));
		map.put(Position.MidFielder, new LinkedList<>(formation.getMidFielders()));
		map.put(Position.Forward, new LinkedList<>(formation.getForwards()));
		return map;
	}

	public static Constraints<Position> getConstraints(Formation formation) {
		return new PositionConstraints(formation);
	}
}