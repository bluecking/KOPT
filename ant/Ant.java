import java.util.*;

public class Ant {
	private double distance;
	private ArrayList<Town> track;
	
	public Ant() {
		this.distance = 0.0;
		track = new ArrayList<Town>();
	}
	
	public Ant(Ant old) {
		this.distance = old.getDistance();
		this.track = new ArrayList<Town>(old.getTrack());
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
	public void addToDistance(double distance) {
		this.distance += distance;
	}

	
	/**
	 * Adds next town to visit
	 * @param town
	 */
	public void nextTown(Town town) {
		track.add(town);
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	/**
	 * Returns the track that this ant left
	 * @return
	 */
	public ArrayList<Town> getTrack() {
		return track;
	}
	
	public void clear() {
		this.distance = 0.0;
		this.track = new ArrayList<Town>();
	}
}