package aj.nfl.stats;

import static aj.nfl.stats.OffenseAction.*;

public enum DefenseAction {

	BlockRun(Run),
	BlockPass(Pass),
	BlockLine(Line),
	BlockKick(Kick);
	
	private final OffenseAction complement;

	private DefenseAction(OffenseAction complement) {
		this.complement = complement;
	}

	public OffenseAction getComplement() {
		return complement;
	}
}
