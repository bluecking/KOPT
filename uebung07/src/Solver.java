import java.util.*;

public class Solver implements SolverInterface {

    Instance instance;

    public Solution solve(Instance instance) {
        this.instance = instance;

//        return method1();
        return method2();
//        return method3();
    }

    // Flip, Greedy, statisch, nach iterationen, position
    private Solution method1(int iterations) {
        Solution solution = new Solution(instance);
        Solution bestSolution = new Solution(solution);
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < iterations; i++) {
            Solution bestNeighbour = new Solution(instance);
            int pos = -1;

            for (int j = 0; j < solution.getSize(); j++) {
                if (list.contains(j)) {
                    continue;
                }

                flip(j, solution);
                if (solution.isFeasible() && solution.getValue() > bestNeighbour.getValue()) {
                    pos = j;
                    bestNeighbour = new Solution(solution);
                }
                flip(j, solution);
            }

            list.add(pos);
            solution = new Solution(bestNeighbour);
            if (solution.getValue() > bestSolution.getValue())
                bestSolution = new Solution(solution);
        }

        return bestSolution;
    }
    
    /**
     * API, random starting solution, dynamic tabulist, long term memory,
     * breaks when there are no neighbours available anymore.
     * jumps back to best value after @max_iterations iterations that don't yield
     * a higher valued solution
     * @return feasible Solution with heuristic highest value
     */
    private Solution method2() {
    	final int max_iterations = 5;
    	int iteration = 0;
    	ArrayList<int[]> tabuList = new ArrayList<int[]>();
    	
    	Solution curr = findStartSolutionByRandom(instance);
    	Solution best = new Solution(curr);
    	ArrayList<Solution> legitNeighbours = legitApiNeighbours(curr);
    	
    	do {
    		Solution tmp = new Solution(instance);
    		for(Solution s : legitNeighbours) {
    			if(s.getValue() > tmp.getValue()) {
    				tmp = new Solution(s);
    			}
    		}
    		curr = new Solution(tmp);
    		if(curr.getValue() > best.getValue()) {
    			iteration = 0;
    			best = new Solution(curr);
    		} else {
    			iteration++;
    			if(iteration > max_iterations) {
    				curr = new Solution(best);
    			}
    		}
    		
    		legitNeighbours = legitApiNeighbours(curr);
    	} while(!legitNeighbours.isEmpty());
        return best;
    }

    private Solution method3() {
        return null;
    }
    
    /**
     * Calculates all neighbours found by using API on Solution object.
     * Ignores neighbours that would create a conflict with tabulist
     * @param sol starting Solution object
     * @return list containing all feasible neighbours
     */
    private ArrayList<Solution> legitApiNeighbours(Solution sol) {
    	ArrayList<Solution> list = new ArrayList<Solution>();
    	
    	for(int i = 0; i < sol.getSize() - 1; i++) {
    		api(i, sol);
    		if(sol.isFeasible()) {
    			list.add(new Solution(sol));			
    		}
    		api(i, sol);
    	}
    	return list;
    }
    
    private ArrayList<Solution> nonLegitApiNeighbours(Solution sol) {
    	return null;
    }

    /**
     * Flips a single byte in a @Solution object
     *
     * @param pos      Determines the position of the byte that will be flipped
     * @param solution object that will be manipulated
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
     * @param from     Bit that will be moved
     * @param to       Target position for the bit stored in 'from'
     * @param solution
     * @Solution object whose 'sol' attribute will be changed
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
        }
        else {
            for (int i = from; to < i; i--) {
                api(i, solution);
            }
        }
    }

    /**
     * Swaps neighboring bits
     *
     * @param pos Position of the bit that will be shifted with the bit at
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
        }
        else {
            tmp = solution.get(pos);
            solution.set(pos, solution.get(pos + 1));
            solution.set(pos + 1, tmp);
        }
    }
    
	/**
	 * Uses an instance to determine a 'random' starting solution by 'randomly'
	 * filling the knapsack with objects until the next object causes an
	 * overflow.
	 */
	private Solution findStartSolutionByRandom(Instance instance) {
		Random rand = new Random(10);
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
}