package knapsack;

import java.util.*;

public class Solver implements SolverInterface {
	
	private final int freezingRate = 1;
	private final int freezingRateRatio = 2;
	private final int heatingRateRatio = 5;
	private int temperature = 0;
	
	private int weight_limit = 0;
	private int amount = 0;
	private Instance instance;  
	
	public Solver(int temperature) {
		this.temperature = temperature;
	}
	
	public Solver() {
		this.temperature = 0;
	}
	
	/**
	 * Solves the binary Knapsack by using Simulated Annealing
	 */
	@Override
	public Solution solve(Instance instance) {
		weight_limit = instance.getWeightLimit();
		amount = instance.getSize();
		this.instance = instance;
		Random r = new Random(1);
		int iteration = r.nextInt(10) * 100;
		int n = 100000;
			
		Solution start = generateRandomSolution(iteration);
		Solution best = new Solution(start);
		
		//Simulated Annealing n iterations
		for(int iter = 0; iter < n; iter++) {
			// calculates best neighbour
			Solution best_neighbour = new Solution(instance);
			
			for(int i = 0; i < instance.getSize(); i++) {
				flip(i, best);
				if(best.isFeasible() && best.getValue() > best_neighbour.getValue()) {
					best_neighbour = new Solution(best);
				}
				flip(i, best);
			}
			
			/*for(int i = 0; i < instance.getSize(); i++) {
				Solution new_neighbour = generateSeededSolution(r.nextInt(temperature));
				if(best_neighbour.getValue() < new_neighbour.getValue()) {
					best_neighbour = new Solution(new_neighbour);
				}
			}*/
			
			// checks whether its Solution value is bigger or lower
			if(best_neighbour.getValue() > best.getValue()) {
				increaseTemperature(true);
				best = new Solution(best_neighbour);
			} else {
				// worse -> checks if its still eligible to take for further use
				if (Math.random() >= calculateProbability(best_neighbour, best)) {
					decreaseTemperature(true);
					best = new Solution(best_neighbour);
				}
			}
			
		}
		

		return best;
	}
	
	/**
	 * Calculates the probability of using a worse Solution
	 * @param next
	 * @param current
	 * @return probability between 0 and 1
	 */
	private double calculateProbability(Solution next, Solution current) {
		int differenceOfValue = next.getValue() - current.getValue();
		return Math.exp(differenceOfValue/temperature);
	}
	
	private void increaseTemperature(boolean bool) {
		if(bool) {
			temperature += freezingRate;
		}
		
	}
	
	private void decreaseTemperature(boolean bool) {
		if(bool) {
			temperature -= freezingRate;
		}
		
	}
	
	
	/**
	 * Generates multiple Solution objects and returns the one with highest value
	 * @param iteration
	 * @param instance
	 * @return
	 */
	private Solution generateRandomSolution(int iteration) {
		Solution sol = new Solution(instance);
		
		for(int i = 0; i < iteration; i++) {
			Solution current = findStartSolutionByRandom();
			if(current.getValue() > sol.getValue()) {
				sol = new Solution(current);
			}
		}
		
		return sol;
	}
	
	/**
	 * Generates a single pseudo random Solution object
	 * @param instance
	 * @return
	 */
	private Solution findStartSolutionByRandom() {
		Solution solution = new Solution(instance);
		Random r = new Random(10);
		
		while (true) {
			int randPos = r.nextInt(solution.getSize() - 1);
			solution.set(randPos, 1);

			if (!solution.isFeasible()) {
				solution.set(randPos, 0);
				break;
			}
		}

		return solution;
	}
	
	/**
	 * Generates a single pseudo random Solution object by using a seed
	 * @param instance
	 * @return
	 */
	private Solution generateSeededSolution(int n) {
		Solution solution = new Solution(instance);
		Random r = new Random(n);
		
		while (true) {
			int randPos = r.nextInt(solution.getSize() - 1);
			solution.set(randPos, 1);

			if (!solution.isFeasible()) {
				solution.set(randPos, 0);
				break;
			}
		}

		return solution;
	}
	
	/**
	 * flips a Solution bit at given position
	 * @param pos
	 * @param solution
	 */
	private void flip(int pos, Solution solution) {
		if (pos > solution.getSize() - 1) {
			return;
		}

		if (solution.get(pos) == 1)
			solution.set(pos, 0);
		else
			solution.set(pos, 1);
	}
}