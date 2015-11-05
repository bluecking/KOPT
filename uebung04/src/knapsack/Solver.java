package knapsack;

import java.util.*;

public class Solver implements SolverInterface {
	private List<Item> items = new LinkedList<Item>();
	private int weight_limit;
	
	public Solver(Instance instance) {
		this.weight_limit = instance.getWeightLimit();
		for(int i = 0; i < instance.getSize(); i++) {
			Item item = new Item(instance.getValueArray()[i], 
					instance.getWeightArray()[i] , i);
			items.add(item);
		}
	}
	
	private class Node implements Comparable<Node> {
	      
	      public int h;
	      List<Item> taken;
	      public double bound;
	      public double value;
	      public double weight;
	      
	      public Node() {
	         taken = new ArrayList<Item>();
	      }
	      
	      public Node(Node parent) {
	         h = parent.h + 1;
	         taken = new ArrayList<Item>(parent.taken);
	         bound = parent.bound;
	         value = parent.value;
	         weight = parent.weight;
	      }
	      
	      public int compareTo(Node other) {
	         return (int) (other.bound - bound);
	      }
	      
	      public void computeBound() {
	         int i = h;
	         double w = weight;
	         bound = value;
	         Item item;
	         do {
	            item = items.get(i);
	            if (w + item.getWeight() > weight_limit) break;
	            w += item.getWeight();
	            bound += item.getWeight();
	            i++;
	         } while (i < items.size());
	         bound += (weight_limit - w) * ((double)item.getValue() / item.getWeight());
	      }
	   }
	
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