package aj.soccer.teams;

/*package-private*/ class PlayerImpl implements Player {

	private final String playerName;

	/*package-private*/ PlayerImpl(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String getName() {
		return playerName;
	}

	@Override
	public String toString() {
		return playerName;
	}

}
