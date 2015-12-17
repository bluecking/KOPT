import java.util.*;

public class Town {
	private int identifier;
	private double[][] neighbour;
	
	public Town() {
		neighbour = new double[100][2];
	}
	
	public Town(int neighbours) {
		neighbour = new double[neighbours][2];
		for(int i = 0; i < neighbours; i++) {
			neighbour[i][0] = 0;
			neighbour[i][1] = 0.001;
		}
	}
	
	public void increasePheronome(int next, double increment) {
		
	}
	
	public Town(Town old) {
		this.identifier = old.getId();
		this.neighbour = old.getNeighbour().clone();
	}
	
	public int getId() {
		return identifier;
	}
	
	public void setNeighbour(int neighbour, double distance) {
		this.neighbour[neighbour - 1][0] = distance;
	}
	
	public double[][] getNeighbour() {
		return neighbour;
	}
	
	public void setId(int identifier) {
		this.identifier = identifier;
	}
	
	
	
	@Override
	public String toString() {
		String s = "";
		
		for(int i = 0; i < neighbour.length; i++) {
			s+= identifier +  " " + (i + 1) + " " + neighbour[i][0] + "\n";
		}
		return s;
	}
}

