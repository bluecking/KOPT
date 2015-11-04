package knapsack;

import java.util.Arrays;
import java.util.Collections;
import java.lang.*;

public class Solver implements SolverInterface {
    @Override
    public Solution solve(Instance instance) {
    	Item[] item_list = new Item[instance.getSize()];
    	double upper_bound = 0.0;
    	int n = instance.getWeightLimit();
    	
    	for(int i = 0; i < instance.getSize(); i++) {
    		item_list[i] = new Item(instance.getValue(i), instance.getWeight(i), i);
    	}
    	
    	Arrays.sort(item_list, Collections.reverseOrder());
    	
        Solution solution = new Solution(instance);
        
        branchAndBound(item_list);
        
        for(Item e: item_list) {
        	
        }
        
        return solution;
    }
    
    private void branchAndBound(Item[] item_list) {
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