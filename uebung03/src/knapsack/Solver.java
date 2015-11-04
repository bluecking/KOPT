package knapsack;

import java.util.Arrays;
import java.util.Collections;
import java.lang.*;

public class Solver implements SolverInterface {
	private int weight_limit = 0;
	private int best_value = 0;
	private Item[] best_solution;
	
    @Override
    public Solution solve(Instance instance) {
    	Item[] item_list = new Item[instance.getSize()];
    	weight_limit = instance.getWeightLimit();
    	best_solution = new Item[instance.getSize()];
    	
    	for(int i = 0; i < instance.getSize(); i++) {
    		item_list[i] = new Item(instance.getValue(i), instance.getWeight(i), i);
    	}
    	
    	Arrays.sort(item_list, Collections.reverseOrder());
    	
        Solution solution = new Solution(instance);
        
        for(Item e:item_list) {
        	//System.out.println(e);
        }
        
        item_list = branchAndBound(item_list, 0);
        
        for(Item e: item_list) {
        	if(e.getAmount() == 1) {
        		solution.set(e.getN(), 1);;
        	}
        }
        
        return solution;
    }
    
    private Item[] branchAndBound(Item[] list, int index) {
    	int value = 0;
    	int restweight = weight_limit;
    	int ub_calc_restweight = weight_limit;
    	double ub_curr_node = 0.0;
    	
    	Item[] item_list = list;
    	
    	for(int i = 0; i < index; i++) {
    		if(item_list[i].getAmount() == 1) {
    			value += item_list[i].getValue();
    			restweight -= item_list[i].getWeight();
    			ub_calc_restweight -= item_list[i].getWeight();
    		}
    	}
    	
    	ub_curr_node = (double)value;
    	
    	for(int i = index; i < list.length; i++) {
    		if(item_list[i].getWeight() > ub_calc_restweight) {
    			ub_curr_node += (double)ub_calc_restweight * item_list[i].getEfficiency();
    			ub_calc_restweight = 0;
    		}else{
    			ub_curr_node += item_list[i].getValue();
    			ub_calc_restweight -= item_list[i].getWeight();
    		}
    	}
    	
    	if(value > best_value) {
    		best_value = value;
    		best_solution = item_list;
    	}
    	
    	Item[] list_l = new Item[list.length]; //1
    	Item[] list_r = new Item[list.length]; //0
    	
    	for(int i = 0; i < list.length; i++) {
    		list_l[i] = new Item(item_list[i].getValue(), item_list[i].getWeight(), 
    				item_list[i].getN());
    		list_r[i] = new Item(item_list[i].getValue(), item_list[i].getWeight(), 
    				item_list[i].getN());
    	}
    	
    	if((double)best_value < ub_curr_node) {
    		list_l[index].setAmount(1);
    		list_l = branchAndBound(list_l, index + 1);
    		list_r = branchAndBound(list_r, index + 1);
    	}
    	
    	if((double)value == ub_curr_node) {
    		return item_list;
    	}
    	
    	int value_l = 0;
    	int value_r = 0;
    	
    	for(int i = 0; i < list.length; i++) {
    		if(item_list[i].getAmount() == 1) {
    			value_l += item_list[i].getValue();
    			value_r += item_list[i].getValue();
    		}
    	}
    	
    	if(value_l > value_r && value_l > value) {
    		return list_l;
    	}else if(value_r > value) {
    		return list_r;
    	}else{
    		return item_list;
    	}
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