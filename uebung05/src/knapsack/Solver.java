package knapsack;

import java.util.*;

public class Solver implements SolverInterface {

	private int weight_limit = 0;
	private int best_value = 0;
	private Item[] best_solution;
	private Random rand = new Random(2);

	private int method;

	public Solver(int method) {
		this.method = method;
	}

	/**
	 * Solves the binary knapsack by using 3 different heuristic functions. For
	 * testing purposes recommended seeds for the RNG in line 10 are as follows using
	 * rucksack0100a.txt as example: 
	 * heuristik1 := 2 or 10
	 * heuristik2 := 2
	 * heuristik3 := 2
	 */
	@Override
	public Solution solve(Instance instance) {
		int iteration = 100;

		Solution sol = new Solution(instance);

		return sol;
	}
}