package aj.soccer.data;

/**
 * Specifies the types of position a player may fill on the team.
 */
public enum Position {

	GoalKeeper('G'),
	Defender('D'),
	Forward('F'),
	MidFielder('M');

	private final char code;

	private Position(char code) {
		this.code = code;
	}
	
	/**
	 * Obtains a printable code for the position.
	 * 
	 * @return The code character.
	 */
	public char toCode() {
		return code;
	}
	
	/**
	 * Obtains the position corresponding to the given code.
	 * 
	 * @param code - The position code.
	 * @return The position instance.
	 * @throws IllegalArgumentException If the code does not correspond to any position.
	 */
	public static Position fromCode(char code) {
		for (Position position : values()) {
			if (position.code == code) return position;
		}
		throw new IllegalArgumentException("Unknown position code: '" + code +"'");
	}
}
