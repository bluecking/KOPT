import java.util.*;

public class Solver implements SolverInterface {
	private final int max_iterations = 5;

    private Instance instance;

    public Solution solve(Instance instance) {
        this.instance = instance;

//        return method1();
//        return method2();
        return method3();
    }

    // Flip, Greedy, statisch, nach iterationen, position
    private Solution method1(int iterations, int maxListSize) {
        Solution currentSolution = new Solution(instance);
        Solution bestSolution = new Solution(currentSolution);
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < iterations; i++) {
            Solution bestNeighbour = new Solution(instance);
            int pos = -1;

            // Find the best neighbour
            for (int j = 0; j < currentSolution.getSize(); j++) {
                // Tabu criteria
                if (list.contains(j)) {
                    continue;
                }

                flip(j, currentSolution);

                if (currentSolution.isFeasible() && currentSolution.getValue() > bestNeighbour.getValue()) {
                    pos = j;
                    bestNeighbour = new Solution(currentSolution);
                }

                flip(j, currentSolution);
            }

            if(list.size() == maxListSize) {
                list.remove(list.size() - 1);
            }

            list.add(pos);
            currentSolution = new Solution(bestNeighbour);

            if (currentSolution.getValue() > bestSolution.getValue()) {
                bestSolution = new Solution(currentSolution);
            }
        }

        return bestSolution;
    }
    
    /**
     * API, random starting solution, dynamic tabulist, long term memory,
     * breaks when there are no neighbours available anymore.
     * jumps back to best value after @max_iterations iterations that don't yield
     * a higher valued solution
     * 
     * Tabulist is an int[] defined as follows:
     * {position, valueOfSolution(position), valueOfSolution(position)}
     * @return feasible Solution with heuristic highest value
     */
    private Solution method2() {
    	int iteration = 0;
    	ArrayList<int[]> tabuList = new ArrayList<int[]>();
    	
    	Solution curr = findStartSolutionByRandom(instance);
    	
    	Solution best = new Solution(curr);
    	ArrayList<Solution> legitNeighbours = legitApiNeighbours(curr);
    	
    	do {
    		Solution tmp = new Solution(instance);
    		int indexer = 0;
    		for(int i = 0; i < legitNeighbours.size(); i++) {
    			if(legitNeighbours.get(i).isFeasible() &&
    					tmp.getValue() < legitNeighbours.get(i).getValue()) {
    				boolean bool = true;
    				for(int[] tabu : tabuList) {
    					if(tabu[0] == i && tabu[1] == curr.get(i) && tabu[2] == curr.get(i + 1)) {
    						bool = false;
    					}
    				}
    				if(bool) {
    					indexer = i;
    					tmp = new Solution(legitNeighbours.get(i));
    				}		
    			}
    		}
    		if(tmp.getValue() == 0) {
    			break;
    		}
    		int[] e = {indexer, curr.get(indexer), curr.get(indexer + 1)};
    		System.out.println(e[0] + " " + e[1] + " " + e[2]);
    		tabuList.add(e);
    		curr = new Solution(tmp);
    		if(curr.getValue() > best.getValue()) {
    			iteration = 0;
    			best = new Solution(curr);
    		} else {
    			iteration++;
    			if(iteration > max_iterations) {
    				iteration = 0;
    				curr = new Solution(best);
    			}
    		}
    		System.out.println("Iteration:" + iteration + " V:" + curr.getValue());
    		legitNeighbours = new ArrayList<Solution>(legitApiNeighbours(curr));
    	} while(true);
        return best;


    }
    
    /**
     * Shift neighbourhood, random starting solution, dynamic tabulist, long term memory,
     * Tabulist consists of only a Solution object whose value is checked against every 
     * neighbour. -- note -- does skip different Solutions with same value.
     * e.g. : 1 0 0 0 1 with Solution.value = 10
     * 		  0 0 1 0 0 with Solution.value = 10
     * might implement Solution comparison method to compare Solution objects
     * @return
     */
    private Solution method3() {
    	int iteration = 0;
    	
    	Solution curr = findStartSolutionByRandom(instance);
        Solution best = new Solution(curr);
        ArrayList<Solution> neighbours = new ArrayList<Solution>();
        ArrayList<Solution> tabuList = new ArrayList<Solution>();
        
        while(true) {
        	neighbours = new ArrayList<Solution>(shiftNeighbours(curr));
        	Solution tmp = new Solution(instance);
        	
        	//Determines highest value neighbour
        	for(Solution s : neighbours) {
        		boolean existsInList = false;
        		if(!tabuList.isEmpty()) {
        			for(Solution tabu : tabuList) {
        				if(s.getValue() == tabu.getValue()) {
        					existsInList = true;
        				}
        			}
        		}
        		
        		if(!existsInList && s.getValue() > tmp.getValue()) {
        			tmp = new Solution(s);
        		}
        	}
        	
        	if(tmp.getValue() == 0) {
        		break;
        	}
        	tabuList.add(new Solution(tmp));
        	curr = new Solution(tmp);
        	if(curr.getValue() > best.getValue()) {
    			iteration = 0;
    			best = new Solution(curr);
    		} else {
    			iteration++;
    			if(iteration > max_iterations) {
    				iteration = 0;
    				curr = new Solution(best);
    			}
    		}
        	
        }
        return best;
    }
    
    /**
     * Calculates all neighbours found by using API on Solution object.
     * @param sol starting Solution object
     * @return list containing all feasible neighbours
     */
    private ArrayList<Solution> legitApiNeighbours(Solution sol) {
    	ArrayList<Solution> list = new ArrayList<Solution>();
    	
    	for(int i = 0; i < sol.getSize() - 1; i++) {
    		api(i, sol);    		
    		list.add(new Solution(sol));
    		api(i, sol);
    	}
    	return list;
    }
    
    /**
     * Calculates every feasible neighbour found by using shift on a Solution object
     * @param sol
     * @return
     */
    private ArrayList<Solution> shiftNeighbours(Solution sol) {
    	ArrayList<Solution> list = new ArrayList<Solution>();
    	Solution tmp = new Solution(sol);
    	
    	for(int i = 0; i < sol.getSize(); i++) {
    		for(int j = 0; j < sol.getSize(); j++) {
    			if(i != j) {
    				tmp = new Solution(sol);
    				shift(i, j, tmp);
    				if(tmp.isFeasible()) {
    					list.add(new Solution(tmp));
    				}
    			}
    		}
    	}
    	return list;
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