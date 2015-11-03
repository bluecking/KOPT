package knapsack;

import java.util.Arrays;
import java.util.Collections;
import java.lang.*;

public class Solver implements SolverInterface {
    @Override
    public Solution solve(Instance instance) {
    	Instance sorted_instance = sortByEfficiency(instance);
        Solution solution = new Solution(instance);
        Solution optSolution = new Solution(instance);
        double upper_bound = 0.0;
        
        return solution;
    }
    
    private Instance sortByEfficiency(Instance instance) {
    	Instance sorted_instance = new Instance(instance.getSize());
    	
    	double[] efficiency = new double[instance.getSize()];
    	
    	for(int i = 0; i<instance.getSize(); i++) {
    		efficiency[i] = (double) instance.getValue(i)/instance.getWeight(i);
    	}
    	
    	Arrays.sort(efficiency);
    	
    	for(int i = 0; i < efficiency.length / 2; i++) {
    	    double temp = efficiency[i];
    	    efficiency[i] = efficiency[efficiency.length - i - 1];
    	    efficiency[efficiency.length - i - 1] = temp;
    	}
    	
    	return sorted_instance;
    }

    private void incrementInstance(Solution solution) {
        for (int i = 0; i < solution.getArray().length; i++) {
            if (solution.getArray()[i] == 0) {
                solution.set(i, 1);
                break;
            } else {
                solution.set(i, 0);
            }
        }
    }

    private void copySolution(Solution solution, Solution opt_solution) {
        for (int i = 0; i < solution.getArray().length; i++) {
            opt_solution.set(i, solution.get(i));
        }
    }

    private boolean isComplete(int[] array) {
        for (int value : array) {
            if (value == 0) return false;
        }

        return true;
    }
}