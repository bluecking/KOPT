package knapsack;

import java.util.*;

public class Solver implements SolverInterface {
	
    @Override
    public Solution solve(Instance instance) {
    	Solution solution = new Solution(instance);
     
        return solution;
    }
    
    public double getWeight(List<Item> items) {
    	double weight = 0;
    	for(Item item: items) {
    		weight += item.getWeight();
    	}
    	return weight;
    }
    
    public double getValue(List<Item> items) {
    	double value = 0;
    	for(Item item: items) {
    		value += item.getValue();
    	}
    	return value;
    }

}