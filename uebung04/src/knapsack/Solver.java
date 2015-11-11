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

		Solution start = findStartSolutionByEfficiency(instance);
		Solution start_1 = findStartSolutionByRandom(instance);
		Solution start_2 = new Solution(instance); 
		Solution test = new Solution(instance);
		test.set(0, 1);

		switch (this.method) {
		case 1:
			return heuristik1(iteration, start_1);
		case 2:
			return heuristik2(iteration, start_1);
		case 3:
			return heuristik3(iteration, start_1);
		}

		return start_1;
	}

	/**
	 * Uses an instance to determine a 'random' starting solution by 'randomly'
	 * filling the knapsack with objects until the next object causes an
	 * overflow.
	 */
	private Solution findStartSolutionByRandom(Instance instance) {
		Solution solution = new Solution(instance);
		while (true) {
			int randPos = rand.nextInt(solution.getSize() - 1);
			solution.set(randPos, 1);

			if (!solution.isFeasible()) {
				solution.set(randPos, 0);
				break;
			}
		}

		return solution;
	}

	/**
	 * Heuristic that uses a neighborhood that flips a bit at a single position.
	 */
	private Solution heuristik1(int iterations, Solution solution) {
		Solution bestSolution = new Solution(solution);
		ArrayList<Solution> list = new ArrayList<Solution>();

		for (int j = 0; j < iterations; j++) {
			list.clear();
			for (int i = 0; i < solution.getSize(); i++) {
				flip(i, bestSolution);

				if (!bestSolution.isFeasible()) {
					flip(i, bestSolution);
				} else {
					list.add(new Solution(bestSolution));
					flip(i, bestSolution);
				}
			}
			for (Solution sol : list) {
				if (sol.getValue() > bestSolution.getValue()) {
					bestSolution = new Solution(sol);
				}
			}
		}
		return bestSolution;
	}

	/**
	 * Heuristic that uses an operator that only swaps bits at position n and
	 * n+1, also known as neighboring bits. For position n =
	 * instance.getSize()-1, its bit will be swapped with the very first bit in
	 * the solution.
	 * 
	 * @param iterations
	 *            amount of searches through neighbors
	 * @param solution
	 *            starting @Solution object
	 * @return final @Solution object
	 */
	private Solution heuristik2(int iterations, Solution solution) {
		Solution bestSolution = new Solution(solution);
		ArrayList<Solution> list = new ArrayList<Solution>();

		for (int j = 0; j < iterations; j++) {
			list.clear();
			for (int i = 0; i < solution.getSize(); i++) {
				api(i, bestSolution);

				if (!bestSolution.isFeasible()) {
					api(i, bestSolution);
				} else {
					list.add(new Solution(bestSolution));
					api(i, bestSolution);
				}
			}
			for (Solution sol : list) {
				if (sol.getValue() > bestSolution.getValue()) {
					bestSolution = new Solution(sol);
				}
			}
		}
		return bestSolution;
	}
	
	/**
	 * Heuristic that uses a neighborhood that allows for a bit to move to any other
	 * position.
	 */
	private Solution heuristik3(int iteration, Solution solution) {
		Solution bestSolution = new Solution(solution);
		ArrayList<Solution> list = new ArrayList<Solution>();
		
		for (int j = 0; j < iteration; j++) {
			for (int i = 0; i < solution.getSize(); i++) {
				for (int k = 0; k < solution.getSize(); k++) {
					if (i != k) {
						shift(i, k, bestSolution);
						
						if (!bestSolution.isFeasible()) {
							shift(k, i, bestSolution);
						} else {
							list.add(new Solution(bestSolution));
							shift(k, i, bestSolution);
						}
					}
				}
			}
			for (Solution sol : list) {
				if (sol.getValue() > bestSolution.getValue()) {
					bestSolution = new Solution(sol);
				}
			}
		}
		return bestSolution;
	}

	/**
	 * Uses an instance to sort its content by efficiency and fill the knapsack
	 * until the next element would cause the knapsack to overflow.
	 */
	private Solution findStartSolutionByEfficiency(Instance instance) {
		Solution sol = new Solution(instance);
		ArrayList<Pair> listSortedByEfficiency = new ArrayList<>();

		for (int i = 0; i < instance.getSize(); i++) {
			listSortedByEfficiency.add(new Pair(i, instance.getValue(i) / instance.getWeight(i)));
		}

		Collections.sort(listSortedByEfficiency, new Comparator<Pair>() {
			@Override
			public int compare(Pair o, Pair t1) {
				return Double.compare(o.getValue(), t1.getValue());
			}
		});

		for (Pair pair : listSortedByEfficiency) {
			sol.set(pair.getItem(), 1);

			if (!sol.isFeasible())
				sol.set(pair.getItem(), 0);
		}

		return sol;
	}

	/**
	 * Flips a single byte in a @Solution object
	 * 
	 * @param pos
	 *            Determines the position of the byte that will be flipped
	 * @param solution
	 *            object that will be manipulated
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

	/**
	 * Moves a bit to any position and consequently fixes the amount array in solution.
	 * 
	 * @param from
	 *            Bit that will be moved
	 * @param to
	 *            Target position for the bit stored in 'from'
	 * @param solution
	 * 			  @Solution object whose 'sol' attribute will be changed
	 */
	private void shift(int from, int to, Solution solution) {
		if (from > solution.getSize() - 1 || to > solution.getSize() - 1) {
			return;
		}

		int tmp;
		if (from < to) {
			for (int i = from; i < to; i++) {
				api(i, solution);
			}
		} else {
			for (int i = from; to < i; i--) {
				api(i, solution);
			}
		}
	}

	/**
	 * Swaps neighboring bits
	 * 
	 * @param pos
	 *            Position of the bit that will be shifted with the bit at
	 *            position pos+1
	 */
	private void api(int pos, Solution solution) {
		if (pos > solution.getSize() - 1) {
			return;
		}

		int tmp;
		if (pos == solution.getSize() - 1) {
			tmp = solution.get(pos);

			solution.set(pos, solution.get(0));
			solution.set(0, tmp);
		} else {
			tmp = solution.get(pos);
			solution.set(pos, solution.get(pos + 1));
			solution.set(pos + 1, tmp);
		}
	}
}