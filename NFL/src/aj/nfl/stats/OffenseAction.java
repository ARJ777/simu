package aj.nfl.stats;

public enum OffenseAction {

	Run,
	Pass,
	Line,
	Kick;

	public static OffenseAction fromString(String name) {
		for (OffenseAction action : values()) {
			if (name.equalsIgnoreCase(action.name()))
				return action;
		}
		return null;
	}
}
