import java.util.*;

public class Solver {
	private final int totalIterations = 1000;
	private double alpha;
	private double beta;
	
	public ArrayList<Town> solve(ArrayList<Town> towns, double alpha, double beta) {
		if(alpha < 0 && beta < 0) {
			return null;
		}
		
		ArrayList<Town> tmp = new ArrayList<Town>(towns);
		ArrayList<Ant> ants = new ArrayList<Ant>();
		Ant best = new Ant();
		int iterations = 0;
		int amountOfAnts = 3;
		
		best.setDistance(Double.MAX_VALUE);
		
		for(int i = 0; i < amountOfAnts; i++) {
			Ant ant = new Ant();
			ants.add(ant);
		}
		
		while(iterations < totalIterations) {
			Ant currBest = new Ant();
			currBest.setDistance(Double.MAX_VALUE);
			for(Ant a: ants) {
				ArrayList<Town> left = new ArrayList<Town>(towns);
				Town start = new Town(left.get(0));
				a.nextTown(start);
				left.remove(0);
				
				int position = 0;
				//calculates remaining route
				while(!left.isEmpty()) {
					double sum = 0.0;
					
					Random rand = new Random(System.currentTimeMillis());
					double probabilityMatch = Math.random();
					Town next = left.get(rand.nextInt(left.size()));
					
					//sum in denominator of given formula
					for(Town t: left) {
						sum += (Math.pow(t.getNeighbour()[next.getId() - 1][1], alpha) * 
								Math.pow(t.getNeighbour()[next.getId() - 1][0], beta));
					}
					//total probability of taking this node
					double currProbability = ((Math.pow(
							a.getTrack().get(a.getTrack().size() - 1).getNeighbour()
							[next.getId() - 1][1], alpha) * Math.pow(
							a.getTrack().get(a.getTrack().size() - 1).getNeighbour()
							[next.getId() - 1][0], beta)
							) / sum);
					
					if(currProbability > probabilityMatch) {
						Town realNext = new Town(next);
						a.addToDistance(a.getTrack().get(a.getTrack().size() - 1).
								getNeighbour()[next.getId() - 1][0]);
						a.nextTown(realNext);
						left.remove(next);
					}
				}
				//add distance from last to first node
				a.addToDistance(a.getTrack().get(a.getTrack().size() - 1).
						getNeighbour()[0][0]);
				
			}
			for(Ant a: ants) {
				if(a.getDistance() < currBest.getDistance()) {
					currBest = new Ant(a);
				}
				a.clear();
			}
			if(currBest.getDistance() < best.getDistance()) {
				best = new Ant(currBest);
			}
			for(int i = 0; i < towns.size() - 1; i++) {
				towns.get(best.getTrack().get(i).getId() - 1).increasePheronome(
						best.getTrack().get(i + 1).getId() - 1, 0.001);
			}
			towns.get(best.getTrack().get(0).getId() - 1).increasePheronome(
					best.getTrack().get(best.getTrack().size() - 1).getId() - 1, 0.001);
			iterations++;
		}
		System.out.println(best.getDistance());
		return best.getTrack();
	}
}