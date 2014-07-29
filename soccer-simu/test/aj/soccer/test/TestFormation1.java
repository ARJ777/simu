package aj.soccer.test;

import java.util.Arrays;
import java.util.List;

import aj.soccer.data.Coordinates;
import aj.soccer.data.Formation;

/*package-private*/ class TestFormation1 implements Formation {

	private static final Coordinates dummyLocation = new Coordinates() {
		@Override
		public double getY() {
			return 0;
		}
		
		@Override
		public double getX() {
			return 0;
		}
	};

	private static final List<Coordinates> twoLocations =
			Arrays.asList(dummyLocation, dummyLocation);
	
	private static final List<Coordinates> threeLocations =
			Arrays.asList(dummyLocation, dummyLocation,
					dummyLocation);
	
	private static final List<Coordinates> fiveLocations =
			Arrays.asList(dummyLocation, dummyLocation,
					dummyLocation, dummyLocation,
					dummyLocation);

	@Override
	public Coordinates getGoalKeeper() {
		return dummyLocation;
	}

	@Override
	public List<Coordinates> getDefenders() {
		return threeLocations;
	}

	@Override
	public List<Coordinates> getMidFielders() {
		return fiveLocations;
	}

	@Override
	public List<Coordinates> getForwards() {
		return twoLocations;
	}

}
