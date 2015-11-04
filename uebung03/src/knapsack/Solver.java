package knapsack;

import java.util.Arrays;
import java.util.Collections;
import java.lang.*;

public class Solver implements SolverInterface {
	private int weight_limit = 0;
	
    @Override
    public Solution solve(Instance instance) {
    	Item[] item_list = new Item[instance.getSize()];
    	weight_limit = instance.getWeightLimit();
    	
    	for(int i = 0; i < instance.getSize(); i++) {
    		item_list[i] = new Item(instance.getValue(i), instance.getWeight(i), i);
    	}
    	
    	Arrays.sort(item_list, Collections.reverseOrder());
    	
        Solution solution = new Solution(instance);
        
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
    	double ub_curr_node = 0.0;
    	double ub_left_node = 0.0; // 1 Item
    	double ub_right_node = 0.0; // 0 Item
    	Item[] item_list = list;
    	Item[] list_l = new Item[list.length];
    	Item[] list_r = new Item[list.length];
    	
    	for(int i = 0; i < list.length; i++) {
    		list_l[i] = new Item(item_list[i].getValue(), item_list[i].getWeight(),
    				item_list[i].getN());
    		list_r[i] = new Item(item_list[i].getValue(), item_list[i].getWeight(),
    				item_list[i].getN());
    	}
    	
    	int list_l_value = 0;
    	int list_r_value = 0;
    	
    	list_l[index].setAmount(1);
    	
    	for(Item e: item_list) {
    		if(e.getAmount() == 1) {
    			value += e.getValue();
    			restweight -=e.getWeight();
    		}
    	}
    	
    	ub_curr_node = (double)value + item_list[index].getEfficiency() * restweight;
    	
    	
    	if(index + 1 < list.length) {
    		ub_left_node = (double)value + item_list[index].getValue() +
        			item_list[index+1].getEfficiency() * restweight;
        	ub_right_node = (double)value + item_list[index+1].getEfficiency() * restweight;
    	}
    	
    	if((double)value < ub_left_node && index + 1 < list.length) {
    		list_l = branchAndBound(list_l, index + 1);
    	}
    	
    	if((double)value < ub_right_node && index + 1 < list.length) {
    		list_r = branchAndBound(list_r, index + 1);
    	}
    	
    	for(Item a: list_l) {
    		if(a.getAmount() == 1) {
    			list_l_value += a.getValue();
    		}
    	}
    	
    	for(Item b: list_r) {
    		if(b.getAmount() == 1) {
    			list_r_value += b.getValue();
    		}
    	}
    	
    	if(list_l_value > list_r_value) {
    		return list_l;
    	}else{
    		return list_r;
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