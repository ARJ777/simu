/*
 * Models how two players fare against each other in a game.
 */
public class SinglesShotModel {

	private final double modelWeight = 50;
	
	public final double probServerAce;
	public final double probReceiverWinsReturn;
	public final double probServerWinsReturn;
	
	public SinglesShotModel(PlayerShotModel server, PlayerShotModel receiver) {
		probServerAce = (modelWeight + server.aceStrength - receiver.saveStrength) / 100.0;
		probReceiverWinsReturn = (modelWeight + receiver.returnStrength - server.saveStrength) / 100.0;
		probServerWinsReturn = (modelWeight + server.returnStrength - receiver.saveStrength) / 100.0;
	}
	
}
