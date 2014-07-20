package aj.soccer.teams;

/*package-private*/ class CoordinatesImpl implements Coordinates {

	private final double xCoord;
	private final double yCoord;

	/*package-private*/ CoordinatesImpl(double xCoord, double yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	@Override
	public double getX() {
		return xCoord;
	}

	@Override
	public double getY() {
		return yCoord;
	}

}
