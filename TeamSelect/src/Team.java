
public class Team {

	public enum Zone {
		goal, defense, midfield, attack
	}
	public static final int NUM_ZONES = Zone.values().length;
	
	public final String name;
	public final int[] strengths = new int[NUM_ZONES];
	
	public Team(String name, int[] strengths) {
		this.name = name;
		if (strengths.length != NUM_ZONES)
			throw new RuntimeException("Invalid vector length");
		System.arraycopy(strengths, 0, this.strengths, 0, NUM_ZONES);
	}

	public Team(String data) {
		String[] parts = data.split(",");
		if (parts.length != NUM_ZONES+1)
			throw new RuntimeException("Invalid data: " + data);
		name = parts[0];
		for (int i = 0; i < NUM_ZONES; i++)
			strengths[i] = Integer.valueOf(parts[i+1]);
	}
	
}
