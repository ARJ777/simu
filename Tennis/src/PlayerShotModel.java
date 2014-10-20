/*
 * Models how two players fare against each other in a game.
 */
public class PlayerShotModel {

	private final double powerWeight = 5;
	private final double gripWeight = 4;
	private final double speedWeight = 4;
	private final double returnPowerDiscount = 0.8;
	
	public final double aceStrength; // Play serves ace.
	public final double saveStrength; // Player stops ace.
	public final double returnStrength; // Player returns serve.
	public final Player player;
	
	public PlayerShotModel(Player player) {
		this.player = player;
		aceStrength = powerWeight*player.power;
		saveStrength = gripWeight*player.grip+speedWeight*player.speed;
		returnStrength = returnPowerDiscount*powerWeight*player.power;
	}
	
}
