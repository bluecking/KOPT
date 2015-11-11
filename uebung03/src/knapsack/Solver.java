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
    	Collections.sort(items, Item.ratio());
    	
    	Node best = new Node();
    	Node root = new Node();
        root.computeBound();
        
        PriorityQueue<Node> q = new PriorityQueue<Node>();
        q.offer(root);
        
        while (!q.isEmpty()) {
           Node node = q.poll();
           
           if (node.bound > best.value && node.h < items.size() - 1) {
              
              Node with = new Node(node);
              Item item = items.get(node.h);
              with.weight += item.getWeight();
              
              if (with.weight <= weight_limit) {
              
                 with.taken.add(items.get(node.h));
                 with.value += item.getValue();
                 with.computeBound();
                 
                 if (with.value > best.value) {
                    best = with;
                 }
                 if (with.bound > best.value) {
                    q.offer(with);
                 }
              }
              
              Node without = new Node(node);
              without.computeBound();
              
              if (without.bound > best.value) {
                 q.offer(without);
              }
           }
        }
    	
        Solution solution = new Solution(instance);
        for(Item i: best.taken) {
        	solution.set(i.getN(), 1);
        }
     
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