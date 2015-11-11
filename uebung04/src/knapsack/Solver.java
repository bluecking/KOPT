package knapsack;

import java.util.*;

public class Solver implements SolverInterface {


    private int weight_limit = 0;
    private int best_value = 0;
    private Item[] best_solution;
    private Random rand = new Random(10);

    private int method;

    public Solver(int method) {
        this.method = method;
    }

    @Override
    public Solution solve(Instance instance) {
        int iteration = 1000;

//        Solution start = findStartSolutionByEfficiency(instance);
        Solution start = findStartSolutionByRandom(instance);
       // Solution start = new Solution(instance);

        switch(this.method) {
            case 1:
                return heuristik1(iteration, start);
            case 2:
                return heuristik2(iteration, start);
            case 3:
                return heuristik3(iteration, start);
        }

        return start;
    }
    
    /*
     * Uses an instance to determine a 'random' starting solution by
     * 'randomly' filling the knapsack with objects until the next object
     * causes an overflow.
     */
    private Solution findStartSolutionByRandom(Instance instance) {
        Solution solution = new Solution(instance);
        while(true) {
            int randPos = rand.nextInt(solution.getSize()-1);
            solution.set(randPos, 1);

            if(!solution.isFeasible()) {
                solution.set(randPos, 0);
                break;
            }
        }

        return solution;
    }
    
    /*
     * Uses a neighbourhood that flips a single bit per iteration and compares it with the
     * current solution.
     */
    private Solution heuristik1(int iterations, Solution solution) {
    	Solution bestSolution = new Solution(solution);
    	ArrayList<Solution> list = new ArrayList<Solution>();
    	
        for (int j = 0; j < iterations; j++) {
        	list.clear();
            for (int i = 0; i < solution.getSize(); i++) {
                flipSolution(i, bestSolution);

                if (!bestSolution.isFeasible()) {
                    flipSolution(i, bestSolution);
                } else {
                	list.add(new Solution(bestSolution));
                	flipSolution(i, bestSolution);
                }
            }
            for(Solution sol : list) {
            	if(sol.getValue() > bestSolution.getValue()) {
            		bestSolution = new Solution(sol);
            	}
            }
        }
	        
        return bestSolution;
    }

    private Solution heuristik2(int iterations, Solution solution) {
        Solution bestSolution = new Solution(solution);
        Random rand = new Random(new Date().getTime());

        for (int i = 0; i < iterations; i++) {
            int randPos = rand.nextInt(solution.getSize() - 1);
            flipSolution(randPos, solution);

            if (!solution.isFeasible())
                flipSolution(randPos, solution);
            else {
                if (bestSolution.getValue() < solution.getValue()) {
                    bestSolution = new Solution(solution);
                }
            }
        }

        return bestSolution;
    }

    private Solution heuristik3(int iteration, Solution solution) {
        Solution bestSolution = new Solution(solution);

        for (int j = 0; j < iteration; j++) {
            for (int i = 0; i < solution.getSize() - 1; i++) {
                solution.set(i, solution.get(i + 1));

                solution.set(i + 1, solution.get(i));

                if (!solution.isFeasible()) {
                    solution.set(i, solution.get(i + 1));
                    solution.set(i + 1, solution.get(i));
                } else {
                    if (bestSolution.getValue() < solution.getValue()) {
                        bestSolution = new Solution(solution);
                    }
                }
            }
        }


        return bestSolution;
    }
    
    /*
     * Uses an instance to sort its content by efficiency and 
     * fill the knapsack until the next element would cause the knapsack to overflow.
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
    
    /*
     * Flips a single byte in a @Solution object
     * @param i	Determines the position of the byte that will be flipped
     * @param solution object that will be manipulated
     */
    private void flipSolution(int i, Solution solution) {
        if (solution.get(i) == 1)
            solution.set(i, 0);
        else
            solution.set(i, 1);

    }

}